package com.jandyco

import org.gradle.BuildResult
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.logging.Logging
import org.gradle.api.logging.Logger

class BuildListener implements org.gradle.BuildListener {
    private static Logger logger = Logging.getLogger(BuildListener.class)
    private static final String SEPARATOR = "-".multiply(80)
    def testCounts

    public BuildListener(testCounts) {
        this.testCounts = testCounts
    }

    @Override
    void buildFinished(BuildResult result) {
        def combinedResults = combineTestCountsByType(testCounts)

        if (combinedResults.keySet().size() == 0) {
            return
        }
        outputTestResults(combinedResults)
    }

    def combineTestCountsByType(testCounts) {
        def results = [:]
        testCounts.each {
            if (it.taskWasRun) {
                if (!results.keySet().contains(it.taskName)) {
                    results[it.taskName] = it
                } else {
                    results[it.taskName].addCounts(it)
                }
            }
        }
        return results
    }

    def outputTestResults(combinedResults) {
        logger.lifecycle("\n$SEPARATOR\nTEST RESULTS\n$SEPARATOR")
        TestCounts total = new TestCounts()
        combinedResults.each {testType, counts ->
            outputTestCount(testType, counts)
            total.addCounts(counts)
        }
        logger.lifecycle(SEPARATOR)
        outputTestCount("Total", total)
    }

    def outputTestCount(type, counts) {
        def message = "$type: Tests Run: ${counts.totalTestsRun()}, "
        message += "Failures: ${counts.failed}, Skipped: ${counts.skipped}"
        logger.lifecycle(message)

    }

    @Override
    void buildStarted(Gradle gradle) {}

    @Override
    void projectsEvaluated(Gradle gradle) {}

    @Override
    void projectsLoaded(Gradle gradle) {}

    @Override
    void settingsEvaluated(Settings settings) {}
}
