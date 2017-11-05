# Satisfy Framework

Satisfy is test automation framework.

## Features

* Easy to use for non-developers
* Interact with any Web UI
* Auto generated reports
* Parallel test execution
* Extendable throw SPI
* Ready to use out of the box
* Built on JBehave and Thucydides
* Compatible with all modern browsers


## Building

This will build the framework, publish it to a local artifactory repository, then resolve the dependencies of the project. 


### Requirements

- [Java JDK (1.8+)] [java]
- [Apache Maven (3.2.1+)] [apache-maven]


### Steps

1. Download the source code from git: `git clone git@github.com:tapack/satisfy.git`
2. Open a command line in the cloned directory: `cd ./satisfy`
3. Export MAVEN_OPTS variable: `export MAVEN_OPTS=-XX:MaxPermSize=128m` (`set MAVEN_OPTS=-XX:MaxPermSize=128m` for windows)
4. Now run the maven to build the atom: `mvn clean install -P build-with-code-quality-check,build-with-functional-check`
    - The library has now been built, published to a local artifactory repository, the app has had its dependencies resolved.


#### Maven Profiles:

- `build-with-code-coverage-check` - provide additional execution of the maven plugins that corresponding for code coverage check (e.g. `jacoco-maven-plugin`)
- `build-with-code-quality-check` - provide additional execution of the maven plugins that corresponding for code quality check (e.g. `maven-checkstyle-plugin`, `maven-pmd-plugin`, `findbugs-maven-plugin`)
- `build-with-functional-check` - provide additional execution of the maven plugins that corresponding for functional check (e.g. `maven-failsafe-plugin`, `maven-cargo2-plugin`)

This project is licensed under the terms of the MIT license.

[java]: http://www.oracle.com/technetwork/java/javase/downloads/index.html "Java"
[apache-maven]: http://maven.apache.org/download.cgi "Apache Maven"