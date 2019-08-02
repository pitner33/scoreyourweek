package com.sol.scoreyourweek.controller;

import com.sol.scoreyourweek.DailyScore;
import com.sol.scoreyourweek.DailyScoreDTO;
import com.sol.scoreyourweek.DailyScoreRepository;
import com.sol.scoreyourweek.DailyScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
