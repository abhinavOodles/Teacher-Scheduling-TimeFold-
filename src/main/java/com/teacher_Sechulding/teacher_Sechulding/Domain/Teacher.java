package com.teacher_Sechulding.teacher_Sechulding.Domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher_Sechulding.teacher_Sechulding.Domain.Enum.AvailabilityType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.List;

@Data
public class Teacher {

    @PlanningId
    private long id;

    private String name ;

    private String courseName ;

    private int level ;

    private AvailabilityType availabilityType ;


   @ManyToOne
   @Transient
   @JsonIgnore
   private List<Batch> batchList ;


    public boolean checkBatches(){
        if (batchList.size() % 3 == 0){
            return true ;
        }
        return false ;
    }

     public int checkMaximumHours(){
        int totalWorkingHours = 0  ;

        for (Batch batch : batchList){
            totalWorkingHours++ ;
        }
        return  totalWorkingHours ;
     }

}
