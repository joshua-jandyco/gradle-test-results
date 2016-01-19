package com.jandyco

import org.gradle.api.tasks.testing.TestResult;
import spock.lang.*

class TestCountsTest extends Specification {

    def "new test count has zero failed, passed and skipped tests"() {
        when:
        def counts = new TestCounts("test")

        then:
        counts.failed == 0
        counts.passed == 0
        counts.skipped == 0
    }

    def "addResult adds results counts to existing counts"() {
        given:
        def counts = new TestCounts("test")
        counts.passed = 10
        counts.failed = 20
        counts.skipped = 30

        def result = new FakeTestResult(1,2,3)

        when:
        counts.addResult(result)

        then:
        counts.passed == 11
        counts.failed == 22
        counts.skipped == 33
    }

    def "addCounts adds counts to existing counts"() {
        given:
        def counts = new TestCounts("test")
        counts.passed = 10
        counts.failed = 20
        counts.skipped = 30

        def other = new TestCounts("other")
        other.passed = 1
        other.failed = 2
        other.skipped = 3

        when:
        counts.addCounts(other)

        then:
        counts.passed == 11
        counts.failed == 22
        counts.skipped == 33
    }

    def "total is the total of passed and failed"() {
        when:
        def counts = new TestCounts("test")
        counts.passed = 10
        counts.failed = 20
        counts.skipped = 100

        then:
        counts.totalTestsRun() == 30
    }
}
