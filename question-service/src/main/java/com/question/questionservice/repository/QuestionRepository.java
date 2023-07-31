package com.question.questionservice.repository;

import com.question.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Boolean existsByTitle(String title);

    Optional<Question> findQuestionById(Integer id);

    List<Question> findByCategory(String category);

    Integer findQuestion(Integer id);

    @Query(value = "SELECT q.id FROM question q WHERE q.category=:category " +
            "ORDER BY RANDOM() LIMIT :questionNum", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int questionNum);
}
