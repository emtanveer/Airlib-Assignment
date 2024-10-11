package com.tanveer.airlib.task.unit_testings.screen_dashboard.presentation.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/*
 - CoroutineRule are CoroutineRule is a custom rule used to handle coroutines in Android tests.
    When writing tests involving suspending functions and coroutines,
    you need to handle the coroutine lifecycle properly to ensure that all coroutines are completed before the test finishes.
    CoroutineRule simplifies this process by managing the lifecycle of coroutines and providing utilities to write coroutine-based tests.

 - TestWatcher() are JUnit rule that allows you to intercept and observe the execution of individual test methods in your test class.
    It provides hooks for setting up actions before and after each test method is executed.
    You can subclass TestWatcher and override its methods to perform specific actions during the test lifecycle,
    such as logging, taking screenshots, or cleaning up resources.

    `Note`: This class will only be consume/use as a Rule in our unit-test domain.
 */
@ExperimentalCoroutinesApi
class MainDispatcherRule(val dispatcher: TestDispatcher = StandardTestDispatcher()): TestWatcher() {

    override fun starting(description: Description) = Dispatchers.setMain(dispatcher)

    override fun finished(description: Description) = Dispatchers.resetMain()
}