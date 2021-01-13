package com.tm.taskManagement.utils

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

//import com.tm.taskManagement.utils.GenericSpecificationsBuilder;
import com.tm.taskManagement.repository.TaskRepository;
import com.tm.taskManagement.utils.TaskSpecification;
//import com.tm.taskManagement.utils.TaskSpecificationsBuilder;
import com.tm.taskManagement.model.Task;
//import com.baeldung.spring.PersistenceConfig;
//import com.baeldung.web.util.CriteriaParser;
import com.tm.taskManagement.utils.SearchOperation;
import com.tm.taskManagement.utils.SpecSearchCriteria;

import java.util.List;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIn.isIn;
import static org.hamcrest.core.IsNot.not;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
@Transactional
@Rollback
public class TaskSpecificationTest {

    @Autowired
    private TaskRepository repository;

    private Task task1;

    private Task task2;

    private Task task3;

    @Before
    public void init() {

        task1 = new Task('description': 'As a manager, I want to add more tasks','point': 4);
        repository.save(task1);

        task2 = new Task('description': 'As a team leader, I want to review the solution', 'point': 3);
        repository.save(task2);

        task3 = new Task('description': 'As a member, I want to update task status','point': 2);
        repository.save(task3);
    }

    @Test
    public void givenDescritionAndPoint_whenGettingListOfTask_thenCorrect() {
        final TaskSpecification spec = new TaskSpecification(new SpecSearchCriteria("description", SearchOperation.EQUALITY, "As a manager, I want to add more tasks"));
        final TaskSpecification spec1 = new TaskSpecification(new SpecSearchCriteria("point", SearchOperation.EQUALITY, 4));
        final List<Task> results = repository.findAll(Specification
                .where(spec)
                .and(spec1));

        assertThat(task1, isIn(results));
        assertThat(task2, not(isIn(results)));
    }

//    @Test
//    public void givenFirstOrLastName_whenGettingListOfUsers_thenCorrect() {
//        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
//
//        SpecSearchCriteria spec = new SpecSearchCriteria("firstName", SearchOperation.EQUALITY, "john");
//        SpecSearchCriteria spec1 = new SpecSearchCriteria("'","lastName", SearchOperation.EQUALITY, "doe");
//
//        List<User> results = repository.findAll(builder
//                .with(spec)
//                .with(spec1)
//                .build());
//
//        assertThat(results, hasSize(2));
//        assertThat(userJohn, isIn(results));
//        assertThat(userTom, isIn(results));
//    }
//
//    @Test
//    public void givenFirstOrLastNameAndAgeGenericBuilder_whenGettingListOfUsers_thenCorrect() {
//        GenericSpecificationsBuilder<User> builder = new GenericSpecificationsBuilder<>();
//        Function<SpecSearchCriteria, Specification<User>> converter = UserSpecification::new;
//
//        CriteriaParser parser=new CriteriaParser();
//        List<User> results = repository.findAll(builder.build(parser.parse("( lastName:doe OR firstName:john ) AND age:22"), converter));
//
//        assertThat(results, hasSize(1));
//        assertThat(userJohn, isIn(results));
//        assertThat(userTom, not(isIn(results)));
//    }
//
//    @Test
//    public void givenFirstOrLastNameGenericBuilder_whenGettingListOfUsers_thenCorrect() {
//        GenericSpecificationsBuilder<User> builder = new GenericSpecificationsBuilder<>();
//        Function<SpecSearchCriteria, Specification<User>> converter = UserSpecification::new;
//
//        builder.with("firstName", ":", "john", null, null);
//        builder.with("'", "lastName", ":", "doe", null, null);
//
//        List<User> results = repository.findAll(builder.build(converter));
//
//        assertThat(results, hasSize(2));
//        assertThat(userJohn, isIn(results));
//        assertThat(userTom, isIn(results));
//    }
//
//    @Test
//    public void givenFirstNameInverse_whenGettingListOfUsers_thenCorrect() {
//        final UserSpecification spec = new UserSpecification(new SpecSearchCriteria("firstName", SearchOperation.NEGATION, "john"));
//        final List<User> results = repository.findAll(Specification.where(spec));
//
//        assertThat(userTom, isIn(results));
//        assertThat(userJohn, not(isIn(results)));
//    }
//
//    @Test
//    public void givenMinAge_whenGettingListOfUsers_thenCorrect() {
//        final UserSpecification spec = new UserSpecification(new SpecSearchCriteria("age", SearchOperation.GREATER_THAN, "25"));
//        final List<User> results = repository.findAll(Specification.where(spec));
//        assertThat(userTom, isIn(results));
//        assertThat(userJohn, not(isIn(results)));
//    }
//
//    @Test
//    public void givenFirstNamePrefix_whenGettingListOfUsers_thenCorrect() {
//        final UserSpecification spec = new UserSpecification(new SpecSearchCriteria("firstName", SearchOperation.STARTS_WITH, "jo"));
//        final List<User> results = repository.findAll(spec);
//        assertThat(userJohn, isIn(results));
//        assertThat(userTom, not(isIn(results)));
//    }
//
//    @Test
//    public void givenFirstNameSuffix_whenGettingListOfUsers_thenCorrect() {
//        final UserSpecification spec = new UserSpecification(new SpecSearchCriteria("firstName", SearchOperation.ENDS_WITH, "n"));
//        final List<User> results = repository.findAll(spec);
//        assertThat(userJohn, isIn(results));
//        assertThat(userTom, not(isIn(results)));
//    }
//
//    @Test
//    public void givenFirstNameSubstring_whenGettingListOfUsers_thenCorrect() {
//        final UserSpecification spec = new UserSpecification(new SpecSearchCriteria("firstName", SearchOperation.CONTAINS, "oh"));
//        final List<User> results = repository.findAll(spec);
//
//        assertThat(userJohn, isIn(results));
//        assertThat(userTom, not(isIn(results)));
//    }
//
//    @Test
//    public void givenAgeRange_whenGettingListOfUsers_thenCorrect() {
//        final UserSpecification spec = new UserSpecification(new SpecSearchCriteria("age", SearchOperation.GREATER_THAN, "20"));
//        final UserSpecification spec1 = new UserSpecification(new SpecSearchCriteria("age", SearchOperation.LESS_THAN, "25"));
//        final List<User> results = repository.findAll(Specification
//                .where(spec)
//                .and(spec1));
//
//        assertThat(userJohn, isIn(results));
//        assertThat(userTom, not(isIn(results)));
//    }
}