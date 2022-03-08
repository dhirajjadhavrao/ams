package com.web.ams.controller;

import com.web.ams.request.TeamVo;
import com.web.ams.response.GenericResponse;
import com.web.ams.service.AlertService;
import com.web.ams.service.CommonUtilsService;
import com.web.ams.service.CrudService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TeamController {
    public static final Logger logger = LogManager.getLogger(TeamController.class);

    @Autowired
    private CrudService crudService;
    @Autowired
    private AlertService alertService;
    @Autowired
    private CommonUtilsService utilsService;

    private GenericResponse response = null;

    @PostMapping("/team")
    public ResponseEntity<?> createTeam(@RequestBody @Valid TeamVo teamVo, Errors errors){

        logger.info("TeamController :  createTeam(): started");
        if (errors != null && errors.hasErrors()) {
            logger.info("Error at TeamController :  postTeam(): Field Validation");
            response = utilsService.validateFields(errors);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }

        Long teamId  = crudService.createTeam(teamVo);
        logger.info("TeamController :  createTeam(): End");
        return ResponseEntity.ok(teamId);
    }

    @PostMapping("/{teamId}/alert")
    public ResponseEntity<?> sendAlertToTeamMember(@PathVariable Long teamId){

        logger.info("TeamController :  sendAlertToTeamMember(): started");

        if(teamId != null) {
            response = alertService.sendAlertToTeamMember(teamId);
        } else {
            response = new GenericResponse();
            response.setStatus("Team Id should not be null or empty");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        }
        logger.info("TeamController :  sendAlertToTeamMember(): End");
        return ResponseEntity.ok(response);
    }


}
