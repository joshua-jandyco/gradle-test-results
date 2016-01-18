package com.jandyco

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestDescriptor;
import org.gradle.api.tasks.testing.TestListener;
import org.gradle.api.tasks.testing.TestResult;

class TestResultsListener implements TestListener {
    TestCounts counts;

    public TestResultsListener(Test task) {
        counts = new TestCounts(task.name)
    }

    @Override
    public void beforeSuite(TestDescriptor suite) { }

    @Override
    public void afterSuite(TestDescriptor suite, TestResult result) {
        counts.wasRun = true
        String className = suite.getClassName();
        if (className != null) {
            counts.addResult(result)
        }
    }

    @Override
    public void beforeTest(TestDescriptor test) { }

    @Override
    public void afterTest(TestDescriptor test, TestResult result) {}
}

class TestCounts {
    boolean wasRun = false;
    String type

    long failed = 0
    long passed  = 0
    long skipped = 0

    public TestCounts(String type) {
        this.type = type
    }

    def addResult(TestResult result) {
        failed += result.getFailedTestCount()
        passed += result.getSuccessfulTestCount()
        skipped += result.getSkippedTestCount()
    }

    def addCounts(TestCounts counts) {
        failed += counts.failed
        passed += counts.passed
        skipped += counts.skipped
    }

    def total() {
        return failed + passed
    }
}
