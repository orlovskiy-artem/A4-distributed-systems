package com.orlovsky.mooc_platform.acccount_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
//@EnableDiscoveryClient
//@RibbonClient(name = "account-service")
//@EnableEurekaClient
public class AcccountServiceApplication {

    public static void main(String[] args)   {
        SpringApplication.run(AcccountServiceApplication.class, args);
    }

}
