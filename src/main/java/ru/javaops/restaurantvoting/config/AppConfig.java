package ru.javaops.restaurantvoting.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.ProjectionDefinitionConfiguration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import ru.javaops.restaurantvoting.repository.projection.LunchWithDetailsProjection;

import java.sql.SQLException;

@Slf4j
@Configuration
public class AppConfig {
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2WebServer() throws SQLException {
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        log.info("Start H2 TCP server");
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    @Bean
    protected Module hibernateJacksonModule() {
        return new Hibernate5Module();
    }

    @Configuration
    public static class CustomRepositoryRestMvcConfiguration implements RepositoryRestConfigurer {
        @Override
        public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            ProjectionDefinitionConfiguration projections = config.getProjectionConfiguration();
            projections.addProjection(LunchWithDetailsProjection.class);
        }
    }
}
