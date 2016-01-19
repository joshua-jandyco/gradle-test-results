package com.jandyco

import org.gradle.api.tasks.testing.TestResult;

/**
 * Total passed, failed and skipped tests for all projects in the current build,
 * for a specific test task.
 */
class TestCounts {
    // Was the task that this count is for run during the build
    boolean taskWasRun = false;
    // The name of the task this count is for (Type of Test)
    String taskName

    long failed = 0
    long passed  = 0
    long skipped = 0

    /**
     * Create a test count object to represent counts for a task with the provided name
     *
     * @param taskName The name of the task this object counts tests for.
     */
    public TestCounts(String taskName) {
        this.taskName = taskName
    }

    /*
     * Combine the counts of the TestResult with this object
     */
    def addResult(TestResult result) {
        failed += result.getFailedTestCount()
        passed += result.getSuccessfulTestCount()
        skipped += result.getSkippedTestCount()
    }

    /**
     * Combine the counts of the provided counts object with this object
     */
    def addCounts(TestCounts counts) {
        failed += counts.failed
        passed += counts.passed
        skipped += counts.skipped
    }

    /**
     * Total number of tests run
     */
    def totalTestsRun() {
        return failed + passed
    }
}
