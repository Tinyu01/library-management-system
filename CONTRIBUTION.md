# Contributing to Library Management System

Thank you for your interest in contributing to the Library Management System! This document provides guidelines and instructions for contributing to the project.

## Code of Conduct

By participating in this project, you agree to uphold our Code of Conduct, which ensures a positive and respectful environment for all contributors.

## How to Contribute

### Reporting Bugs

If you find a bug in the application, please create an issue in the GitHub repository with the following information:

1. A clear and descriptive title
2. Steps to reproduce the bug
3. Expected behavior
4. Actual behavior
5. Screenshots (if applicable)
6. Environment details (OS, browser version, etc.)

### Suggesting Enhancements

We welcome suggestions for enhancements to the Library Management System. To suggest an enhancement:

1. Create an issue with a clear and descriptive title
2. Provide a detailed description of the suggested enhancement
3. Explain why this enhancement would be useful to users
4. Include any relevant examples or mockups

### Pull Requests

We encourage contributions through pull requests. To submit a pull request:

1. Fork the repository
2. Create a new branch for your feature or bug fix
3. Make your changes following our coding guidelines
4. Write or update tests as necessary
5. Ensure all tests pass
6. Submit a pull request with a clear description of your changes

## Development Setup

### Prerequisites

- JDK 17 or higher
- Maven 3.8+
- Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)
- Git

### Local Development

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/library-management-system.git
   ```

2. Navigate to the project directory:
   ```
   cd library-management-system
   ```

3. Build the project:
   ```
   mvn clean install
   ```

4. Run the application:
   ```
   mvn spring-boot:run
   ```

The application will be available at `http://localhost:8080`.

## Coding Guidelines

### Java Code Style

- Follow standard Java naming conventions
- Use 4 spaces for indentation
- Write clear and concise Javadoc comments for public methods
- Keep methods focused on single responsibilities
- Use meaningful variable and method names

### Git Workflow

- Create feature branches from `develop`
- Use descriptive branch names (e.g., `feature/add-book-search`, `fix/availability-check`)
- Write clear, concise commit messages
- Keep pull requests focused on a single feature or bug fix

### Testing

- Write unit tests for all new features
- Ensure existing tests pass before submitting a pull request
- Aim for high test coverage, especially for critical components

## Review Process

1. A maintainer will review your pull request
2. Changes may be requested before merging
3. Once approved, your pull request will be merged into the main codebase

## Documentation

- Update documentation for any changes to APIs or functionality
- Add Javadoc comments for new classes and methods
- Update the README.md file if necessary

## Questions?

If you have questions about contributing, please open an issue with the "question" label.

Thank you for contributing to the Library Management System!