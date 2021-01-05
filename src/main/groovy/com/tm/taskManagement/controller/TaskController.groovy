package com.tm.taskManagement.controller

import com.tm.taskManagement.exception.ResourceNotFoundException
import com.tm.taskManagement.model.Task
import com.tm.taskManagement.model.User
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

    @GetMapping("/tasks")
    List<Task> getAllTasks() {
        taskRepository.findAll();
    }

    @PostMapping("/tasks")
    Task createTask(@Valid @RequestBody Task task) {
        taskRepository.save(task);
    }

    @PostMapping("/tasks/{id}/addsubtask")
    Task addSubTask(@PathVariable(value = "id") Long taskId, @Valid @RequestBody Task task) {
        Task parentTask = taskRepository.findById(taskId)
                .orElseThrow({ -> new ResourceNotFoundException("Task", "id", taskId) })
        task.setParentID(taskId)
        taskRepository.save(task)
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

        if (taskDetails.getDescription()){
            task.setDescription(taskDetails.getDescription())
        }
        if (taskDetails.getPoint()) {
            task.setPoint(taskDetails.getPoint())
        }
        String progress = taskDetails.getProgress()
        if (progress == "IN-PROGRESS") {
            task.setProgress(progress)
            task.setStartDate(new Date())
        } else if (progress == "DONE") {
            task.setProgress(progress)
            task.setEndDate(new Date())
        }
        taskRepository.save(task);
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
        taskRepository.save(task);
    }

}