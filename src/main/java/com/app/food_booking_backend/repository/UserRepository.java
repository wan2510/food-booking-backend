package com.app.food_booking_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.food_booking_backend.model.entity.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByUuid(String uuid);

    // Lấy danh sách user chưa bị xóa mềm (dựa trên status = 'ACTIVE')
    @Query("SELECT u FROM User u WHERE u.status != 'DELETED'")
    List<User> findActiveUsers();
    
}