package zm.hashcode.rafiki;

import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;
import java.util.Objects;

/**
 * Testcontainers wrapper for Rafiki - the open-source Interledger service.
 * <p>
 * This class manages a Docker Compose stack containing:
 * <ul>
 *   <li>Rafiki Backend (Open Payments, Admin API, ILP Connector)</li>
 *   <li>PostgreSQL database</li>
 *   <li>Redis cache</li>
 *   <li>TigerBeetle accounting database</li>
 * </ul>
 * <p>
 * Note: Authentication and Frontend services are not included in this testcontainer
 * configuration for simplicity. They require additional Kratos identity server setup.
 *
 * <h2>Example Usage:</h2>
 * <pre>{@code
 * @TestInstance(TestInstance.Lifecycle.PER_CLASS)
 * public class MyIntegrationTest {
 *     private RafikiCompose rafiki;
 *
 *     @BeforeAll
 *     void setup() {
 *         rafiki = new RafikiCompose();
 *         rafiki.start();
 *     }
 *
 *     @AfterAll
 *     void teardown() {
 *         rafiki.stop();
 *     }
 *
 *     @Test
 *     void testBackend() {
 *         String adminUrl = rafiki.backendAdminUrl();
 *         // Make HTTP requests to test Rafiki
 *     }
 * }
 * }</pre>
 *
 * @see <a href="https://rafiki.dev">Rafiki Documentation</a>
 * @see <a href="https://github.com/hashcode-zm/rafiki-testcontainers">GitHub Repository</a>
 * @since 0.1.0
 */
public class RafikiCompose implements AutoCloseable {

    private final ComposeContainer compose;

    /**
     * Creates a new Rafiki testcontainer instance.
     * <p>
     * The containers are not started until {@link #start()} is called.
     * All services are configured to wait up to 3 minutes for startup.
     */
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

    /**
     * Starts all Rafiki containers and waits for them to be ready.
     * <p>
     * This method blocks until all backend services are listening on their ports
     * (up to 3 minutes per service). On first run, Docker images will be downloaded.
     *
     * @throws org.testcontainers.containers.ContainerLaunchException if containers fail to start
     */
    public void start() {
        compose.start();
    }

    /**
     * Stops and removes all Rafiki containers.
     * <p>
     * This method cleans up all containers, networks, and volumes created by this instance.
     */
    public void stop() {
        compose.stop();
    }

    /**
     * Returns the URL for the Rafiki Backend Open Payments API endpoint.
     * <p>
     * This endpoint handles Open Payments protocol operations (wallet addresses, quotes, payments).
     *
     * @return the Open Payments API URL (e.g., "http://localhost:51234")
     * @see <a href="https://openpayments.guide">Open Payments Specification</a>
     */
    public String backendOpenPaymentsUrl() {
        return getServiceUrl("rafiki-backend-1", 3000);
    }

    /**
     * Returns the URL for the Rafiki Backend Admin API endpoint.
     * <p>
     * This GraphQL endpoint provides administrative operations for managing
     * wallet addresses, peers, and viewing account balances.
     *
     * @return the Admin API URL (e.g., "http://localhost:51235")
     */
    public String backendAdminUrl() {
        return getServiceUrl("rafiki-backend-1", 3001);
    }

    /**
     * Returns the URL for the Rafiki Backend ILP Connector endpoint.
     * <p>
     * This endpoint handles Interledger Protocol (ILP) packet routing.
     *
     * @return the ILP Connector URL (e.g., "http://localhost:51236")
     * @see <a href="https://interledger.org">Interledger Protocol</a>
     */
    public String backendConnectorUrl() {
        return getServiceUrl("rafiki-backend-1", 3002);
    }

    /**
     * Returns the URL for the Rafiki Auth Server endpoint.
     * <p>
     * <strong>Note:</strong> The auth service is not included in this testcontainer.
     * This method is provided for API compatibility but will return a placeholder URL.
     *
     * @return a placeholder URL (service not running in testcontainer)
     * @deprecated Auth service not included in testcontainer configuration
     */
    @Deprecated
    public String authServerUrl() {
        return getServiceUrl("rafiki-auth-1", 3006);
    }

    /**
     * Returns the URL for the Rafiki Frontend admin interface.
     * <p>
     * <strong>Note:</strong> The frontend service is not included in this testcontainer.
     * This method is provided for API compatibility but will return a placeholder URL.
     *
     * @return a placeholder URL (service not running in testcontainer)
     * @deprecated Frontend service not included in testcontainer configuration
     */
    @Deprecated
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
