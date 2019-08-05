package com.sol.scoreyourweek;

import com.sol.scoreyourweek.DailyScoreRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

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

    private Integer weekNumber;
    private Integer weeklyScore;
    private Integer numberOfScoredDays;

//    @OneToMany
//    private List<DailyScore> ListOfDailyScores;
//
//    @Autowired
//    private DailyScoreRepository dailyScoreRepository;


}
