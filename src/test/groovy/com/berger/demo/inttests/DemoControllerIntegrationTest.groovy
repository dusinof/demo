package com.berger.demo.inttests

import com.berger.demo.dto.DepartmentDto
import com.berger.demo.dto.DepartmentOverview
import com.berger.demo.dto.ImmutableUserDto
import com.berger.demo.dto.UserDto
import com.berger.demo.response.GenericResponse
import com.berger.demo.users.UsersRepository
import org.springframework.beans.factory.annotation.Autowired

class DemoControllerIntegrationTest extends IntegrationTest {

    @Autowired
    UsersRepository usersRepository



    def "Should fill database with data"() {
        expect:
        assert usersRepository.findAll().size() == 0

        when: 'We create a new data in DB'
        def rawResponse = post('/initialize')

        then: 'expected message is received'
        def response = objectMapper.readValue(rawResponse, GenericResponse<String>)
        response.response == 'Data successfully created.'

        when: 'request all departments'
        def rawDepartments = get("/departments")

        then: 'all departments are created as expected'
        List<DepartmentDto> departments = objectMapper.readValue(rawDepartments, objectMapper.getTypeFactory().constructCollectionType(List.class, DepartmentDto.class))
        departments.containsAll(createExpectedDepartments())

        when: 'request for all created users'
        def rawUsersResponse = get('/users')

        then: 'all users are creted as expected'
        List<UserDto> users = objectMapper.readValue(rawUsersResponse, objectMapper.getTypeFactory().constructCollectionType(List.class, UserDto.class))
        users.containsAll(createExpectedUsers())
    }

    def 'Should validate, that delete operation is available only for specific scenarios'() {
        given:
        def departmentId = 2L
        def url = "/department/${departmentId}"

        when: 'We create a new data in DB'
        def rawResponse = post('/initialize')

        then: 'expected message is received'
        def response = objectMapper.readValue(rawResponse, GenericResponse<String>)
        response.response == 'Data successfully created.'


        when: 'request to delete department Business'
        def rawDeleteResponse = delete(url)
        response = objectMapper.readValue(rawDeleteResponse, GenericResponse<String>)

        then: 'expected delete message was received.'
        response.response == 'Department: 2 successfully deleted by userId: 1'

        when: 'we check departments for orphans'
        entityManager.clear() // otherwise findAll was not reflecting orphan fix.
        def rawDepartments = get("/departments")

        then: 'there should not be any reference to parentId (department) no.2 since it was deleted'
        List<DepartmentDto> departments = objectMapper.readValue(rawDepartments, objectMapper.getTypeFactory().constructCollectionType(List.class, DepartmentDto.class))
        !departments.collect { it.parentId }.contains(Optional.ofNullable(2L))

        when:'Attempt to delete department where Bill is not owner either parent'
        rawDeleteResponse = delete("/department/5")
        response = objectMapper.readValue(rawDeleteResponse, GenericResponse<String>)

        then:'It should fail' // currently sending 200 with message.
        response.response == "Can not delete department: 5. User Id: 1 is not direct owner or parent owner"
    }


    private static List<DepartmentDto> createExpectedDepartments() {
        [
                createDepartment(1L, 'Marketing', 1L, null),
                createDepartment(2L, 'Business', 2L, 1L),
                createDepartment(3L, 'Design', 3L, 2L),
                createDepartment(4L, 'Finance', 4L, 3L),
                createDepartment(5L, 'HR', 3L, null),
                createDepartment(6L, 'Development', 4L, null),
        ]
    }

    private static List<UserDto> createExpectedUsers() {
        [
                createUserDto('Bill', [createDepartmentOverview(1L, 'Marketing', null)]),
                createUserDto('Tom', [createDepartmentOverview(2L, 'Business', 1L)]),
                createUserDto('Mark', [
                        createDepartmentOverview(3L, 'Design', 2L),
                        createDepartmentOverview(5L, 'HR', null)
                ]
                ),
                createUserDto('Jane', [
                        createDepartmentOverview(4L, 'Finance', 3L),
                        createDepartmentOverview(6L, 'Development', null)
                ]),
                createUserDto('David', [])
        ]
    }

    private static UserDto createUserDto(String name, List<DepartmentOverview> departments) {
        ImmutableUserDto.of(name, departments)
    }


    private static DepartmentDto createDepartment(Long id, String information, Long ownerId, Long parentId) {
        DepartmentDto.of(id, information, ownerId, parentId)
    }

    private static DepartmentOverview createDepartmentOverview(Long id, String information, Long parentId) {
        DepartmentOverview.of(id, information, parentId)
    }
}
