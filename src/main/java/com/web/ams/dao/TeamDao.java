package com.web.ams.dao;

import com.web.ams.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamDao extends JpaRepository<Team, Long> {
    public Team findByName(String name);

}
