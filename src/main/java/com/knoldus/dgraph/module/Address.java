package com.knoldus.dgraph.module;

import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
public final class Address {
    private final String addressType = StringUtil.EMPTY_STRING;
    private final String uid;
    private final String houseNumber;
    private final String street;
    private final String city;
    private final String state;
}
