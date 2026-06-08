package com.zombie_cleaner.zombie_cleaner_server.utils.aws;

import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import com.zombie_cleaner.zombie_cleaner_server.services.EnvironmentService;
import com.zombie_cleaner.zombie_cleaner_server.services.impl.EnvironmentServiceImpl;
import com.zombie_cleaner.zombie_cleaner_server.utils.AuthenticationUtil;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AwsUtilFunctions {

    @Autowired
    AuthenticationUtil authenticationUtil;

    @Autowired
    EnvironmentService environmentService;
    public String getExternalId(String environmentId) throws AuthenticationException {
        Environment environment= environmentService.getEnvironmentById(environmentId);
        return environment.getExternalId();
    }
}
