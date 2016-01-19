package com.jandyco

import org.gradle.api.tasks.testing.TestResult;

class FakeTestResult implements TestResult {
    long passed
    long failed
    long skipped

    public FakeTestResult(passed, failed, skipped) {
        this.passed = passed
        this.failed = failed
        this.skipped = skipped
    }

    long getEndTime() {}
    Throwable getException() {}
    List<Throwable>	getExceptions() {}
    long getFailedTestCount() { return failed }
    TestResult.ResultType getResultType() {}
    long getSkippedTestCount() { return skipped }
    long getStartTime() {}
    long getSuccessfulTestCount() { return passed }
    long getTestCount() {}
}
