package com.tm.taskManagement.model

import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.CreatedDate

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.*
import javax.validation.constraints.NotBlank

//import java.util.Date

@Entity
@Table(name = 'users')
@EntityListeners(AuditingEntityListener)
class User {

    @Id
    @Length(max = 30)
    String id

    @NotBlank
    @Length(max = 100)
    String first_name

    @NotBlank
    @Length(max = 100)
    String last_name

}
