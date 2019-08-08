package com.sol.scoreyourweek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DailyScoreServiceImpl implements DailyScoreService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DailyScoreRepository dailyScoreRepository;

    @Autowired
    WeeklyScoreRepository weeklyScoreRepository;

    @Override
    public DailyScore dailyScoreElementFromDTO(DailyScoreDTO dailyScoreDTO) {

        Optional<DailyScore> dailyScoreToWorkWith = dailyScoreRepository.findDailyScoreByDate(dailyScoreDTO.getDate());

        if (dailyScoreToWorkWith.isPresent()) {
            DailyScore dailyScore = dailyScoreToWorkWith.get();
            setScores(dailyScore, dailyScoreDTO);

            Optional<WeeklyScore> weeklyScoreToWorkWith = weeklyScoreRepository.findByWeekNumber(dailyScore.getWeekNumber());
            if (!weeklyScoreToWorkWith.isPresent()) {
                logger.error("DailyScore is present without WeeklyScore - I thought it is impossible!");
            } else {
                WeeklyScore weeklyScore = weeklyScoreToWorkWith.get();
                List<DailyScore> dailyScoreList = weeklyScore.getListOfDailyScores();
                //TODO refresh the list with this particular DailyScore (fint it in the list and refresh its values)
            }


            dailyScoreRepository.save(dailyScore);
            return dailyScore;
        } else {
            DailyScore dailyScore = new DailyScore();
            setScores(dailyScore, dailyScoreDTO);
            setDate(dailyScore, dailyScoreDTO);
            dailyScore.setWeekNumber(dailyScore.getDate());

            Optional<WeeklyScore> weeklyScoreToWorkWith = weeklyScoreRepository.findByWeekNumber(dailyScore.getWeekNumber());
            if (weeklyScoreToWorkWith.isPresent()) {
                WeeklyScore weeklyScore = weeklyScoreToWorkWith.get();
                setAndSaveWeeklyScore(dailyScore, weeklyScore);
            } else {
                WeeklyScore weeklyScore = new WeeklyScore();
                setAndSaveWeeklyScore(dailyScore, weeklyScore);
            }

            dailyScoreRepository.save(dailyScore);
            return dailyScore;
        }
    }

    private void setAndSaveWeeklyScore(DailyScore dailyScore, WeeklyScore weeklyScore) {
        dailyScore.setWeeklyScore(weeklyScore);
        List<DailyScore> dailyScoreList = new ArrayList<>();
        dailyScoreList.add(dailyScore);

        weeklyScore.setListOfDailyScores(dailyScoreList);
        weeklyScore.setWeekNumber(dailyScore.getWeekNumber());
        weeklyScore.setWeeklyScore(weeklyScoreSum(weeklyScore));
        weeklyScore.setNumberOfScoredDays();
        weeklyScoreRepository.save(weeklyScore);
    }

    @Override
    public Integer setScoreByBoolean(Boolean scoreBoolean) {
        if (scoreBoolean.equals(true)) {
            return 1;
        }
        return 0;
    }

    @Override
    public void setScores(DailyScore dailyScore, DailyScoreDTO dailyScoreDTO) {
        dailyScore.setHealthyFoodScore(setScoreByBoolean(dailyScoreDTO.getHealthyFoodScore()));
        dailyScore.setNoSugarScore(setScoreByBoolean(dailyScoreDTO.getNoSugarScore()));
        dailyScore.setTrainingScore(setScoreByBoolean(dailyScoreDTO.getTrainingScore()));
        dailyScore.setSelfTimeScore(setScoreByBoolean(dailyScoreDTO.getSelfTimeScore()));
    }

    @Override
    public void setDate(DailyScore dailyScore, DailyScoreDTO dailyScoreDTO) {
        dailyScore.setDate(dailyScoreDTO.getDate());
    }

    @Override
    public Integer dailyScoreSum(DailyScore dailyScore) {
        return dailyScore.getHealthyFoodScore()
                + dailyScore.getNoSugarScore()
                + dailyScore.getTrainingScore()
                + dailyScore.getSelfTimeScore();
    }

    @Override
    public Integer weeklyScoreSum(WeeklyScore weeklyScore) {
        List<DailyScore> dailyScoreList = weeklyScore.getListOfDailyScores();
        Integer weeklyScoreSum = 0;
        for (DailyScore dailyScore : dailyScoreList) {
            weeklyScoreSum += dailyScoreSum(dailyScore);
        }

        return weeklyScoreSum;
    }


}
