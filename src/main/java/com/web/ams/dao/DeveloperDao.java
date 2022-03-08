package com.web.ams.dao;

import com.web.ams.entity.Developer;
import com.web.ams.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeveloperDao extends JpaRepository<Developer, Long> {
    public Developer findByName(String name);
    public Developer findByNameAndTeamId(String name, Team teamId);
    public List<Developer> findAllByTeamId(Team teamId);
}
