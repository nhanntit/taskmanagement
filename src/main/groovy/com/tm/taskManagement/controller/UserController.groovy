package com.tm.taskManagement.controller

import com.tm.taskManagement.exception.ResourceNotFoundException
import com.tm.taskManagement.model.User
import com.tm.taskManagement.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.validation.Valid
import javax.persistence.Query

//import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") String userId) {
        return userRepository.findById(userId)
                .orElseThrow({ -> new ResourceNotFoundException("User", "id", userId) });
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") String userId,
                           @Valid @RequestBody User userDetails) {

        User user = userRepository.findById(userId)
                .orElseThrow({ -> new ResourceNotFoundException("User", "id", userId) });

        user.setFirst_name(userDetails.getFirst_name());
        user.setLast_name(userDetails.getLast_name());

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow({ -> new ResourceNotFoundException("User", "id", userId) });

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}