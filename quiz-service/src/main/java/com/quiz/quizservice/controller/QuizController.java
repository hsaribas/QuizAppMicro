package com.quiz.quizservice.controller;

import com.quiz.quizservice.dto.Answer;
import com.quiz.quizservice.dto.QuestionDTO;
import com.quiz.quizservice.dto.QuizDTO;
import com.quiz.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDTO quizDTO) {
        quizService.createQuiz(quizDTO.getCategory(), quizDTO.getQuestionNum(), quizDTO.getTitle());

        return new ResponseEntity<>("New Quiz Created", HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionDTO>> getQuizQuestions(@PathVariable Long id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<String> submitQuiz(@PathVariable Long id, @RequestBody List<Answer> answers) {
        return new ResponseEntity<>("Your score is: " + quizService.submitQuiz(id, answers), HttpStatus.OK);
    }
}
