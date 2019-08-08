package com.sol.scoreyourweek;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyScore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long weekId;

    private Integer weekNumber; //TODO connection to dailyscore and onetomany-manytoone
    private Integer weeklyScore;
    private Double weeklyScorePercent;
    private Integer numberOfScoredDays;

    @OneToMany(mappedBy = "weeklyScore", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<DailyScore> listOfDailyScores;


    public void setNumberOfScoredDays() {
        this.numberOfScoredDays = listOfDailyScores.size();
    }

    public void setWeeklyScorePercent() {
        double scoreInDouble = (double) weeklyScore;
        this.weeklyScorePercent = (scoreInDouble / 28) * 100;
    }
}
