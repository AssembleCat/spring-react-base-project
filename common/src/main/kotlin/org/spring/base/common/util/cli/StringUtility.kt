package org.spring.base.common.util.cli

/**
 * CamelCase to SnakeCase converter
 *
 * @return converted string
 */
fun String.camelToSnake(): String {
    //https://www.baeldung.com/kotlin/convert-camel-case-snake-case
    val pattern = "(?<=.)[A-Z]".toRegex()
    return this.replace(pattern, "_$0").lowercase()
}

/**
 * SnakeCase to CamelCase converter
 *
 * @return converted string
 */
fun String.snakeToCamel(): String {
    //https://www.baeldung.com/kotlin/convert-camel-case-snake-case
    val pattern = "_[a-z]".toRegex()
    return this.replace(pattern) { it.value.uppercase() }.replace("_", "")
}
