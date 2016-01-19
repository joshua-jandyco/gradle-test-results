package com.jandyco

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestResult;
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.*

class TestResultsListenerTest extends Specification {
    def task
    def result
    def descriptor
    def listener

    def setup() {
        task = ProjectBuilder.builder().build().tasks.create("MyTask", Test)
        descriptor = new FakeTestDescriptor("name")
        listener = new TestResultsListener(task)
        result = new FakeTestResult(1,2,3)
    }

    def "test count has the name of the provided task"() {
        given:
        task = ProjectBuilder.builder().build().tasks.create("MyTask", Test)

        when:
        def listener = new TestResultsListener(task)

        then:
        listener.counts.taskName == "MyTask"
    }

    def "when not run, counts object as is not marked as run"() {
        when:
        listener = new TestResultsListener(task)

        then:
        listener.counts.taskWasRun == false
    }

    def "afterSuite marks the counts object as run"() {
        when:
        listener.afterSuite(descriptor, result)

        then:
        listener.counts.taskWasRun == true
    }

    def "when descriptor class name isn't null, counts are added"() {
        given:
        def descriptor = new FakeTestDescriptor("name")
        def result = new FakeTestResult(1,2,3)

        when:
        listener.afterSuite(descriptor, result)

        then:
        listener.counts.passed == 1
        listener.counts.failed == 2
        listener.counts.skipped == 3
    }

    def "when descriptor class name is null, counts aren't added"() {
        given:
        def descriptor = new FakeTestDescriptor(null)
        def result = new FakeTestResult(1,2,3)

        when:
        listener.afterSuite(descriptor, result)

        then:
        listener.counts.passed == 0
        listener.counts.failed == 0
        listener.counts.skipped == 0
    }
}

