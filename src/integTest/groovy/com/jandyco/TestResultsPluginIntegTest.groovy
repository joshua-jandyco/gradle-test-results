package com.jandyco

import spock.lang.*
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class TestResultsPluginIntegTest extends Specification { 
    Project project

    def setup() {
        project = ProjectBuilder.builder().build()
    }

    def "apply test-results plugin"() {
        when:
        project.apply plugin: TestResultsPlugin

        then:
        1==1
    }
}
