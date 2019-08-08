package com.sol.scoreyourweek;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyScoreDTO {
    private Boolean trainingScore;
    private Boolean healthyFoodScore;
    private Boolean noSugarScore;
    private Boolean selfTimeScore;
    private Date date;
}
