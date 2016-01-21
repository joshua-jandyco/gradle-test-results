Test Results Plugin
=====================
[![Build Image](https://snap-ci.com/joshua-jandyco/gradle-test-results/branch/master/build_image)](https://snap-ci.com/joshua-jandyco/gradle-test-results/branch/master)

Prints a summary of run, failed and skipped tests at the end of the build.
The test results summary currently shows:

* Summary for each test type run (e.g test, integTest, etc)
* Total of all tests run

Example Output:

    ...
    BUILD SUCCESSFUL

    Total time: 10.577 secs

    --------------------------------------------------------------------------------
    TEST RESULTS
    --------------------------------------------------------------------------------
    test: Tests Run: 6, Failures: 3, Skipped: 0
    integrationTest: Tests Run: 2, Failures: 1, Skipped: 0
    --------------------------------------------------------------------------------
    Total: Tests Run: 8, Failures: 4, Skipped: 0

Usage
=======================
Put the following in your ~/.gradle/init.gradle so that it is available for all
of your projects.

```gradle
apply plugin: 'com.jandyco.test-results'
...
buildscripts {
    repositories {
        maven {
            url "https://plugins.gradle.org/m2/"
        }
    }
    dependencies {
        classpath 'org.jandyco:test-results:1.0.0'
    }
}
```

For Gradle 2.0+, you can use the Plugins DSL:
```gradle
plugins {
    id "org.jandyco.test-results" version "1.0.0"
}
```

