package com.sol.scoreyourweek.repository;

import com.sol.scoreyourweek.model.scores.Scores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Scores, Long> {
}
