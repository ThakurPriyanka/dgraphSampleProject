package com.knoldus.dgraph.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Builder
@AllArgsConstructor
@Value
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {
    String name;
    int  age;

}
