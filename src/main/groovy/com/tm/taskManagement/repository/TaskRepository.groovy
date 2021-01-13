package com.tm.taskManagement.repository

import com.tm.taskManagement.model.Task
import com.tm.taskManagement.utils.SearchCriteria
import org.hibernate.criterion.CriteriaQuery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.CriteriaBuilder

@Repository
interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

//    @PersistenceContext
//    EntityManager entityManager
//
//    @Override
//    List<Task> searchTask(List<SearchCriteria> params) {
//        CriteriaBuilder builder = entiryManager.getCriteriaBuilder()
//        CriteriaQuery<Task> query =  builder.createQuery(Task.class)
//        Roor r = query.from(Task.class)
//
//        Predicate predicate = builder.conjunction();
//
//        UserSearchQueryCriteriaConsumer searchConsumer =
//                new UserSearchQueryCriteriaConsumer(predicate, builder, r);
//        params.stream().forEach(searchConsumer);
//        predicate = searchConsumer.getPredicate();
//        query.where(predicate);
//
//        List<User> result = entityManager.createQuery(query).getResultList();
//        return result;
//
//    }
}
