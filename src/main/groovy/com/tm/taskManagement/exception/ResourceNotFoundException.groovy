package com.tm.taskManagement.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends RuntimeException {

    String resourceName
    String fieldName
    Object fieldValue

    ResourceNotFoundException( String resourceName, String fieldName, Object fieldValue) {
        super(String.format('%s not found with %s : %s', resourceName, fieldName, fieldValue))
        this.resourceName = resourceName
        this.fieldName = fieldName
        this.fieldValue = fieldValue
    }

    String getResourceName() {
        resourceName
    }

    String getFieldName() {
        fieldName
    }

    Object getFieldValue() {
        fieldValue
    }

}
