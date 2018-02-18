package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import requests.UserRequests;



@SpringBootApplication
@ComponentScan(basePackageClasses = UserRequests.class)
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }

}
