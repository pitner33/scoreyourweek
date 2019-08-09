package com.sol.scoreyourweek.model.scores;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sol.scoreyourweek.model.DailyScore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class TrainingScore extends Scores {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long trainingScoreId;

    @OneToOne(mappedBy = "trainingScore", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private DailyScore dailyScore;
}
