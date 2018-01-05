package com.poc.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Bala on 05/01/2018.
 */
@SpringBootApplication
public class CadsSolaceMain implements CommandLineRunner{

    @Autowired
    public CadsSolaceService cadsSolaceService;

    public static void main(String args[]) throws Exception{
        SpringApplication app = new SpringApplication(CadsSolaceMain.class);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        cadsSolaceService.subscribeSolaceTopic();

    }
}
