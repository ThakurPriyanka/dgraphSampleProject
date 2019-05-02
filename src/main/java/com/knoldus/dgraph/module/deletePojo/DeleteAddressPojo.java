package com.knoldus.dgraph.module.deletePojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public final class DeleteAddressPojo {
    private final String uid;
    private final DeleteAddress address;
}
