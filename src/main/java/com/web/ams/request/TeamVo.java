package com.web.ams.request;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;

public class TeamVo implements Serializable {
    public final static Long SERIAL_ID = 1L;

    @NotNull(message = "Team Name Should not be null")
    private String teamName;

    @NotNull(message = "Add At least One Developer")
    private List<DeveloperVo> developers;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<DeveloperVo> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<DeveloperVo> developers) {
        this.developers = developers;
    }
}
