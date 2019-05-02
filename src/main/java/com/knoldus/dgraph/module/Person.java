package com.knoldus.dgraph.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public final class Person {
    private final String personType = StringUtil.EMPTY_STRING;
    private final String uid;
    private final String name;
    private final Integer  age;
    private final List<String> hobbies;
    private final List<Address> address;
}
