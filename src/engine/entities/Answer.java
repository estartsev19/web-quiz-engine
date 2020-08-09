package engine.entities;

import javax.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private int[] answer;

    public Answer(int[] answer){
        this.answer = answer;
    }

    public Answer(){

    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
        if (this.answer == null){
            this.answer = new int[]{};
        }
    }
}