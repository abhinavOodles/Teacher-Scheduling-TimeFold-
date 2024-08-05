package com.teacher_Sechulding.teacher_Sechulding.Domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Data;

import java.time.LocalDateTime;

@PlanningEntity
@Data
public class Batch {

    @PlanningId
    private long id ;

    private String courseName ;

    private int level ;

    private LocalDateTime startTime ;
    private LocalDateTime endTime ;

    @JsonIgnore
    @PlanningVariable
    @OneToMany
    @Transient
    private Teacher teacher ;




}
