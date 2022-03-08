package com.web.ams.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.web.ams.entity.Developer;
import com.web.ams.response.GenericResponse;
import com.web.ams.response.RequestValidations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CommonUtilsService {

    public static final Logger logger = LogManager.getLogger(CommonUtilsService.class);

    public GenericResponse validateFields(Errors errors){
        GenericResponse response = new GenericResponse();
        List<FieldError> fieldList = errors.getFieldErrors();
        List<RequestValidations> validations = new ArrayList<>();
        for (FieldError fieldError : fieldList) {
            RequestValidations requestValidations = new RequestValidations();
            requestValidations.setMessage(fieldError.getDefaultMessage());
            requestValidations.setField(fieldError.getField());
            validations.add(requestValidations);
        }

        return createResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(),HttpStatus.BAD_REQUEST.value(),null, validations);
    }

    public String getRandomDeveloperNumber(List<Developer> developers){
        logger.info("CommonUtilsService :  getRandomDeveloperNumber(): started");

        Random random = new Random();
        int index = random.nextInt(developers.size());

        logger.info("CommonUtilsService :  getRandomDeveloperNumber(): end with index {}", index);
        return developers.get(index).getPhone();
    }

    public GenericResponse createResponse(String status, Integer statusCode, String teamId, List<RequestValidations> requestValidationsList) {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setStatus(status);
        genericResponse.setStatusCode(statusCode);
        genericResponse.setRequestValidationsList(requestValidationsList);
        genericResponse.setTeamId(teamId);
        return genericResponse;
    }

    public GenericResponse callAlertRestApi(String devNumber){
        GenericResponse genericResponse = null;
        ResponseEntity<JsonNode> alertResponse = null;
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        ObjectNode request = mapper.createObjectNode();
        request.put("phone_number", devNumber);
        HttpEntity entity = new HttpEntity(request);
        try {
            logger.info("AlertService :  sendAlertToTeamMember(): calling rest template");
            alertResponse = restTemplate.exchange("https://run.mocky.io/v3/fd99c100-f88a-4d70-aaf7-393dbbd5d99f", HttpMethod.POST, entity, JsonNode.class);

            if (alertResponse != null && HttpStatus.OK.equals(alertResponse.getStatusCode()) && alertResponse.getBody() != null){
                genericResponse = createResponse(alertResponse.getBody().toString(), alertResponse.getStatusCode().value(), null,null);
            } else {
                genericResponse = createResponse("Error at alert service",HttpStatus.INTERNAL_SERVER_ERROR.value(), null,null);
            }
        } catch (Exception e){
            genericResponse = createResponse("Error while sending alert",HttpStatus.INTERNAL_SERVER_ERROR.value(), null,null);
            logger.info("AlertService :  sendAlertToTeamMember(): Exception ",e);
        }

        return genericResponse;
    }

}
