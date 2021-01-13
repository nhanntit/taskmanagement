package com.tm.taskManagement.controller

import com.tm.taskManagement.exception.ResourceNotFoundException
import com.tm.taskManagement.model.User
import com.tm.taskManagement.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.validation.Valid
import javax.persistence.Query

//import java.util.List

@RestController
@RequestMapping('/api')
class UserController {

    @Autowired
    UserRepository userRepository

    @GetMapping('/users')
    List<User> getAllUsers() {
        userRepository.findAll()
    }

    @PostMapping('/users')
    User addUser(@Valid @RequestBody User user) {
        userRepository.save(user)
    }

    @GetMapping('/users/{id}')
    User getUserById(@PathVariable(value = 'id') String userId) {
        userRepository.findById(userId)
                .orElseThrow({ -> new ResourceNotFoundException('User', 'id', userId) })
    }

    @PutMapping('/users/{id}')
    User updateUser(@PathVariable(value = 'id') String userId,
                           @Valid @RequestBody User userDetails) {

        User user = userRepository.findById(userId)
                .orElseThrow({ -> new ResourceNotFoundException('User', 'id', userId) })

        user.first_name = userDetails.first_name
        user.last_name = userDetails.last_name

        User updatedUser = userRepository.save(user)
        updatedUser
    }

    @DeleteMapping('/users/{id}')
    ResponseEntity<?> deleteUser(@PathVariable(value = 'id') String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow({ -> new ResourceNotFoundException('User', 'id', userId) })

        userRepository.delete(user)

        ResponseEntity.ok().build()
    }

}
