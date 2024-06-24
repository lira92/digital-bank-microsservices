package com.investment.api.modules.goal.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.investment.api.modules.goal.entities.DeductionRegister;
import com.investment.api.modules.goal.entities.Goal;

@Service
public class CreateDeductionRegisterCollection {

    public static List<DeductionRegister> createDeductionRegisterCollection(Goal goal, int deductionDay) {
        LocalDate targetDate = goal.getTargetDate();
        LocalDate currentDate = getStartDate(deductionDay);
        return createDeductionRegisterCollection(targetDate, currentDate, deductionDay, goal);
    }

    private static LocalDate getStartDate(int deductionDay) {
        LocalDate currentDate = LocalDate.now();

        if (deductionDay < currentDate.getDayOfMonth() ||
                deductionDay > currentDate.lengthOfMonth()) {
            currentDate = currentDate.plusMonths(1).withDayOfMonth(1);
        }

        return currentDate;
    }

    private static List<DeductionRegister> createDeductionRegisterCollection(LocalDate targetDate,
            LocalDate currentDate, int deductionDay, Goal goal) {
        List<DeductionRegister> deductionRegisters = new ArrayList<>();

        while (!currentDate.isAfter(targetDate)) {
            int deductionDayInMonth = deductionDay;

            if (currentDate.lengthOfMonth() < deductionDay) {
                deductionDayInMonth = currentDate.lengthOfMonth();
            }

            deductionRegisters.add(new DeductionRegister(
                    LocalDate.of(currentDate.getYear(), currentDate.getMonth(), deductionDayInMonth), goal));

            currentDate = currentDate.plusMonths(1).withDayOfMonth(1);
        }

        return deductionRegisters;
    }
}
