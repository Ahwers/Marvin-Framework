package com.ahwers.marvin.framework.application.state;

import java.util.Set;

import com.ahwers.marvin.framework.application.Application;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApplicationStateFactory {

    private Set<Application> supportedApps;

    public ApplicationStateFactory(Set<Application> supportedApps) {
        this.supportedApps = supportedApps;
    }

    public ApplicationState unmarshallApplicationStateForApplication(String marshalledAppState, String applicationName) {
        Application app = getApplicationWithName(applicationName);
        Class<? extends ApplicationState> appStateClass = app.getStateClass();

        ApplicationState appState = null;
        try {
            appState = new ObjectMapper().readValue(marshalledAppState, appStateClass);
        } catch (JsonProcessingException e) {
            // TODO: Throw a server exception somehow
            e.printStackTrace();
        }

        return appState;
    }

    private Application getApplicationWithName(String appName) {
        Application targetApp = null;

        for (Application app : this.supportedApps) {
            if (app.getName().equals(appName)) {
                targetApp = app;
            }
        }

        if (targetApp == null) {
            throw new IllegalArgumentException("An application with name '" + appName + "' has not been loaded.");
        }

        return targetApp;
    }

    public Set<Application> getSupportedApplications() {
        return this.supportedApps;
    }
    
}
