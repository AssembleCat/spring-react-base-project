package org.spring.base.build.enum

enum class Phase(val branchRegex: String) {
    LOCAL("*"),
    ALPHA("\$develop*^"),
    PRODUCTION("\$master^"),
}
