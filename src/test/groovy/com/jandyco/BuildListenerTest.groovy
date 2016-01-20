package com.jandyco

import org.gradle.api.logging.Logger
import org.gradle.api.tasks.testing.TestResult;
import spock.lang.*

class BuildListenerTest extends Specification {
    BuildListener listener
    def counts = []

    def createListener() {
        listener = new BuildListener(counts)
        listener.logger = Mock(Logger)
    }

    def "No tests run - Nothing output"() {
        given: "TestCounts marked as not run"
        counts << new TestCounts(false,"test",0,0,0)
        counts << new TestCounts(false,"test",0,0,0)
        createListener()

        when: "buildFinished called"
        listener.buildFinished(null)

        then: "nothing is printed"
        0 * listener.logger.lifecycle(*_)

    }

    def "each test type is output and then total"() {
        given: "TestCounts of multiple types marked as run"
        counts << new TestCounts(true,"test",10,20,31)
        counts << new TestCounts(true,"test",1,2,3)
        counts << new TestCounts(true,"integTest",40,50,60)
        createListener()

        when: "buildFinished called"
        listener.buildFinished(null)

        then:
        1 * listener.logger.lifecycle() {
            it == "\n" + "-".multiply(80) + "\nTEST RESULTS\n" + "-".multiply(80)
        }

        then:
        1 * listener.logger.lifecycle("test: Tests Run: 33, Failures: 22, Skipped: 34")
        1 * listener.logger.lifecycle("integTest: Tests Run: 90, Failures: 50, Skipped: 60")

        then:
        1 * listener.logger.lifecycle("-".multiply(80))

        then:
        1 * listener.logger.lifecycle("Total: Tests Run: 123, Failures: 72, Skipped: 94")
    }
}
