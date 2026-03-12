package com.zombie_cleaner.zombie_cleaner_server.dtos.user.responses;

import com.zombie_cleaner.zombie_cleaner_server.dtos.environment.responses.EnvironmentSummary;
import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import lombok.Data;

import java.util.List;

@Data
public class UserDetailsResponse {
    private String email;
    private List<EnvironmentSummary> environments;
}
