package com.sol.scoreyourweek;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;
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

//    @ManyToOne
//    private WeeklyScore weeklyScore;



    public void setWeekNumber(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
    }
}
