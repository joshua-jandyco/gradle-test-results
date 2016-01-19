package com.jandyco

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestDescriptor;
import org.gradle.api.tasks.testing.TestListener;
import org.gradle.api.tasks.testing.TestResult;

/**
 * Collects the test results for each test task run
 */
class TestResultsListener implements TestListener {
    TestCounts counts;

    public TestResultsListener(Test task) {
        counts = new TestCounts(task.name)
    }

    @Override
    public void afterSuite(TestDescriptor suite, TestResult result) {
        counts.taskWasRun = true
        String className = suite.getClassName();
        if (className != null) { //Ignore Gradle Worker
            counts.addResult(result)
        }
    }

    @Override
    public void beforeSuite(TestDescriptor suite) { }

    @Override
    public void beforeTest(TestDescriptor test) { }

    @Override
    public void afterTest(TestDescriptor test, TestResult result) {}
}

