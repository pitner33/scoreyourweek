package com.sol.scoreyourweek.model;

import com.sol.scoreyourweek.model.scores.HealthyFoodScore;
import com.sol.scoreyourweek.model.scores.NoSugarScore;
import com.sol.scoreyourweek.model.scores.SelfTimeScore;
import com.sol.scoreyourweek.model.scores.TrainingScore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyScore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private TrainingScore trainingScore;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private HealthyFoodScore healthyFoodScore;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private NoSugarScore noSugarScore;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private SelfTimeScore selfTimeScore;

    private Date date;
    private Integer weekNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private WeeklyScore weeklyScore;

}
