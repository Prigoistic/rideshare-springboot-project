package org.example.rideshare.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.stream.Collectors;

@Configuration
public class EndpointLogger {

    private static final Logger log = LoggerFactory.getLogger(EndpointLogger.class);

    @Bean
    public CommandLineRunner logEndpoints(RequestMappingHandlerMapping handlerMapping) {
        return args -> {
            try {
                var mappings = handlerMapping.getHandlerMethods();
                log.info("Registered request mappings (method -> patterns):");
                for (var entry : mappings.entrySet()) {
                    RequestMappingInfo info = entry.getKey();
                    var methods = info.getMethodsCondition().getMethods()
                            .stream().map(Enum::name).collect(Collectors.joining(","));
                    var patterns = info.getPathPatternsCondition() != null
                            ? info.getPathPatternsCondition().getPatternValues()
                            : info.getPatternsCondition().getPatterns().stream().map(Object::toString).collect(Collectors.toSet());
                    log.info("{} -> {}", methods.isEmpty() ? "[ANY]" : methods, patterns);
                }
            } catch (Exception e) {
                log.warn("Failed to log endpoints: {}", e.getMessage());
            }
        };
    }
}
