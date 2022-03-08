package com.web.ams.controller;

import com.web.ams.dao.DeveloperDao;
import com.web.ams.dao.TeamDao;
import com.web.ams.request.TeamVo;
import com.web.ams.response.GenericResponse;
import com.web.ams.service.AlertService;
import com.web.ams.service.CommonUtilsService;
import com.web.ams.service.CrudService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeamController.class)
@RunWith(SpringRunner.class)
@DisplayName("Test Controller")
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommonUtilsService utilsService;
    @MockBean
    private DeveloperDao developerDao;
    @MockBean
    private TeamDao teamDao;
    @MockBean
    private GenericResponse genericResponse;
    @MockBean
    private AlertService alertService;
    @MockBean
    private CrudService crudService;

    private RequestBuilder requestBuilder;

    @Test
    void createTeam() throws Exception {
        String requestBody = "{\n" +
                "    \"developers\": [\n" +
                "        {\n" +
                "            \"name\": \"thisDev\",\n" +
                "            \"phoneNumber\": \"9561995857\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"dev1\",\n" +
                "            \"phoneNumber\": \"9922004572\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"teamName\": \"TeamAlpha\"\n" +
                "}";

        Mockito.when(crudService.createTeam(any(TeamVo.class))).thenReturn(1l);

        requestBuilder = MockMvcRequestBuilders.post("/team")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void sendAlertToTeamMember() throws Exception {
        GenericResponse  response = new GenericResponse();
        response.setStatus(HttpStatus.OK.toString());
        response.setStatusCode(HttpStatus.OK.value());

        Mockito.when(alertService.sendAlertToTeamMember(any(Long.class))).thenReturn(response);

        requestBuilder = MockMvcRequestBuilders.post("/{teamId}/alert",4)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }
}