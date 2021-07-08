package com.berger.demo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;
import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableDepartmentOverview.class)
public interface DepartmentOverview extends DepartmentBase {

    static DepartmentOverview of (Long id, String information, Long parentId){
        return ImmutableDepartmentOverview.builder()
                .id(id)
                .information(information)
                .parentId(Optional.ofNullable(parentId))
                .build();
    }
}
