package com.berger.demo.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableGenericResponse.class)
public interface GenericResponse<T> {

    @Value.Parameter
    T getResponse();
}
