package com.braincorp.petrolwatcher

class TestApp : App() {

    override fun dependencyInjection(): DependencyInjection = TestDependencyInjection()

}