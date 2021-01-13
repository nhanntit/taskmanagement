package com.tm.taskManagement.controller

import com.tm.taskManagement.exception.ResourceNotFoundException
import com.tm.taskManagement.model.Task
import com.tm.taskManagement.model.TaskHistory
import com.tm.taskManagement.model.User
import com.tm.taskManagement.repository.TaskHistoryRepository
import com.tm.taskManagement.repository.TaskRepository
import com.tm.taskManagement.repository.UserRepository
import com.tm.taskManagement.utils.TaskSpecificationsBuilder
import groovyx.net.http.RESTClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import java.util.regex.Matcher
import java.util.regex.Pattern

//import java.util.List

@RestController
@RequestMapping('/api')
class TaskController {

    @Autowired
    TaskRepository taskRepository

    @Autowired
    UserRepository userRepository

    @Autowired
    TaskHistoryRepository taskHistoryRepository

    @GetMapping('/tasks')
    List<Task> getAllTasks() {
        taskRepository.findAll()
    }

    @GetMapping('/tasks/search')
    List<Task> search(@RequestParam(value = 'search') String search) {
        TaskSpecificationsBuilder builder = new TaskSpecificationsBuilder()
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|!|~)(\\w+?),")
        Matcher matcher = pattern.matcher(search + ',')
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3))
        }
        Specification<Task> spec = builder.build()
        taskRepository.findAll(spec)
    }

    @PostMapping('/tasks')
    Task addTask(@Valid @RequestBody Task task) {
        Task savedTask = taskRepository.save(task)
        addHistory(savedTask, 'Admin adds this task')
        savedTask
    }

    @PostMapping('/tasks/{id}/addsubtask')
    Task addSubTask(@PathVariable(value = 'id') Long taskId, @Valid @RequestBody Task task) {
        Task parentTask = taskRepository.findById(taskId)
                .orElseThrow({ -> new ResourceNotFoundException('Task', 'id', taskId) })
        task.parentID = parentTask.id
        Task savedTask = taskRepository.save(task)
        addHistory(savedTask, 'Admin adds this subtask')
        savedTask
    }

    @GetMapping('/tasks/{id}')
    Task getTaskById(@PathVariable(value = 'id') Long taskId) {
        taskRepository.findById(taskId)
                .orElseThrow({ -> new ResourceNotFoundException('Task', 'id', taskId) })
    }

    @PutMapping('/tasks/{id}')
    Task updateTask(@PathVariable(value = 'id') Long taskId,
                           @RequestBody Task taskDetails) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow({ -> new ResourceNotFoundException('Task', 'id', taskId) })

        String updatedInfo = ''
        if (taskDetails.description) {
            task.description = taskDetails.description
            updatedInfo = 'changed on description'
        }
        if (taskDetails.point) {
            task.point = taskDetails.point
            updatedInfo = String.format('changed point to %s', taskDetails.point)
        }
        String progress = taskDetails.progress
        if (progress == 'TODO') {
            task.progress = progress
            updatedInfo = 'changed progress to TODO'
        } else if (progress == 'IN-PROGRESS') {
            task.progress = progress
            task.startDate = new Date()
            updatedInfo = 'changed progress to IN-PROGRESS'
        } else if (progress == 'DONE') {
            task.progress = progress
            task.endDate = new Date()
            updatedInfo = 'changed progress to DONE'
        }
        Task savedTask = taskRepository.save(task)
        addHistory(savedTask, String.format('Admin updates this task, %s', updatedInfo))
        savedTask
    }

    @DeleteMapping('/tasks/{id}')
    ResponseEntity<?> deleteTask(@PathVariable(value = 'id') Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow({ -> new ResourceNotFoundException('Task', 'id', taskId) })

        userRepository.delete(task)

        ResponseEntity.ok().build()
    }

    @PutMapping('/tasks/{id}/assign')
    Task assignTask(@PathVariable(value = 'id') Long taskId,
                    @RequestBody User userDetails) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow({ -> new ResourceNotFoundException('Task', 'id', taskId) })

        User user = userRepository.findById(userDetails.id)
                .orElseThrow({ -> new ResourceNotFoundException('User', 'id', userDetails.id) })
        task.assignee = user
        Task savedTask = taskRepository.save(task)
        addHistory(savedTask, 'Admin change assignee of this task')
        savedTask
    }

    def addHistory(Task task, String content) {
        TaskHistory history =  new TaskHistory('content':content)
        history.task = task
        taskHistoryRepository.save(history)
    }

}
