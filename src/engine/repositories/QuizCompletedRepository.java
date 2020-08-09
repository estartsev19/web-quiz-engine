package engine.repositories;

import engine.entities.QuizCompleted;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCompletedRepository extends PagingAndSortingRepository<QuizCompleted, Integer> {

    Page<QuizCompleted> getQuizCompletedByUsername(String username, Pageable pageable);
}
