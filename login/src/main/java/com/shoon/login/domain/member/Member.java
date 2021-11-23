package com.shoon.login.domain.member;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Member {

    private Long id;

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @Builder
    public Member(Long id, @NotEmpty String loginId, @NotEmpty String name, @NotEmpty String password) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }
}
