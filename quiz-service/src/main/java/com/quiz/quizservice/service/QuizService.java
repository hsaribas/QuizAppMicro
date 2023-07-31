package com.quiz.quizservice.service;

import com.quiz.quizservice.dto.Answer;
import com.quiz.quizservice.dto.QuestionDTO;
import com.quiz.quizservice.exception.ErrorMessage;
import com.quiz.quizservice.feign.QuizInterface;
import com.quiz.quizservice.model.Quiz;
import com.quiz.quizservice.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizInterface quizInterface;

    public void createQuiz(String category, int questionNum, String title) {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, questionNum).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);

        quizRepository.save(quiz);
    }

    public ResponseEntity<List<QuestionDTO>> getQuizQuestions(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() ->
                new RuntimeException(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE));
        List<Integer> questionIds = quiz.getQuestionIds();

        return quizInterface.getQuestionsFromId(questionIds);
    }

    public ResponseEntity<Integer> submitQuiz(Long id, List<Answer> answers) {
        return quizInterface.getScore(answers);
    }
}
