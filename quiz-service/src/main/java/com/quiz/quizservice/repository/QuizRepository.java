package com.quiz.quizservice.repository;

import com.quiz.quizservice.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    Optional<Quiz> findById(Long id);
}
