package org.spring.base.window.config.data

/**
 * @see version.yaml
 */
data class VersionInformation(
    val phase: String,
    val version: String,
    val versionComment: String,
    val distributor: String,
    val hotfix: Boolean,
)
