package com.shchurov.webquizzes.controller;

import com.shchurov.webquizzes.model.*;
import com.shchurov.webquizzes.service.CompletedService;
import com.shchurov.webquizzes.service.QuizService;
import com.shchurov.webquizzes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@Validated
public class QuizController {

    private final QuizService quizService;
    private final UserService userService;
    private final CompletedService completedService;

    @Autowired
    public QuizController(QuizService quizService, UserService userService, CompletedService completedService) {
        this.quizService = quizService;
        this.userService = userService;
        this.completedService = completedService;
    }

    private final Feedback feed1 = new Feedback(true, "Congratulations, you're right!");
    private final Feedback feed2 = new Feedback(false, "Wrong answer! Please, try again.");

    @PostMapping("/api/quizzes")
    public Quiz createQuiz(@AuthenticationPrincipal UserDetails details, @Valid @RequestBody Quiz quiz) {
        User user = userService.findUserByEmail(details.getUsername());
        Quiz newQuiz = new Quiz(
                quiz.getTitle(),
                quiz.getText(),
                quiz.getOptions(),
                quiz.getAnswer(),
                user
        );
        return quizService.saveQuiz(newQuiz);
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable Long id) {
        Quiz quiz = quizService.findQuiz(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/api/quizzes")
    public Page<Quiz> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "title") String sortBy) {

        Page<Quiz> result = quizService.findAllQuizzes(page, pageSize, sortBy);

        return result.isEmpty() ? Page.empty() : result;
    }

    @GetMapping("/api/quizzes/completed")
    public Page<Completed> findCompleted(
            @AuthenticationPrincipal UserDetails details,
            @Min(0) @RequestParam(defaultValue = "0") int page,
            @Min(10) @Max(30) @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "completedAt") String sortBy) {

        return completedService.findAllCompleted(
                userService.findUserByEmail(details.getUsername()).getEmail(),
                page,
                pageSize,
                sortBy
        );
    }

    @PutMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> updateQuiz(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable Long id,
            @Valid @RequestBody Quiz quiz) {

        User user = userService.findUserByEmail(details.getUsername());
        Quiz quizFromDb = quizService.findQuiz(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz doesn't exist"));
        if (quizService.findQuiz(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found!");
        } else if (!Objects.equals(quizFromDb.getUser().getEmail(), user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } else {
            return ResponseEntity.ok(quizService.updateQuiz(id, quiz));
        }
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<Feedback> solveQuiz(
            @PathVariable Long id,
            @RequestBody Answer answer,
            @AuthenticationPrincipal UserDetails details) {

        Quiz currentQuiz = quizService.findQuiz(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User user = userService.findUserByEmail(details.getUsername());

        System.out.println(answer.getAnswers());
        System.out.println(currentQuiz.getAnswer());

        if (answer.getAnswers().equals(currentQuiz.getAnswer())) {
            completedService.saveCompleted(new Completed(
                    currentQuiz.getId(),
                    LocalDateTime.now(),
                    user
            ));
            return new ResponseEntity<>(feed1, HttpStatus.OK);
        }
        return new ResponseEntity<>(feed2, HttpStatus.OK);
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> deleteQuiz(@AuthenticationPrincipal UserDetails details, @PathVariable Long id) {
        User user = userService.findUserByEmail(details.getUsername());
        Quiz quizFromDb = quizService.findQuiz(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz doesn't exist"));
        if (quizService.findQuiz(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found!");
        } else if (!Objects.equals(quizFromDb.getUser().getEmail(), user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } else {
            quizService.deleteQuiz(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
