package com.shchurov.webquizzes.service;

import com.shchurov.webquizzes.model.Quiz;
import com.shchurov.webquizzes.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Long id, Quiz quiz) {

        Quiz quizFromDb = quizRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found!")
        );

        quizFromDb.setTitle(quiz.getTitle());
        quizFromDb.setText(quiz.getText());
        quizFromDb.setOptions(quiz.getOptions());
        quizFromDb.setAnswer(quiz.getAnswer());

        return quizRepository.save(quizFromDb);
    }

    public Page<Quiz> findAllQuizzes(Integer page, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy));
        return quizRepository.findAll(paging);
    }

    public Optional<Quiz> findQuiz(Long id) {
        return quizRepository.findById(id);
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

}
