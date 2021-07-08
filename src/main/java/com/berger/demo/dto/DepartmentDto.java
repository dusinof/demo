package com.berger.demo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableDepartmentDto.class)
public interface DepartmentDto extends DepartmentBase {

    Long getOwnerId();

    static DepartmentDto of (Long id,String information, Long owner, Long parentId){
        return ImmutableDepartmentDto.builder()
                .id(id)
                .information(information)
                .ownerId(owner)
                .parentId(Optional.ofNullable(parentId))
                .build();
    }
}
