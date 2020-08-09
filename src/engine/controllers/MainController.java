package engine.controllers;

import engine.entities.*;
import engine.repositories.QuizCompletedRepository;
import engine.repositories.QuizRepository;
import engine.repositories.UserRepository;
import engine.services.QuizCompletedService;
import engine.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class MainController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizCompletedRepository quizCompletedRepository;

    @Autowired
    private QuizCompletedService quizCompletedService;

    public MainController() {

    }

    //create new quiz from JSON
    @PostMapping
    public Quiz createNewQuiz(@Valid @RequestBody Quiz quiz) {
        quiz.setUser((User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal());
        quizRepository.save(quiz);
        return quiz;
    }

    //get quiz by id. Response NOT include the answer.
    @GetMapping(path = "/{id}")
    public Quiz getQuizById(@PathVariable("id") String quizId) {
        int quizIdInteger = Integer.valueOf(quizId);
        Quiz resultQuiz = quizRepository.findById(quizIdInteger);
        if (resultQuiz == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
        }
        return resultQuiz;
    }

    @GetMapping
    public  Page<Quiz> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer page){
        return quizService.getAllQuizzes(page);
    }

    //solving the quiz
    @PostMapping(path = "/{id}/solve")
    public SolveResult solveQuiz(@PathVariable("id") String quizId, @RequestBody Answer answer) {
        int quizIdInteger = Integer.valueOf(quizId);
        Quiz tempQuiz = quizRepository.findById(quizIdInteger);
        int[] answerToCheckSorted = tempQuiz.getAnswer();
        int[] answerSorted = answer.getAnswer();
        if (answerToCheckSorted == null) {
            answerToCheckSorted = new int[]{};
        }
        Arrays.sort(answerToCheckSorted);
        if (answerSorted != null) {
            Arrays.sort(answerSorted);
        }
        if (answerToCheckSorted == null && answerSorted == null) {
            //add the completion of quiz
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            QuizCompleted quizCompleted = new QuizCompleted(((User) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal())
                    .getUsername(), timestamp, tempQuiz.getId());
            quizCompletedRepository.save(quizCompleted);
            return new SolveResult(true, "Congratulations, you're right!");
        }
        if (Arrays.equals(answerToCheckSorted, answerSorted)) {
            //add the completion of quiz
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            QuizCompleted quizCompleted = new QuizCompleted(((User) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal())
                    .getUsername(), timestamp, tempQuiz.getId());
            quizCompletedRepository.save(quizCompleted);
            return new SolveResult(true, "Congratulations, you're right!");
        } else {
            return new SolveResult(false, "Wrong answer! Please, try again.");
        }
    }

    //delete a quiz
    @DeleteMapping(path = "/{id}")
    public void deleteQuiz(@PathVariable("id") String quizId) {
        int quizIdInteger = Integer.valueOf(quizId);
        Quiz tempQuiz = quizRepository.findById(quizIdInteger);
        if (tempQuiz == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
        }
        if (userCanDeleteQuiz(quizIdInteger)){
            quizRepository.deleteById(quizIdInteger);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Quiz deleted");
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This user is not author of this quiz");
        }
    }


    public boolean userCanDeleteQuiz(int quizId) {
        int userIdFromQuizId = quizRepository.findById(quizId).getUser().getId();
        int currentUserId = userRepository.findByEmail(((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername()).getId();
        if (userIdFromQuizId == currentUserId) {
            return true;
        }
        return false;
    }

    @GetMapping("/completed")
    public Page<QuizCompleted> getAllQuizzesCompleted(
            @RequestParam(defaultValue = "0") Integer page){
        String currentUsername = ((User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal())
                .getUsername();
        return quizCompletedService
                .getAllQuizzesCompleted(page, currentUsername);
    }
}
