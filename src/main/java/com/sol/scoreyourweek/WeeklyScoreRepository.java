package com.sol.scoreyourweek;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyScoreRepository extends JpaRepository<WeeklyScore, Long> {
}
