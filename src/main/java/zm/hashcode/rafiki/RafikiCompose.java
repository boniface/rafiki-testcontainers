package zm.hashcode.rafiki;

import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;
import java.util.Objects;

/**
 * Spins up the Rafiki stack for integration testing using docker-compose.
 * Authentication remains ENABLED — we do NOT set AUTH_ENABLED=false.
 */
public class RafikiCompose implements AutoCloseable {

    private final ComposeContainer compose;

    public RafikiCompose() {
        // Use classpath resource directly
        this.compose = new ComposeContainer(
                new File(Objects.requireNonNull(
                        getClass().getClassLoader().getResource("rafiki/docker-compose.yml"),
                        "docker-compose.yml not found in classpath"
                ).getFile())
        )
                .withLocalCompose(true)  // Use local docker-compose for better compatibility
                // Wait only for backend services - auth and frontend require additional Kratos setup
                .withExposedService("rafiki-backend-1", 3000,
                    Wait.forListeningPort().withStartupTimeout(Duration.ofMinutes(3)))
                .withExposedService("rafiki-backend-1", 3001,
                    Wait.forListeningPort().withStartupTimeout(Duration.ofMinutes(3)))
                .withExposedService("rafiki-backend-1", 3002,
                    Wait.forListeningPort().withStartupTimeout(Duration.ofMinutes(3)));
    }

    public void start() {
        compose.start();
    }

    public void stop() {
        compose.stop();
    }

    public String backendOpenPaymentsUrl() {
        return getServiceUrl("rafiki-backend-1", 3000);
    }

    public String backendAdminUrl() {
        return getServiceUrl("rafiki-backend-1", 3001);
    }

    public String backendConnectorUrl() {
        return getServiceUrl("rafiki-backend-1", 3002);
    }

    public String authServerUrl() {
        return getServiceUrl("rafiki-auth-1", 3006);
    }

    public String frontendUrl() {
        return getServiceUrl("rafiki-frontend-1", 3005);
    }

    private String getServiceUrl(String serviceName, int port) {
        try {
            int mappedPort = compose.getServicePort(serviceName, port);
            return "http://localhost:" + mappedPort;
        } catch (IllegalStateException e) {
            // Container not started yet, return placeholder
            return "http://localhost:" + port + " (not started)";
        }
    }

    @Override
    public void close() {
        stop();
    }
}
