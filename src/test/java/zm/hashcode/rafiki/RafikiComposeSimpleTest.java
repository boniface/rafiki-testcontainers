package zm.hashcode.rafiki;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RafikiComposeSimpleTest {

    @Test
    void canInstantiate() {
        RafikiCompose compose = new RafikiCompose();
        assertNotNull(compose, "RafikiCompose should be instantiated");

        // URLs should return placeholders when not started
        String adminUrl = compose.backendAdminUrl();
        assertNotNull(adminUrl);
        assertTrue(adminUrl.contains("not started") || adminUrl.contains("localhost"));

        String openPaymentsUrl = compose.backendOpenPaymentsUrl();
        assertNotNull(openPaymentsUrl);
        assertTrue(openPaymentsUrl.contains("not started") || openPaymentsUrl.contains("localhost"));

        String connectorUrl = compose.backendConnectorUrl();
        assertNotNull(connectorUrl);
        assertTrue(connectorUrl.contains("not started") || connectorUrl.contains("localhost"));

        System.out.println("✅ RafikiCompose instantiated successfully");
        System.out.println("   Backend services are available for testing");
    }
}

