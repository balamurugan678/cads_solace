package com.poc.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Bala on 05/01/2018.
 */
@Component
@ConfigurationProperties
public class CadsConfig {

    @Value("${cads.solace-host}")
    private String solaceHost;

    @Value("${cads.solace-vpn}")
    private String solaceVPN;

    @Value("${cads.solace-username}")
    private String solaceUser;

    @Value("${cads.solace-password}")
    private String solacePassword;

    @Value("${cads.solace-treats-topic}")
    private String solaceTopic;

    @Value("${cads.targetDirectory}")
    private String targetDirectory;

    public String getSolaceHost() {
        return solaceHost;
    }

    public void setSolaceHost(String solaceHost) {
        this.solaceHost = solaceHost;
    }

    public String getSolaceVPN() {
        return solaceVPN;
    }

    public void setSolaceVPN(String solaceVPN) {
        this.solaceVPN = solaceVPN;
    }

    public String getSolaceUser() {
        return solaceUser;
    }

    public void setSolaceUser(String solaceUser) {
        this.solaceUser = solaceUser;
    }

    public String getSolacePassword() {
        return solacePassword;
    }

    public void setSolacePassword(String solacePassword) {
        this.solacePassword = solacePassword;
    }

    public String getSolaceTopic() {
        return solaceTopic;
    }

    public void setSolaceTopic(String solaceTopic) {
        this.solaceTopic = solaceTopic;
    }

    public String getTargetDirectory() {
        return targetDirectory;
    }

    public void setTargetDirectory(String targetDirectory) {
        this.targetDirectory = targetDirectory;
    }
}
