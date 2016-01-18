package com.jandyco

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test

class TestResultsPlugin implements Plugin<Project> {
    void apply(Project project) {
        def testCounts = []
        project.afterEvaluate {
            project.allprojects { proj ->
                proj.tasks.withType(Test).each { test ->
                    def listener = new TestResultsListener(test)
                    test.addTestListener(listener)
                    testCounts << listener.counts
                }
            }
            project.gradle.addBuildListener(new BuildListener(testCounts))
        }
    }
}
