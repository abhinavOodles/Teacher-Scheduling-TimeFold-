package com.teacher_Sechulding.teacher_Sechulding.Service;

import ai.timefold.solver.core.api.score.ScoreExplanation;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.constraint.ConstraintMatch;
import ai.timefold.solver.core.api.score.constraint.Indictment;
import ai.timefold.solver.core.api.solver.SolutionManager;
import ai.timefold.solver.core.api.solver.SolverJob;
import ai.timefold.solver.core.api.solver.SolverManager;
import com.teacher_Sechulding.teacher_Sechulding.Domain.Batch;
import com.teacher_Sechulding.teacher_Sechulding.Domain.TeacherTimeTable;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class SolverService {

    @Autowired
     private SolutionManager<TeacherTimeTable, HardSoftScore> solutionManager ;
    @Autowired
    private SolverManager<TeacherTimeTable, String> solverManager ;

    private static final Logger logger = LoggerFactory.getLogger(SolverService.class);


    public TeacherTimeTable solver(TeacherTimeTable timeTable) {
        String jobId = UUID.randomUUID().toString();
        solutionManager.update(timeTable);
        SolverJob<TeacherTimeTable, String> solverJob = solverManager.solve(jobId, timeTable);

        TeacherTimeTable solution;
        try {
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ScoreExplanation<TeacherTimeTable, HardSoftScore> scoreExplanation = solutionManager.explain(solution);
        Map<Object, Indictment<HardSoftScore>> indictmentMap = scoreExplanation.getIndictmentMap();

        for (Batch process : solution.getBatchList()) {
            Indictment<HardSoftScore> indictment = indictmentMap.get(process);
            if (indictment == null) {
                continue;
            }
            // The score impact of that planning entity
            HardSoftScore totalScore = indictment.getScore();

            for (ConstraintMatch<HardSoftScore> constraintMatch : indictment.getConstraintMatchSet()) {
                String constraintName = constraintMatch.getConstraintName();
                HardSoftScore score = constraintMatch.getScore();
                logger.info("BatchId::::::::::{},Constraint name ::::::{},:::::::Score{}", process.getId(), constraintName, score);
            }
        }

        return solution;
    }
}
