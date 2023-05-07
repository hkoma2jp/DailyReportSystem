package com.techacademy.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@Table(name = "employee")
@Where(clause = "delete_flag = 0")
public class Employee {
    /** ID 主キー */
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 氏名 20桁 null不許可 */
    @Column(length = 20, nullable = false)
    @NotEmpty
    @Length(max=20)
    private String name;

    /** 削除フラグ */
    @Column(nullable = false)
    private Integer delete_flag;

    /** 登録日時 */
    @Column
    private LocalDateTime created_at;
    public void setCreated_at(LocalDateTime createdAt) {
        this.created_at = createdAt;
    }

    /** 更新日時 */
    @Column
    private LocalDateTime updated_at;
    public void setUpdated_at(LocalDateTime updatedAt) {
        this.updated_at = updatedAt;
    }

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Authentication authentication;
}


