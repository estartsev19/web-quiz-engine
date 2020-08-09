package engine.services;

import engine.entities.Quiz;
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
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    public Page<Quiz> getAllQuizzes(Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, 10, Sort.by("id"));

        Page<Quiz> pagedResult = quizRepository.findAll(paging);
        return pagedResult;
    }
}
