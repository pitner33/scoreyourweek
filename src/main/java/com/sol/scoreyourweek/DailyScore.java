package com.sol.scoreyourweek;

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

    private Integer trainingScore;
    private Integer healthyFoodScore;
    private Integer noSugarScore;
    private Integer selfTimeScore;
    private Date date;
    private Integer weekNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private WeeklyScore weeklyScore;

}
