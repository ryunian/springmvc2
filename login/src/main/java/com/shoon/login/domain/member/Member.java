package com.shoon.login.domain.member;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@Data
public class Member {

    private Long id;

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;
}
