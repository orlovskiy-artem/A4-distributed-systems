package com.orlovsky.mooc_platform;

import com.orlovsky.mooc_platform.service.grpc.GrpcAccountService;
import io.grpc.ServerBuilder;
import io.grpc.channelz.v1.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoocPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoocPlatformApplication.class, args);
    }
}
