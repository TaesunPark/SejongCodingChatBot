package com.example.testlocal.repository;

import com.example.testlocal.domain.entity.Chat;
import com.example.testlocal.domain.entity.Chatbot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatbotRepository extends JpaRepository<Chatbot, Long> {

    @Query(value = "select * from chatbot where user_id = ?1", nativeQuery = true)
    List<Chatbot> findAllByUserId(Long userId);


}
