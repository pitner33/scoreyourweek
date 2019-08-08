package com.sol.scoreyourweek.repository;

import com.sol.scoreyourweek.model.DailyScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DailyScoreRepository extends JpaRepository<DailyScore, Long> {
    Optional<DailyScore> findDailyScoreByDate(Date date);
}
