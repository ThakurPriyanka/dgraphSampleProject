package com.knoldus.dgraph.module.deletePojo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class DeleteListValue {
    private final String uid;
    private final String hobbies;
}
