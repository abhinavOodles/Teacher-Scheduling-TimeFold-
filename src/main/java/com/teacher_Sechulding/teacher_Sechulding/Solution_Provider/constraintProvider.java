package com.teacher_Sechulding.teacher_Sechulding.Solution_Provider;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import com.teacher_Sechulding.teacher_Sechulding.Domain.Batch;
import com.teacher_Sechulding.teacher_Sechulding.Domain.Enum.AvailabilityType;
import com.teacher_Sechulding.teacher_Sechulding.Domain.Teacher;

import java.time.LocalDateTime;

public class constraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                requiredSkill(constraintFactory),
                overlappingOfBatch(constraintFactory),
               BatchShouldHaveTeacher(constraintFactory),
                // Maximumhourperdaynotexceed(constraintFactory),
                //Maximumhourperweeknotexceed(constraintFactory),
                unavailability(constraintFactory),
                requiredlevel(constraintFactory),
                //give15MinuteBreakAfter3Batches(constraintFactory)
    } ;
    }

    private Constraint overlappingOfBatch(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEachUniquePair(Batch.class ,
                        Joiners.equal(Batch::getTeacher))
                .filter(this::checkOverLappingBatch)
                .penalize(HardSoftScore.ofHard(2))
                .asConstraint("Batch is Over-Lapped with Teacher");
    }

    private boolean checkOverLappingBatch(Batch batch1, Batch batch2) {

            Teacher batch1Teacher = batch1.getTeacher();
            Teacher batch2Teacher = batch2.getTeacher();

            LocalDateTime startTimeOfVehicle1 = batch1.getStartTime();
            LocalDateTime endTimeOfVehicle1 = batch1.getEndTime();

            LocalDateTime startTimeOfVehicle2 = batch2.getStartTime();
            LocalDateTime endTimeOfVehicle2 = batch2.getEndTime();


            if (batch1Teacher.equals(batch2Teacher)) {
                return startTimeOfVehicle1.isBefore(startTimeOfVehicle2) && endTimeOfVehicle1.isAfter(endTimeOfVehicle2);
            } else {
                return false;
            }
        }


    private Constraint give15MinuteBreakAfter3Batches(ConstraintFactory constraintFactory) {
        return null ;
    }

    private Constraint requiredlevel(ConstraintFactory constraintFactory) {
      return constraintFactory
              .forEach(Batch.class)
              .filter(batch -> batch.getTeacher() != null)
              .filter(batch -> !(batch.getLevel() == batch.getTeacher().getLevel()))
              .penalize(HardSoftScore.ONE_HARD)
              .asConstraint("Level Mismatched") ;

    }

    private Constraint unavailability(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Batch.class)
                .filter(batch -> batch.getTeacher() != null)
                .filter(batch -> batch.getTeacher().getAvailabilityType() == AvailabilityType.UNAVAILABLE)
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Teacher is UNAVAILABLE");
    }

//    private Constraint Maximumhourperweeknotexceed(ConstraintFactory constraintFactory) {
//        return null;
//    }
//
    private Constraint Maximumhourperdaynotexceed(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Teacher.class)
                .filter(teacher -> teacher.checkMaximumHours() > 6)
                .penalize(HardSoftScore.ofHard(5))
                .asConstraint("Maximum Working Hours Exceed") ;
    }


    private Constraint BatchShouldHaveTeacher(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEachIncludingNullVars(Batch.class)
                .filter(batch -> batch.getTeacher()==null)
                .penalize(HardSoftScore.ofHard(1))
                .asConstraint("Batch should have Teacher");

    }

    private Constraint requiredSkill(ConstraintFactory constraintFactory) {
        return  constraintFactory
                .forEach(Batch.class)
                .filter(batch -> !batch.getTeacher().getCourseName().contains(batch.getCourseName()))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Required Skill");
    }
    }

