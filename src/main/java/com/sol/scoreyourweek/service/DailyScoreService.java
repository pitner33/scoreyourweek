package com.sol.scoreyourweek.service;

import com.sol.scoreyourweek.model.WeeklyScore;
import com.sol.scoreyourweek.model.DailyScore;
import com.sol.scoreyourweek.model.dto.DailyScoreDTO;
import org.springframework.stereotype.Service;

@Service
public interface DailyScoreService {
    DailyScore dailyScoreElementFromDTO(DailyScoreDTO dailyScoreDTO);

    Integer setScoreByBoolean(Boolean scoreBoolean);

    void setScores(DailyScore dailyScore, DailyScoreDTO dailyScoreDTO);

    void setDate(DailyScore dailyScore, DailyScoreDTO dailyScoreDTO);

    Integer dailyScoreSum(DailyScore dailyScore);

    Integer weeklyScoreSum(WeeklyScore weeklyScore);

    Integer weekNumberFromDailyScoreDTO(DailyScoreDTO dailyScoreDTO);
}
