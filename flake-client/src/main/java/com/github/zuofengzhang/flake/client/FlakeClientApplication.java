package com.github.zuofengzhang.flake.client;

import com.github.zuofengzhang.flake.client.utils.DbCheck;
import javafx.application.Application;
import javafx.scene.Node;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.InjectionPointLazyFxControllerAndViewResolver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ResourceBundle;

@SpringBootApplication
@MapperScan("com.github.zuofengzhang.flake.client.dao")
@EnableScheduling
@Slf4j
public class FlakeClientApplication {
    public static void main(String[] args) {
        FlakeContext.init();
        DbCheck.check();
        Application.launch(FlakeClientDashboard.class, args);
    }

    @Bean
    public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
        log.info("init fxWeaver");
        return new SpringFxWeaver(applicationContext);
    }

    @Bean
    public ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("flake-client");
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public <C, V extends Node> FxControllerAndView<C, V> controllerAndView(FxWeaver fxWeaver,
                                                                           InjectionPoint injectionPoint) {
        return new InjectionPointLazyFxControllerAndViewResolver(fxWeaver)
                .resolve(injectionPoint);
    }
}
