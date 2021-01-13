package com.tm.taskManagement.model

import org.hibernate.annotations.OnDelete
import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

//import org.hibernate.validator.constraints.Range
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties
//import org.springframework.data.annotation.CreatedDate
//import org.springframework.data.annotation.LastModifiedDate

import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import java.sql.Timestamp

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = 'history')
@EntityListeners(AuditingEntityListener)
class TaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @NotBlank
    @Length(max = 1024)
    String content

    @ManyToOne //(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Task task

//    @ManyToOne
//    User updatedBy

    @Column(nullable = false, updatable = false)
    @CreatedDate
    Timestamp createdAt

}
