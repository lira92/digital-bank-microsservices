package com.investment.api.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.investment.api.entities.Goal;
import com.investment.api.repositories.GoalRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @GetMapping
    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable Long id) {
        Optional<Goal> goal = goalRepository.findById(id);
        if (goal.isPresent()) {
            return ResponseEntity.ok(goal.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Goal createGoal(@RequestBody Goal goal) {
        return goalRepository.save(goal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable Long id, @RequestBody Goal goalDetails) {
        Optional<Goal> goal = goalRepository.findById(id);
        if (goal.isPresent()) {
        	Goal existingGoal = goal.get();
        	existingGoal.setDescription(goalDetails.getName());
        	existingGoal.set(goalDetails.getName());
        	
            return ResponseEntity.ok(goalRepository.save(existingGoal));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        if (goalRepository.existsById(id)) {
        	goalRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
