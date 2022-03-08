package com.web.ams.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.web.ams.dao.TeamDao;
import com.web.ams.entity.Developer;
import com.web.ams.entity.Team;
import com.web.ams.response.GenericResponse;
import com.web.ams.response.RequestValidations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.web.ams.dao.DeveloperDao;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Service
public class AlertService {
    public static final Logger logger = LogManager.getLogger(AlertService.class);

    @Autowired
    private CommonUtilsService utilsService;

    @Autowired
    private DeveloperDao developerDao;

    @Autowired
    private TeamDao teamDao;

    private GenericResponse genericResponse;

    public GenericResponse sendAlertToTeamMember(Long teamId){
        logger.info("AlertService :  sendAlertToTeamMember(): started");

        Team team = teamDao.getById(teamId);
        List<Developer> developers = developerDao.findAllByTeamId(team);

        String devNumber = null;
        if(developers != null && developers.size() > 1) {
            devNumber = utilsService.getRandomDeveloperNumber(developers);
        } else if(developers.size() == 1){
            devNumber = developers.get(0).getPhone();
        } else {
            genericResponse =  utilsService.createResponse("NO Developers to alert!",HttpStatus.OK.value(), null,null);
        }

        if(devNumber != null){
            genericResponse = utilsService.callAlertRestApi(devNumber);
        } else {
            genericResponse = utilsService.createResponse("NO Developers Number to alert!",HttpStatus.OK.value(), null,null);
        }
        logger.info("AlertService :  sendAlertToTeamMember(): End");
        return genericResponse;
    }

}
