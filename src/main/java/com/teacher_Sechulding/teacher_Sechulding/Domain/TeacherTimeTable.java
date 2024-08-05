package com.teacher_Sechulding.teacher_Sechulding.Domain;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.SolverStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@PlanningSolution
@Data
public class TeacherTimeTable {

    @JsonIgnore
    private String name;

    @ProblemFactCollectionProperty
    @ValueRangeProvider
    private List<Teacher> TeacherList;

    @ValueRangeProvider
    @PlanningEntityCollectionProperty
     private List<Batch> BatchList ;




    @JsonIgnore
    @PlanningScore
    private HardSoftScore score;

    @JsonIgnore
    private SolverStatus solverStatus;


    public TeacherTimeTable() {
    }
}
