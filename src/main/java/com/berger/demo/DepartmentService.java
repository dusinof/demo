package com.berger.demo;

import com.berger.demo.domain.Department;
import com.berger.demo.domain.Users;
import com.berger.demo.dto.DepartmentDto;
import com.berger.demo.response.GenericResponse;
import com.berger.demo.response.ImmutableGenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CommonService commonService;

    public DepartmentService(DepartmentRepository departmentRepository, CommonService commonService) {
        this.departmentRepository = departmentRepository;
        this.commonService = commonService;
    }


    @Transactional
    public void createDepartment(String information, Users owner, Long parentId) {
        departmentRepository.save(new Department(information, owner, parentId));
    }

    @Transactional
    public void deleteAllDepartments() {
        departmentRepository.deleteAll();
    }

    @Transactional
    public List<DepartmentDto> findAllDepartments() {
        return StreamSupport.stream(departmentRepository.findAll().spliterator(), false)
                .map(dep -> DepartmentDto.of(dep.getId(), dep.getInformation(), dep.getOwner().getId(), dep.getParentId().orElse(null)))
                .collect(Collectors.toList());
    }

    @Transactional
    public GenericResponse<String> deleteDepartment(Long id) {
        return deleteDepartment(id, commonService.getLoggedUser().getId());
    }

    /*
      Only owners or parents of the department might delete or modify department
     */
    @Transactional
    private GenericResponse<String> deleteDepartment(Long departmentId, Long userId) {
        Set<Long> parentOwners = new HashSet<>();
        getOwnerAndParentOwners(departmentId, parentOwners);
        if (parentOwners.contains(userId)) {
            departmentRepository.deleteById(departmentId);
            return ImmutableGenericResponse.of(String.format("Department: %s successfully deleted by userId: %s", departmentId, userId));
        }
        return ImmutableGenericResponse.of(String.format("Can not delete department. User Id:%s is not direct owner or parent owner", userId));
    }

    /**
     * Recursion function which will return Set of owners which are direct owners or
     * parent owners and for those users department might be modified.
     *
     * @param departmentId
     * @param parents
     */
    private void getOwnerAndParentOwners(Long departmentId, Set<Long> parents) {
        departmentRepository.findById(departmentId).ifPresent(department -> {
            if (department.getParentId().isPresent()) {
                parents.add(department.getOwner().getId()); // adding current Owner into Collection
                getOwnerAndParentOwners(department.getParentId().get(), parents);
            }
            parents.add(department.getOwner().getId()); // adding current Owner into Collection
        });
    }
}
