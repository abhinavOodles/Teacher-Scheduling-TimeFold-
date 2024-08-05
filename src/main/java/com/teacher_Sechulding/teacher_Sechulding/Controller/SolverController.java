package com.teacher_Sechulding.teacher_Sechulding.Controller;

import com.teacher_Sechulding.teacher_Sechulding.Domain.TeacherTimeTable;
import com.teacher_Sechulding.teacher_Sechulding.Service.SolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/solver")
public class SolverController {

    @Autowired
    private SolverService solverService ;

    @PostMapping
    private TeacherTimeTable solve(@RequestBody TeacherTimeTable timeTable){
        return solverService.solver(timeTable) ;
    }
}
