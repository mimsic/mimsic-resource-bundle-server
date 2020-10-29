package com.github.mimsic.rbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.github.mimsic.rbs"
})
public class RbsApplicationLauncher {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(RbsApplicationLauncher.class, args);
    }
}
