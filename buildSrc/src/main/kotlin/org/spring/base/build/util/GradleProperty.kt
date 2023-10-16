package org.spring.base.build.util

import org.gradle.api.Project
import org.spring.base.build.enum.Phase

fun Project.getProperty(key: String): String = this.properties[key] as String

private var currentPhase = Phase.ALPHA

var Project.phase: Phase
    get() = currentPhase
    set(value) {
        currentPhase = value
    }