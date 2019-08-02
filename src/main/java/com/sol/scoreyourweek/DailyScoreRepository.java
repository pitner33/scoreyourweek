package com.sol.scoreyourweek;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DailyScoreRepository extends JpaRepository<DailyScore, Long> {
    Optional<DailyScore> findDailyScoreByDate(LocalDate date);
}
