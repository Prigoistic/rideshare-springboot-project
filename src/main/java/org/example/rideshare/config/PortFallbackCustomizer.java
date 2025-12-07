package org.example.rideshare.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Ensures the application can start even if the configured HTTP port is busy.
 *
 * Behavior:
 * - If the configured port is available, uses it.
 * - If it's busy, tries the next ports (up to +20). If none are free, falls back to 0 (random free port).
 *
 * The chosen port is logged at startup.
 */
@Configuration
public class PortFallbackCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    private static final Logger log = LoggerFactory.getLogger(PortFallbackCustomizer.class);

    @Value("${server.port:8081}")
    private int configuredPort;

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        // If user explicitly set 0, keep it (let OS choose a random port)
        if (configuredPort == 0) {
            log.info("Server port is configured as 0 (random). The OS will select a free port.");
            return;
        }

        if (isPortAvailable(configuredPort)) {
            // Happy path: the chosen port is free
            factory.setPort(configuredPort);
            log.info("Using configured server port {}", configuredPort);
            return;
        }

        // Try a handful of subsequent ports as a friendly fallback
        final int maxAttempts = 20;
        for (int i = 1; i <= maxAttempts; i++) {
            int candidate = configuredPort + i;
            if (isPortAvailable(candidate)) {
                factory.setPort(candidate);
                log.warn("Configured port {} is busy. Falling back to available port {}.", configuredPort, candidate);
                return;
            }
        }

        // Final fallback: let OS pick any free port
        factory.setPort(0);
        log.warn("Configured port {} is busy and no free port found in the next {} ports. Falling back to a random free port.", configuredPort, maxAttempts);
    }

    private boolean isPortAvailable(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            socket.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
