package com.tm.taskManagement.utils

import org.springframework.data.jpa.domain.Specification;

import com.tm.taskManagement.model.Task;
import com.tm.taskManagement.utils.SpecSearchCriteria;
import com.tm.taskManagement.utils.SearchOperation


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TaskSpecification implements Specification<Task> {

    private SearchCriteria criteria;

    public TaskSpecification(final SearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    @Override
    public Predicate toPredicate(final Root<Task> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        switch (criteria.getOperation()) {
            case ':':
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            case '!':
                return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case '>':
                return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case '<':
                return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case '~':
                return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            default:
                return null;
        }
    }

//    private SpecSearchCriteria criteria;
//
//    public TaskSpecification(final SpecSearchCriteria criteria) {
//        super();
//        this.criteria = criteria;
//    }
//
//    public SpecSearchCriteria getCriteria() {
//        return criteria;
//    }
//
//    @Override
//    public Predicate toPredicate(final Root<Task> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
//        switch (criteria.getOperation()) {
//            case EQUALITY:
//                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
//            case NEGATION:
//                return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
//            case GREATER_THAN:
//                return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
//            case LESS_THAN:
//                return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
//            case LIKE:
//                return builder.like(root.get(criteria.getKey()), criteria.getValue().toString());
//            case STARTS_WITH:
//                return builder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
//            case ENDS_WITH:
//                return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
//            case CONTAINS:
//                return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
//            default:
//                return null;
//        }
//    }

}