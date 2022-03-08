package com.web.ams.service;

import com.web.ams.dao.DeveloperDao;
import com.web.ams.dao.TeamDao;
import com.web.ams.entity.Developer;
import com.web.ams.entity.Team;
import com.web.ams.request.DeveloperVo;
import com.web.ams.request.TeamVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrudService {
    public static final Logger logger = LogManager.getLogger(CrudService.class);

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private DeveloperDao developerDao;

    Team team = null;

    public Long createTeam(TeamVo teamVo){
        logger.info("CrudService :  createTeam(): started");

        try {
            logger.info("CrudService :  createTeam(): Saving team details");
            team = teamDao.findByName(teamVo.getTeamName());
            if(team == null){
                team = new Team();
            }
            team.setName(teamVo.getTeamName());
            team.setCreatedDate(System.currentTimeMillis());
            team.setUpdatedDate(System.currentTimeMillis());
            team = teamDao.save(team);
            if(team != null && team.getId() != null){
                logger.info("CrudService :  createTeam(): After Saving team details team id {} ", team.getId());
                List<DeveloperVo> developers = teamVo.getDevelopers();
                if(developers != null && developers.size() != 0){
                    developers.stream().forEach(developerVo -> {
                        Developer dev = saveDevelopers(developerVo, team);
                        logger.info("CrudService :  createTeam(): After Saving Developer details");
                    });
                }
            }
        } catch (Exception e){
            logger.info("CrudService :  createTeam(): Exception : ",e);
        }

        return 987L;

    }

    public Developer saveDevelopers(DeveloperVo developerVo, Team team){
        Developer dev = null;
        try {

            dev = developerDao.findByNameAndTeamId(developerVo.getName(), team);
            if(dev == null){
                dev = new Developer();
            }
            dev.setName(developerVo.getName());
            dev.setPhone(developerVo.getPhoneNumber());
            dev.setTeamId(team);
            dev.setCreatedDate(System.currentTimeMillis());
            dev.setUpdatedDate(System.currentTimeMillis());

            dev = developerDao.save(dev);
        } catch (Exception e){
            logger.info("CrudService :  saveDevelopers(): Exception : ",e);
            throw e;
        }

        return dev;
    }
}
