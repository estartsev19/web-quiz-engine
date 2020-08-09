package engine.services;

import engine.entities.Quiz;
import engine.entities.QuizCompleted;
import engine.repositories.QuizCompletedRepository;
import engine.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizCompletedService {

    @Autowired
    private QuizCompletedRepository quizCompletedRepository;


    public Page<QuizCompleted> getAllQuizzesCompleted(Integer pageNo, String username) {
        Pageable paging = PageRequest.of(pageNo, 10, Sort.by("completedAt").descending());
        return quizCompletedRepository.getQuizCompletedByUsername(username,paging);
    }
}
