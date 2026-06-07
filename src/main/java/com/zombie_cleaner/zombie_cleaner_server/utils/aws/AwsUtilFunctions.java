package com.zombie_cleaner.zombie_cleaner_server.utils.aws;

import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import com.zombie_cleaner.zombie_cleaner_server.services.impl.EnvironmentServiceImpl;
import com.zombie_cleaner.zombie_cleaner_server.utils.AuthenticationUtil;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;

public class AwsUtilFunctions {

    @Autowired
    AuthenticationUtil authenticationUtil;

    @Autowired
    EnvironmentServiceImpl environmentService;
    public String getExternalId(String environmentId) throws AuthenticationException {
        Long currentUserId = authenticationUtil.getCurrentUserId();

        Environment environment= environmentService.getEnvironmentById(environmentId, currentUserId);
        return environment.getExternalId();
    }
}
