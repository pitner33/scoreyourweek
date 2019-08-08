package com.sol.scoreyourweek.repository;

import com.sol.scoreyourweek.model.WeeklyScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeeklyScoreRepository extends JpaRepository<WeeklyScore, Long> {
    Optional<WeeklyScore> findByWeekNumber(Integer weekNumber);
}
