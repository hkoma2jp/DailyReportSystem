package com.techacademy.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name="report")
public class Report {

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 日報の日付 */
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate report_date;

    /** タイトル */
    @Column(nullable = false)
    @NotEmpty
    private String title;

    /** 内容 */
    @Column(nullable = false)
    @NotEmpty
    @Type(type="text")
    private String content;

    /** 従業員テーブルのID */
    @ManyToOne
    @JoinColumn(name="employee_id", nullable = false)
    private Employee employee;

    /** 登録日時 */
    @Column(nullable = false)
    private LocalDateTime created_at;
    public void setCreated_at(LocalDateTime createdAt) {
        this.created_at = createdAt;
    }

    /** 更新日時 */
    @Column(nullable = false)
    private LocalDateTime updated_at;
    public void setUpdated_at(LocalDateTime updatedAt) {
        this.updated_at = updatedAt;
    }

}
