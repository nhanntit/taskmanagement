package com.tm.taskManagement.repository

import com.tm.taskManagement.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}