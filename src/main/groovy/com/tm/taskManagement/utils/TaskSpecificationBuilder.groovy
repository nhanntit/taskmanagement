package com.tm.taskManagement.utils

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tm.taskManagement.model.Task;
import com.tm.taskManagement.utils.SearchOperation;
import com.tm.taskManagement.utils.SearchCriteria

import java.util.stream.Collector
import java.util.stream.Collectors;

public final class TaskSpecificationsBuilder {

    private final List<SearchCriteria> params;

    public TaskSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    // API

    public final TaskSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value))
        return this
    }

    Specification<Task> build() {
        if (params.size() == 0) { return null}
        List<Specification> specs = params.stream()
        .map(TaskSpecification::new).collect(Collectors.toList())

        Specification result = specs.get(0)
        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(specs.get(i))
                    : Specification.where(result).and(specs.get(i));
        }
        result;
    }

//    public final TaskSpecificationsBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
//        return with(null, key, operation, value, prefix, suffix);
//    }
//
//    public final TaskSpecificationsBuilder with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
//        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
//        if (op != null) {
//            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
//                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
//                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
//
//                if (startWithAsterisk && endWithAsterisk) {
//                    op = SearchOperation.CONTAINS;
//                } else if (startWithAsterisk) {
//                    op = SearchOperation.ENDS_WITH;
//                } else if (endWithAsterisk) {
//                    op = SearchOperation.STARTS_WITH;
//                }
//            }
//            params.add(new SpecSearchCriteria(orPredicate, key, op, value));
//        }
//        return this;
//    }

}
