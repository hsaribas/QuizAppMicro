package com.question.questionservice.service;

import com.question.questionservice.dto.Answer;
import com.question.questionservice.dto.QuestionDTO;
import com.question.questionservice.exception.ErrorMessage;
import com.question.questionservice.model.Question;
import com.question.questionservice.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public void addQuestion(Question question) {
        if (questionRepository.existsByTitle(question.getQuestionTitle())) {
            throw new RuntimeException(String.format(ErrorMessage.QUESTION_ALREADY_EXIST_MESSAGE, question.getQuestionTitle()));
        }

        questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {
        if (questionRepository.findAll().size() == 0) {
            throw new RuntimeException(String.format(ErrorMessage.NOT_FOUND_ANY_QUESTION_MESSAGE));
        }

        return questionRepository.findAll();
    }

    public Question getQuestionById(Integer id) {
        return questionRepository.findQuestionById(id).orElseThrow(() ->
                new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
    }

    public List<Question> getQuestionByCategory(String category) {
        return questionRepository.findByCategory(category);
    }

    public void deleteQuestion(Integer id) {
        Question question = getQuestionById(id);

        if (!questionRepository.existsById(questionRepository.findQuestion(id))) {
            throw new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id));
        }

        questionRepository.delete(question);
    }

    public List<Integer> getQuestionsForQuiz(String category, int questionNum) {
        return questionRepository.findRandomQuestionsByCategory(category, questionNum);
    }

    public List<QuestionDTO> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer q : questionIds) {
            questions.add(getQuestionById(q));
        }

        for(Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();

            questionDTO.setId(question.getId());
            questionDTO.setQuestionTitle(question.getQuestionTitle());
            questionDTO.setOption1(question.getOption1());
            questionDTO.setOption2(question.getOption2());
            questionDTO.setOption3(question.getOption3());
            questionDTO.setOption4(question.getOption4());

            questionDTOs.add(questionDTO);
        }

        return questionDTOs;
    }

    public Integer getScore(List<Answer> answers) {
        int score = 0;

        for(Answer answer : answers) {
            Question question = getQuestionById(answer.getId());
            if(answer.getResponse().equals(question.getRightAnswer())) {
                score++;
            }
        }
        return score;
    }
}
