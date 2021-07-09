package com.berger.demo.department;

import com.berger.demo.domain.Department;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    @Modifying
    @Query("UPDATE Department SET parentId=:parentDepartmentId where parentId=:deletedDepartmentId")
    int fixOrphan(@Param("parentDepartmentId")Long parentDepartmentId, @Param("deletedDepartmentId")Long deletedDepartmentId);

}
