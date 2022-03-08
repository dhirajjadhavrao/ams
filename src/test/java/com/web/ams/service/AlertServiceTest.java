package com.web.ams.service;

import com.web.ams.dao.DeveloperDao;
import com.web.ams.dao.TeamDao;
import com.web.ams.entity.Developer;
import com.web.ams.entity.Team;
import com.web.ams.response.GenericResponse;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;


@WebMvcTest(AlertService.class)
@RunWith(SpringRunner.class)
class AlertServiceTest {

    @MockBean
    private CommonUtilsService utilsService;
    @MockBean
    private DeveloperDao developerDao;
    @MockBean
    private TeamDao teamDao;
    @MockBean
    private GenericResponse genericResponse;
    @MockBean
    private CrudService crudService;

    @InjectMocks
    private AlertService alertService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void sendAlertToTeamMember() {

        List<Developer> developers = new ArrayList<>();
        Developer developer = new Developer();
        Team team = new Team();
        team.setId(1l);
        team.setName("TestTeam");
        developer.setTeamId(team);
        developer.setPhone("9999999999");
        developer.setName("TestDeveloper");
        developer.setId(1l);
        developers.add(developer);

        GenericResponse  mockResponse = new GenericResponse();
        mockResponse.setStatus(HttpStatus.OK.toString());
        mockResponse.setStatusCode(HttpStatus.OK.value());

        Mockito.when(teamDao.getById(anyLong())).thenReturn(team);
        Mockito.when(developerDao.findAllByTeamId(any(Team.class))).thenReturn(developers);
        Mockito.when(utilsService.getRandomDeveloperNumber(anyList())).thenReturn(developer.getPhone());
        Mockito.when(utilsService.callAlertRestApi(anyString())).thenReturn(mockResponse);


        GenericResponse response = alertService.sendAlertToTeamMember(1l);

        Assert.assertEquals(mockResponse.getStatusCode(), response.getStatusCode());

    }
}