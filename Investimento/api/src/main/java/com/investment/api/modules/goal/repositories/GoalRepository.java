package com.investment.api.modules.goal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.investment.api.modules.goal.entities.Goal;

import jakarta.transaction.Transactional;

public interface GoalRepository extends JpaRepository<Goal, Long>{   

    @Modifying
    @Transactional
    @Query(value = "UPDATE goals SET current_value = current_value * (1 + :dailyRate)", nativeQuery = true)
    void updateGoalsWithDailyRate(@Param("dailyRate") double dailyRate);
}
