package com.jandyco

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.*

class TestResultsPluginIntegTest extends Specification {
    Project project
    Project subproject

    def setup() {
        project = ProjectBuilder.builder().build()
        subproject = ProjectBuilder.builder().withParent(project).build()

    }

    def "TestResultsListener added to each test case"() {
        given: "Top level and sub project have test tasks"
        project.tasks.add(createMockTestTask("test1"))
        subproject.tasks.add(createMockTestTask("test2"))

        when: "TestResultsPlugin is applied"
        project.apply plugin: TestResultsPlugin
        project.evaluate()

        then: "Each test has a TestResultsListener"
        interaction {
            testTasksHadListenerAdded(project)
            testTasksHadListenerAdded(subproject)
        }
    }

    def testTasksHadListenerAdded(project) {
        project.tasks.withType(Test).each { mock ->
            1 * mock.addTestListener(_ as TestResultsListener)
        }
    }

    def createMockTestTask(name) {
        def mock = Mock(Test)
        mock.getName() >> name
        return mock
    }
}
