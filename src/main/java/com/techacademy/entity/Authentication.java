package com.techacademy.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "authentication")
public class Authentication {

    /** 権限用の列挙型 */
    public static enum Role {
        管理者, 一般
    }

    /** 社員番号 */
    @Id
    @NotEmpty
    private String code;

    /** パスワード　*/
    @NotEmpty
    private String password;

    /** 権限 */
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    /** 従業員テーブルのID */
    @OneToOne
    @JoinColumn(name="employee_id", referencedColumnName="id")
    private Employee employee;

}
