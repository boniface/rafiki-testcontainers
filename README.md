# Rafiki Testcontainers

A reusable [Testcontainers](https://www.testcontainers.org/) wrapper for [Rafiki](https://rafiki.dev/) - the open-source Interledger service for wallet providers.

## Features

- **Automated Docker Setup** - Starts Rafiki backend with all dependencies (PostgreSQL, Redis, TigerBeetle)
- **Fast Integration Tests** - Reusable container instances for testing
- **Automatic Cleanup** - Containers are automatically removed after tests
- **Simple API** - Easy-to-use wrapper around Rafiki's docker-compose

## Requirements

- Java 17 or higher
- Docker installed and running
- Gradle 8.5+

## Installation

Add the dependency to your `build.gradle.kts`:

```kotlin
dependencies {
    testImplementation("zm.hashcode:rafiki-testcontainers:0.1.0")
}
```

Or for Maven (`pom.xml`):

```xml
<dependency>
    <groupId>zm.hashcode</groupId>
    <artifactId>rafiki-testcontainers</artifactId>
    <version>0.1.0</version>
    <scope>test</scope>
</dependency>
```

## Usage

### Basic Example

```java
import org.junit.jupiter.api.*;
import zm.hashcode.rafiki.RafikiCompose;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyRafikiIntegrationTest {

    private RafikiCompose rafiki;

    @BeforeAll
    void setup() {
        rafiki = new RafikiCompose();
        rafiki.start();
    }

    @AfterAll
    void teardown() {
        rafiki.stop();
    }

    @Test
    void testRafikiBackend() {
        String adminUrl = rafiki.backendAdminUrl();

        // Make HTTP requests to test Rafiki integration
        // Example: Create wallet addresses, test payments, etc.
    }
}
```

### Available Methods

- `backendAdminUrl()` - Admin API endpoint (GraphQL)
- `backendOpenPaymentsUrl()` - Open Payments API endpoint
- `backendConnectorUrl()` - ILP Connector endpoint

## What's Included

The testcontainer starts the following services:

- **Rafiki Backend** - Core Rafiki service with all three ports exposed
- **PostgreSQL 16** - Database for backend data
- **Redis 7** - Caching and session management
- **TigerBeetle** - High-performance accounting database

## What's NOT Included

For simplicity, this testcontainer does **not** include:

- **Rafiki Auth Service** - Requires complex Kratos identity server setup
- **Rafiki Frontend** - Admin UI (depends on auth service)

For testing authentication flows, please refer to the [official Rafiki documentation](https://rafiki.dev/integration/deployment/docker-compose/).

## Configuration

The testcontainer uses sensible defaults:

- Backend runs with **authentication disabled** for easier testing
- Database credentials: `postgres:password`
- All services use in-memory/ephemeral storage
- Containers are automatically cleaned up after tests

## Example Test Output

```
 Rafiki containers started successfully
Testing Backend Admin URL: http://localhost:51617
Testing Backend Open Payments URL: http://localhost:51616
Testing Backend Connector URL: http://localhost:51618
 All backend service URLs are valid and accessible
 Backend Admin responded with HTTP 404
 Rafiki containers stopped
```

## Running Tests

```bash
./gradlew test
```

## Troubleshooting

### Containers fail to start

Ensure Docker is running:
```bash
docker ps
```

### Port conflicts

The testcontainer automatically assigns random ports. Check your test output for the actual ports being used.

### Slow startup

First run downloads Docker images (~500MB). Subsequent runs use cached images and start much faster.

## Contributing

Contributions are welcome! Please submit issues and pull requests on GitHub.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Links

- [Rafiki Documentation](https://rafiki.dev/)
- [Testcontainers](https://www.testcontainers.org/)
- [Interledger Protocol](https://interledger.org/)
