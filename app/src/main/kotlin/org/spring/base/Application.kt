package org.spring.base

class Application {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    println(Application().greeting)
}
