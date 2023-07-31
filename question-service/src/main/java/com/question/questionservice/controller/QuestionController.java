package com.question.questionservice.controller;

import com.question.questionservice.dto.Answer;
import com.question.questionservice.dto.QuestionDTO;
import com.question.questionservice.model.Question;
import com.question.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private Environment environment;

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        questionService.addQuestion(question);

        return new ResponseEntity<>("Question Successfully Added", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Integer id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category) {
        return ResponseEntity.ok(questionService.getQuestionByCategory(category));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        questionService.deleteQuestion(id);

        return new ResponseEntity<>("Question Successfully Deleted", HttpStatus.OK);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam int questionNum) {
        return ResponseEntity.ok(questionService.getQuestionsForQuiz(category, questionNum));
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsFromId(@RequestBody List<Integer> questionIds) {
        System.out.println(environment.getProperty("local.server.port"));

        return new ResponseEntity<>(questionService.getQuestionsFromId(questionIds), HttpStatus.OK);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Answer> answers) {
        return new ResponseEntity<>(questionService.getScore(answers), HttpStatus.OK);
    }
}
