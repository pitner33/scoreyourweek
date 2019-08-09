package com.sol.scoreyourweek.service;

import com.sol.scoreyourweek.model.WeeklyScore;
import com.sol.scoreyourweek.model.scores.HealthyFoodScore;
import com.sol.scoreyourweek.model.scores.NoSugarScore;
import com.sol.scoreyourweek.model.scores.SelfTimeScore;
import com.sol.scoreyourweek.model.scores.TrainingScore;
import com.sol.scoreyourweek.repository.ScoreRepository;
import com.sol.scoreyourweek.repository.WeeklyScoreRepository;
import com.sol.scoreyourweek.model.DailyScore;
import com.sol.scoreyourweek.model.dto.DailyScoreDTO;
import com.sol.scoreyourweek.repository.DailyScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreServiceImpl implements ScoreService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DailyScoreRepository dailyScoreRepository;

    @Autowired
    WeeklyScoreRepository weeklyScoreRepository;

    @Autowired
    ScoreRepository scoreRepository;

    @Override
    public DailyScore dailyScoreElementFromDTO(DailyScoreDTO dailyScoreDTO) {

        Optional<DailyScore> dailyScoreToWorkWith = dailyScoreRepository.findDailyScoreByDate(dailyScoreDTO.getDate());

        if (dailyScoreToWorkWith.isPresent()) {
            DailyScore dailyScore = dailyScoreToWorkWith.get();
            Optional<WeeklyScore> weeklyScoreToWorkWith = weeklyScoreRepository.findByWeekNumber(dailyScore.getWeekNumber());

            if (!weeklyScoreToWorkWith.isPresent()) {
                logger.error("DailyScore is present without WeeklyScore - I thought it is impossible!");
            } else {
                WeeklyScore weeklyScore = weeklyScoreToWorkWith.get();
                List<DailyScore> dailyScoreList = weeklyScore.getListOfDailyScores();
                setAndUpdateDailyAndWeeklyScore(dailyScoreDTO, dailyScore, weeklyScore, dailyScoreList);
            }
            return dailyScore;
        } else {
            DailyScore dailyScore = new DailyScore();
            dailyScoreRepository.save(dailyScore);
            Optional<WeeklyScore> weeklyScoreToWorkWith = weeklyScoreRepository.findByWeekNumber(weekNumberFromDailyScoreDTO(dailyScoreDTO));

            if (weeklyScoreToWorkWith.isPresent()) {
                WeeklyScore weeklyScore = weeklyScoreToWorkWith.get();
                List<DailyScore> dailyScoreList = weeklyScore.getListOfDailyScores();

                setAndSaveDailyAndWeeklyScore(dailyScoreDTO, dailyScore, weeklyScore, dailyScoreList);
                return dailyScore;
            } else {
                WeeklyScore weeklyScore = new WeeklyScore();
                List<DailyScore> dailyScoreList = new ArrayList<>();
                setAndSaveDailyAndWeeklyScore(dailyScoreDTO, dailyScore, weeklyScore, dailyScoreList);
            }
            return dailyScore;
        }
    }

    private void setAndUpdateDailyAndWeeklyScore(DailyScoreDTO dailyScoreDTO, DailyScore dailyScore, WeeklyScore weeklyScore, List<DailyScore> dailyScoreList) {
        setScores(dailyScore, dailyScoreDTO);

        dailyScoreList.forEach(listelement -> {
            if (listelement.getId().equals(dailyScore.getId())) {
                dailyScoreList.remove(listelement);
                dailyScoreList.add(dailyScore);
            }
        });

        weeklyScore.setListOfDailyScores(dailyScoreList);
        weeklyScore.setWeekNumber(dailyScore.getWeekNumber());
        weeklyScore.setWeeklyScore(weeklyScoreSum(weeklyScore));
        weeklyScore.setWeeklyScorePercent();
        weeklyScore.setNumberOfScoredDays();

        dailyScore.setWeeklyScore(weeklyScore);
        weeklyScoreRepository.save(weeklyScore);
    }

    private void setAndSaveDailyAndWeeklyScore(DailyScoreDTO dailyScoreDTO, DailyScore dailyScore, WeeklyScore weeklyScore, List<DailyScore> dailyScoreList) {
        setScores(dailyScore, dailyScoreDTO);
        setDate(dailyScore, dailyScoreDTO);
        dailyScore.setWeekNumber(weekNumberFromDailyScoreDTO(dailyScoreDTO));

        dailyScoreList.add(dailyScore);
        weeklyScore.setListOfDailyScores(dailyScoreList);
        weeklyScore.setWeekNumber(dailyScore.getWeekNumber());
        weeklyScore.setWeeklyScore(weeklyScoreSum(weeklyScore));
        weeklyScore.setWeeklyScorePercent();
        weeklyScore.setNumberOfScoredDays();

        dailyScore.setWeeklyScore(weeklyScore);
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
        TrainingScore trainingScore = new TrainingScore();
        trainingScore.setScore(setScoreByBoolean(dailyScoreDTO.getTrainingScore()));
        trainingScore.setDailyScore(dailyScore);
        dailyScore.setTrainingScore(trainingScore);
        scoreRepository.save(trainingScore);

        HealthyFoodScore healthyFoodScore = new HealthyFoodScore();
        healthyFoodScore.setScore(setScoreByBoolean(dailyScoreDTO.getHealthyFoodScore()));
        healthyFoodScore.setDailyScore(dailyScore);
        dailyScore.setHealthyFoodScore(healthyFoodScore);
        scoreRepository.save(healthyFoodScore);

        NoSugarScore noSugarScore = new NoSugarScore();
        noSugarScore.setScore(setScoreByBoolean(dailyScoreDTO.getNoSugarScore()));
        noSugarScore.setDailyScore(dailyScore);
        dailyScore.setNoSugarScore(noSugarScore);
        scoreRepository.save(noSugarScore);

        SelfTimeScore selfTimeScore = new SelfTimeScore();
        selfTimeScore.setScore(setScoreByBoolean(dailyScoreDTO.getSelfTimeScore()));
        selfTimeScore.setDailyScore(dailyScore);
        dailyScore.setSelfTimeScore(selfTimeScore);
        scoreRepository.save(selfTimeScore);
    }

    @Override
    public void setDate(DailyScore dailyScore, DailyScoreDTO dailyScoreDTO) {
        dailyScore.setDate(dailyScoreDTO.getDate());
    }

    @Override
    public Integer dailyScoreSum(DailyScore dailyScore) {
        return dailyScore.getHealthyFoodScore().getScore()
                + dailyScore.getNoSugarScore().getScore()
                + dailyScore.getTrainingScore().getScore()
                + dailyScore.getSelfTimeScore().getScore();
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

    @Override
    public Integer weekNumberFromDailyScoreDTO(DailyScoreDTO dailyScoreDTO) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dailyScoreDTO.getDate());
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }


}
