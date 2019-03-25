package com.knoldus.dgraph.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@Builder
@AllArgsConstructor
@Value
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class People {
    List<Person> persons;

}