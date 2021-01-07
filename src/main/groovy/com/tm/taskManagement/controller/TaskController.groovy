package com.tm.taskManagement.controller

import com.tm.taskManagement.exception.ResourceNotFoundException
import com.tm.taskManagement.model.Task
import com.tm.taskManagement.model.TaskHistory
import com.tm.taskManagement.model.User
import com.tm.taskManagement.repository.TaskHistoryRepository
import com.tm.taskManagement.repository.TaskRepository
import com.tm.taskManagement.repository.UserRepository
import groovyx.net.http.RESTClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


//import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository

    @Autowired
    TaskHistoryRepository taskHistoryRepository

    @GetMapping("/tasks")
    List<Task> getAllTasks() {
        taskRepository.findAll();
    }

    @PostMapping("/tasks")
    Task createTask(@Valid @RequestBody Task task) {
        Task savedTask = taskRepository.save(task);
        addHistory(savedTask,"Admin adds this task")
        savedTask
    }

    @PostMapping("/tasks/{id}/addsubtask")
    Task addSubTask(@PathVariable(value = "id") Long taskId, @Valid @RequestBody Task task) {
        Task parentTask = taskRepository.findById(taskId)
                .orElseThrow({ -> new ResourceNotFoundException("Task", "id", taskId) })
        task.setParentID(taskId)
        Task savedTask = taskRepository.save(task)
        addHistory(savedTask,"Admin adds this subtask")
        savedTask
    }

    @GetMapping("/tasks/{id}")
    Task getTaskById(@PathVariable(value = "id") Long taskId) {
        taskRepository.findById(taskId)
                .orElseThrow({ -> new ResourceNotFoundException("Task", "id", taskId) });
    }

    @PutMapping("/tasks/{id}")
    Task updateTask(@PathVariable(value = "id") Long taskId,
                           @RequestBody Task taskDetails) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow({ -> new ResourceNotFoundException("Task", "id", taskId) })

        String updatedInfo = ""
        if (taskDetails.getDescription()){
            task.setDescription(taskDetails.getDescription())
            updatedInfo = "changed on description"
        }
        if (taskDetails.getPoint()) {
            task.setPoint(taskDetails.getPoint())
            updatedInfo = String.format("changed point to %s",taskDetails.getPoint())
        }
        String progress = taskDetails.getProgress()
        if (progress == "TODO") {
            task.setProgress(progress)
            updatedInfo = "changed progress to TODO"
        }else if (progress == "IN-PROGRESS") {
            task.setProgress(progress)
            task.setStartDate(new Date())
            updatedInfo = "changed progress to IN-PROGRESS"
        } else if (progress == "DONE") {
            task.setProgress(progress)
            task.setEndDate(new Date())
            updatedInfo = "changed progress to DONE"
        }
        Task savedTask = taskRepository.save(task);
        addHistory(savedTask,String.format("Admin updates this task, %s", updatedInfo))
        savedTask
    }

    @DeleteMapping("/tasks/{id}")
    ResponseEntity<?> deleteTask(@PathVariable(value = "id") Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow({ -> new ResourceNotFoundException("Task", "id", taskId) });

        userRepository.delete(task);

        ResponseEntity.ok().build();
    }

    @PutMapping("/tasks/{id}/assign")
    Task assignTask(@PathVariable(value = "id") Long taskId,
                    @RequestBody User userDetails) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow({ -> new ResourceNotFoundException("Task", "id", taskId) });

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow({ -> new ResourceNotFoundException("User", "id", userDetails.getId()) });
        task.setAssignee(user);
        Task savedTask = taskRepository.save(task);
        addHistory(savedTask,"Admin change assignee of this task")
        savedTask
    }

    def addHistory(Task task, String content) {
        TaskHistory history =  new TaskHistory("content":content)
        history.setTask(task)
        taskHistoryRepository.save(history)
    }
}