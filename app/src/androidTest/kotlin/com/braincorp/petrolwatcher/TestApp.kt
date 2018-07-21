package com.braincorp.petrolwatcher

/**
 * The application used in tests
 */
class TestApp : App() {

    override fun dependencyInjection(): DependencyInjection = TestDependencyInjection()

}