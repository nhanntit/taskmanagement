package com.tm.taskManagement.model

import org.hibernate.validator.constraints.Length
//import org.hibernate.validator.constraints.Range

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties
//import org.springframework.data.annotation.CreatedDate
//import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.groups.Default

//import java.util.Date;

//enum TaskStatus {
//    TODO,
//    IN_PROGRESS,
//    DONE;
//}
//TODO custom validate on update
//interface OnUpdate extends Default {}

@Entity
@Table(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Length(max = 1024)
    String description;

//    @Range(min = 1, max = 150)
//    @Min(value = 1L,groups = OnUpdate.class)
//    @Max(value = 5L, groups = OnUpdate.class)
    @Min(1L)
    @Max(5L)
    int point;

//    @Enumerated(TaskStatus.STRING)
    @Pattern(regexp = "TODO|IN-PROGRESS|DONE", flags = Pattern.Flag.CASE_INSENSITIVE)
    String progress

    @OneToOne
    User assignee

    @Temporal(TemporalType.DATE)
    Date startDate;

    @Temporal(TemporalType.DATE)
    Date endDate;

    Long parentID

}