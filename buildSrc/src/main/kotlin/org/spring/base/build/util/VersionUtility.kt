package org.spring.base.build.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.spring.base.build.data.VersionInformation
import java.io.File

object VersionUtility {
    fun getVersion(data: File, phase: String): VersionInformation {
        return ObjectMapper()
            .registerModule(KotlinModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .readValues(YAMLFactory().createParser(data), VersionInformation::class.java)
            .readAll()
            .first { it.phase.lowercase() == phase.lowercase() }
    }
}
