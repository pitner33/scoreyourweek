package com.sol.scoreyourweek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class DailyScoreServiceImpl implements DailyScoreService {

    @Autowired
    DailyScoreRepository dailyScoreRepository;

    @Override
    public DailyScore dailyScoreElementFromDTO(DailyScoreDTO dailyScoreDTO) {

        Optional<DailyScore> dailyScoreToWorkWith = dailyScoreRepository.findDailyScoreByDate(dailyScoreDTO.getDate());
        if (dailyScoreToWorkWith.isPresent()) {
            DailyScore dailyScore = dailyScoreToWorkWith.get();
            setScores(dailyScore, dailyScoreDTO);
            dailyScoreRepository.save(dailyScore);
            return dailyScore;
        }
        DailyScore dailyScore = new DailyScore();
        setScores(dailyScore, dailyScoreDTO);
        setDate(dailyScore,dailyScoreDTO);
        dailyScore.setWeekNumber(dailyScore.getDate());
        dailyScoreRepository.save(dailyScore);
        return dailyScore;
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
}
