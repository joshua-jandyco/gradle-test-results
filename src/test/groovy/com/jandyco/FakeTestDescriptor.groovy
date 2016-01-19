package com.jandyco

import org.gradle.api.tasks.testing.TestDescriptor

class FakeTestDescriptor implements TestDescriptor {
    def className
    public FakeTestDescriptor(String className) {
        this.className = className
    }
    String getClassName() {
        return className
    }
    String getName() {}
    TestDescriptor getParent() {}
    boolean isComposite() {}
}
