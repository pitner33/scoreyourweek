package com.sol.scoreyourweek.controller;

import com.sol.scoreyourweek.model.DailyScore;
import com.sol.scoreyourweek.model.WeeklyScore;
import com.sol.scoreyourweek.model.dto.DailyScoreDTO;
import com.sol.scoreyourweek.repository.DailyScoreRepository;
import com.sol.scoreyourweek.repository.WeeklyScoreRepository;
import com.sol.scoreyourweek.service.ScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class sywController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ScoreService scoreService;

    @Autowired
    DailyScoreRepository dailyScoreRepository;

    @Autowired
    WeeklyScoreRepository weeklyScoreRepository;

    @PostMapping("/dailyscores")
    public ResponseEntity dailyScores(@RequestBody DailyScoreDTO dailyScoreDTO) {
        DailyScore dailyScore = scoreService.dailyScoreElementFromDTO(dailyScoreDTO);
        logger.info(dailyScore.toString());
        logger.info(LocalDate.now().toString());
        logger.info(new DailyScoreDTO().toString());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dailyscores")
    public ResponseEntity allDailyScores() {
        Optional<List<DailyScore>> dailyScoreList = Optional.ofNullable(dailyScoreRepository.findAll());
        return ResponseEntity.of(dailyScoreList);
    }

    @GetMapping("/dailyscores/{weekNumber}")
    public ResponseEntity dailyScoresByWeekNumber(@PathVariable Integer weekNumber){
        WeeklyScore weeklyScore = weeklyScoreRepository.findByWeekNumber(weekNumber).get();
        return ResponseEntity.ok(weeklyScore.getListOfDailyScores());
    }

    @GetMapping("/weeklyscores")
    public ResponseEntity weeklyScoresList() {
        return ResponseEntity.ok(weeklyScoreRepository.findAll());
    }

}
