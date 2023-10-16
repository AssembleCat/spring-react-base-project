package org.spring.base.build.data

data class VersionInformation(
    val phase: String,
    val version: String,
    val versionComment: String,
    val distributor: String,
    val hotfix: Boolean,
)
