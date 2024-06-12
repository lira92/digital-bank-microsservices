package com.investment.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.investment.api.entities.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}
