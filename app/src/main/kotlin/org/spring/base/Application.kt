package org.spring.base

class Application {
    fun main() {
        println(Application().greeting)
    }

    private val greeting: String
        get() {
            return "Hello World!"
        }
}
