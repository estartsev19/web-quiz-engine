package engine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class QuizCompleted {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonIgnore
    private String username;

    @JsonProperty(value = "completedAt", access = JsonProperty.Access.READ_ONLY)
    @Column(name = "completed_at")
    private Timestamp completedAt;

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private int quizId;

    public QuizCompleted() {
    }

    public QuizCompleted(String username, Timestamp completedAt, int quizId) {
        this.username = username;
        this.completedAt = completedAt;
        this.quizId = quizId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }
}
