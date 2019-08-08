package com.sol.scoreyourweek;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeeklyScoreRepository extends JpaRepository<WeeklyScore, Long> {
    Optional<WeeklyScore> findByWeekNumber(Integer weekNumber);
}
