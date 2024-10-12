package com.tanveer.airlib.task.unit_testings.screen_dashboard.presentation

import com.tanveer.airlib.task.shared.presentation.utils.Clock
import com.tanveer.airlib.task.shared.presentation.utils.GreetingGenerator
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalTime

private lateinit var clock: Clock
private lateinit var greetingGenerator: GreetingGenerator

class GreetingGeneratorTest {
    @Before
    fun setUp() {
        clock = mockk()  //Mocking our Clock here
        greetingGenerator = GreetingGenerator(clock)
    }

    @Test
    fun `generate Greeting returns Good-Morning if time is between 5-11`() {
        val morningTime = LocalTime.of(6, 0) // means 6:00 AM

        // Mocking current time, so we could get morningTime out of it
        every { clock.currentTimeNow() } returns morningTime

        val greeting = greetingGenerator.generateGreeting()

        assertEquals("Good Morning", greeting)
    }

    @Test
    fun `generate Greeting returns Good-Afternoon if time is between 12-17`() {
        val afternoonTime = LocalTime.of(14, 30)
        every { clock.currentTimeNow() } returns afternoonTime

        val greeting = greetingGenerator.generateGreeting()

        assertEquals("Good Afternoon", greeting)
    }

    @Test
    fun `generateGreeting returns Good Night before 5`() {

        val nightTimeEarly = LocalTime.of(4, 59)
        every { clock.currentTimeNow() } returns nightTimeEarly

        val greeting = greetingGenerator.generateGreeting()


        assertEquals("Good Night", greeting)
    }

    @After
    fun tearDown() {
        // Clean up resources if needed
    }
}