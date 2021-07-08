package com.berger.demo.dto;

import java.util.Optional;

public interface DepartmentBase {

    Long getId();

    String getInformation();

    Optional<Long> getParentId();
}
