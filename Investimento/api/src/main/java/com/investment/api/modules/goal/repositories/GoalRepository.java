package com.investment.api.modules.goal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.investment.api.modules.goal.entities.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long>{   
}
