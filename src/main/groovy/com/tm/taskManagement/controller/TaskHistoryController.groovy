package com.tm.taskManagement.controller

import com.tm.taskManagement.exception.ResourceNotFoundException
import com.tm.taskManagement.model.Task
import com.tm.taskManagement.model.TaskHistory
import com.tm.taskManagement.model.User
import com.tm.taskManagement.repository.TaskHistoryRepository
import com.tm.taskManagement.repository.UserRepository
import com.tm.taskManagement.repository.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

//import java.util.List

@RestController
@RequestMapping('/api')
class TaskHistoryController {

    @Autowired
    TaskHistoryRepository taskHistoryRepository

    @Autowired
    TaskRepository taskRepository

    @GetMapping('/tasks/{id}/history')
    List<TaskHistory> getAllTaskHistoryByTaskId(@PathVariable(value = 'id') Long taskID) {
        taskHistoryRepository.findByTaskIdOrderByCreatedAtDesc(taskID)
    }

//    @PostMapping('/tasks/{id}/history')
//    Task addSubTask(@PathVariable(value = 'id') Long taskId, @Valid @RequestBody TaskHistory taskHistory) {
//        Task task = taskRepository.findById(taskId)
//                .orElseThrow({ -> new ResourceNotFoundException('Task', 'id', taskId) })
//        taskHistory.setTask(task)
//        taskHistoryRepository.save(taskHistory)
//    }

}
