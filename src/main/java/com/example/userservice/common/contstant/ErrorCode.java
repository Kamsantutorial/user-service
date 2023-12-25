package com.example.userservice.common.contstant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Chuob Bunthoeurn
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    E001("Generate Exception"),
    E002("Record Not Found");

    private String desc;
}
