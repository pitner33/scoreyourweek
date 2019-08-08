package com.sol.scoreyourweek;

import org.springframework.stereotype.Service;

@Service
public interface DailyScoreService {
    DailyScore dailyScoreElementFromDTO(DailyScoreDTO dailyScoreDTO);
    Integer setScoreByBoolean(Boolean scoreBoolean);
    void setScores(DailyScore dailyScore, DailyScoreDTO dailyScoreDTO);
    void setDate(DailyScore dailyScore, DailyScoreDTO dailyScoreDTO);
    Integer dailyScoreSum(DailyScore dailyScore);
    Integer weeklyScoreSum(WeeklyScore weeklyScore);
}
