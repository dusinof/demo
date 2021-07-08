package com.berger.demo.dto;

import com.berger.demo.domain.Department;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableUserDto.class)
@JsonDeserialize(as = ImmutableUserDto.class)
public interface UserDto {

    @Value.Parameter
    @JsonProperty("User Name")
    String getUserName();

    @Value.Parameter
    @JsonProperty("Departments")
    List<DepartmentOverview> getDepartmentOverviews();

}
