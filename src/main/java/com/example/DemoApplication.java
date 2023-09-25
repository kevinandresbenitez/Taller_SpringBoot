package com.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@EnableJpaRepositories(basePackages = {"pl.hypeapp.episodie.dataproviders"})
@EntityScan(basePackages = {"pl.hypeapp.core.entity"})
@SpringBootApplication(scanBasePackages = {"boot.registration","com.example.controllers"} , exclude = JpaRepositoriesAutoConfiguration.class)
@RestController
public class DemoApplication {
    public static void main(String[] args) {
      SpringApplication.run(DemoApplication.class, args);
    }
    
    @GetMapping("/")
    public String hello(){
        return "Hello";
    }
}
