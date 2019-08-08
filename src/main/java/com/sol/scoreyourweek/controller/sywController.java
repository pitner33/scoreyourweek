package com.sol.scoreyourweek.controller;

import com.sol.scoreyourweek.*;
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
    DailyScoreService dailyScoreService;

    @Autowired
    DailyScoreRepository dailyScoreRepository;

    @Autowired
    WeeklyScoreRepository weeklyScoreRepository;

    @PostMapping("/dailyscores")
    public ResponseEntity dailyScores(@RequestBody DailyScoreDTO dailyScoreDTO) {
        DailyScore dailyScore = dailyScoreService.dailyScoreElementFromDTO(dailyScoreDTO);
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
