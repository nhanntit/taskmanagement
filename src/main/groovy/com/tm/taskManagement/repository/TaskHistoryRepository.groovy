package com.tm.taskManagement.repository

import com.tm.taskManagement.model.TaskHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    List<TaskHistory> findByTaskIdOrderByCreatedAtDesc(Long taskId)
//    Optional<TaskHistory> findByIdAndTaskId(Long id, Long taskId)
}