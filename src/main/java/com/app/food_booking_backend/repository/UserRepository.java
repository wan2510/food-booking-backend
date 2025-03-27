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

    // Lấy danh sách user chưa bị xóa mềm (status != 'DELETED')
    @Query("SELECT u FROM User u WHERE u.status != 'DELETED'")
    List<User> findActiveUsers();

    List<User> findByStatus(String status);

    @Query("SELECT u FROM User u WHERE u.role.id IN ('uuid-of-role-3', 'uuid-of-role-4', 'uuid-of-role-5', 'uuid-of-role-6')")
    List<User> findAllStaff();

    @Query("SELECT u FROM User u WHERE u.fullName LIKE %:keyword% OR u.phone LIKE %:keyword%")
    List<User> searchByKeyword(String keyword);
}