package com.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;



@EnableJpaRepositories(basePackages = {"pl.hypeapp.episodie.dataproviders"})
@EntityScan(basePackages = {"pl.hypeapp.core.entity"})
@SpringBootApplication(scanBasePackages = {"boot.registration","com.example.controllers"} , exclude = JpaRepositoriesAutoConfiguration.class)
@Controller
public class DemoApplication {
    public static void main(String[] args) {
      SpringApplication.run(DemoApplication.class, args);
    }
    
    @GetMapping("/")
    public ModelAndView hello(){
        //Cargando pagina de resources/templates/index.html
        return new ModelAndView("index");
    }
}
