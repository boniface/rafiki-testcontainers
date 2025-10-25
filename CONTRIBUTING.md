# Contributing to Rafiki Testcontainers

Thank you for your interest in contributing to Rafiki Testcontainers! This document provides guidelines and instructions for contributing to this project.

## Code of Conduct

By participating in this project, you agree to maintain a respectful and inclusive environment for all contributors.

## Getting Started

### Prerequisites

- Java 25 (via Temurin distribution)
- Gradle (wrapper included)
- Docker (for running Testcontainers tests)
- Git

### Setting Up Development Environment

1. Fork the repository on GitHub
2. Clone your fork locally:
   ```bash
   git clone https://github.com/YOUR-USERNAME/rafiki-testcontainers.git
   cd rafiki-testcontainers
   ```

3. Add upstream remote:
   ```bash
   git remote add upstream https://github.com/hashcode-zm/rafiki-testcontainers.git
   ```

4. Build the project:
   ```bash
   ./gradlew clean build
   ```

5. Run tests:
   ```bash
   ./gradlew test
   ```

## How to Contribute

### Reporting Bugs

- Use the GitHub issue tracker
- Check if the issue already exists
- Include:
  - Clear description of the problem
  - Steps to reproduce
  - Expected vs actual behavior
  - Java version, OS, and relevant environment details
  - Stack traces or error messages

### Suggesting Enhancements

- Use the GitHub issue tracker
- Clearly describe the enhancement
- Explain why it would be useful
- Provide examples if applicable

### Pull Requests

1. **Create a feature branch**:
   ```bash
   git checkout -b feat/your-feature-name
   ```
   or
   ```bash
   git checkout -b fix/your-bug-fix
   ```

2. **Make your changes**:
   - Write clean, readable code
   - Follow existing code style
   - Add tests for new functionality
   - Update documentation as needed

3. **Commit your changes**:
   - Follow [Conventional Commits](https://www.conventionalcommits.org/) format
   - Use meaningful commit messages
   - Examples:
     ```
     feat: add support for custom Rafiki configuration
     fix: resolve container startup timeout issue
     docs: update README with new examples
     test: add integration tests for networking
     refactor: simplify container initialization logic
     chore: update dependencies
     ```

4. **Keep your branch updated**:
   ```bash
   git fetch upstream
   git rebase upstream/main
   ```

5. **Run tests**:
   ```bash
   ./gradlew clean test
   ```

6. **Push to your fork**:
   ```bash
   git push origin feat/your-feature-name
   ```

7. **Create a Pull Request**:
   - Provide a clear title and description
   - Reference any related issues
   - Ensure all CI checks pass

## Commit Message Guidelines

We use [Conventional Commits](https://www.conventionalcommits.org/) for clear and organized changelog generation.

### Format

```
<type>: <subject>

[optional body]

[optional footer]
```

### Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `test`: Adding or updating tests
- `refactor`: Code changes that neither fix bugs nor add features
- `perf`: Performance improvements
- `chore`: Changes to build process, dependencies, or tooling
- `ci`: Changes to CI configuration

### Examples

```
feat: add support for PostgreSQL configuration

Allows users to customize PostgreSQL container settings
including port, database name, and credentials.

Closes #123
```

```
fix: resolve container startup race condition

Adds proper wait strategies to ensure containers are fully
initialized before tests execute.
```

## Code Style

- Follow standard Java conventions
- Use meaningful variable and method names
- Keep methods focused and concise
- Write self-documenting code
- Add comments for complex logic
- Maintain consistent indentation (4 spaces)

## Testing

- Write tests for all new functionality
- Ensure existing tests still pass
- Aim for good test coverage
- Use descriptive test method names
- Follow the Arrange-Act-Assert pattern

## Documentation

- Update README.md if adding new features
- Add JavaDoc comments for public APIs
- Include code examples where helpful
- Keep documentation up to date

## Review Process

1. All submissions require review
2. Maintainers will review your PR
3. Address any feedback or requested changes
4. Once approved, your PR will be merged

## Release Process

This project uses automated releases with JReleaser:
- Maintainers create version tags (e.g., `v0.2.0`)
- GitHub releases are created automatically
- CHANGELOG.md is updated automatically
- Artifacts are published to Maven Central

## Questions?

If you have questions, feel free to:
- Open an issue for discussion
- Reach out to the maintainers

## License

By contributing, you agree that your contributions will be licensed under the Apache License 2.0.

---

Thank you for contributing to Rafiki Testcontainers!
