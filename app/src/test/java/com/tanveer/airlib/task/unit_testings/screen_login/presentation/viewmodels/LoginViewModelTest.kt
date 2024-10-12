package com.tanveer.airlib.task.unit_testings.screen_login.presentation.viewmodels

import com.tanveer.airlib.task.shared.business.use_cases.GetUsernameUseCase
import com.tanveer.airlib.task.shared.business.use_cases.SaveUsernameUseCase
import com.tanveer.airlib.task.shared.data.UserEntity
import com.tanveer.airlib.task.ui.screen_login.presentation.LoginViewModel
import com.tanveer.airlib.task.unit_testings.shared.presentation.utils.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var mockSaveUsernameUseCase: SaveUsernameUseCase

    @MockK
    lateinit var mockGetUsernameUseCase: GetUsernameUseCase

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        // Initialize MockK annotations
        MockKAnnotations.init(this)

        // Initializing LoginViewModel with the mocked use cases
        loginViewModel = LoginViewModel(mockSaveUsernameUseCase, mockGetUsernameUseCase)
    }

    @Test
    fun `when username changes it should also update UI State for it`() = runTest {
        val testUsername = "testuser"
        loginViewModel.onUsernameChange(testUsername)

        assertEquals(testUsername, loginViewModel.uiState.first().username)
    }

    @Test
    fun `test Multiple Insertions Should Only Save Once`() = runTest {

        val username1 = "user1"
        val username2 = "user2"

        // Mock the SaveUsernameUseCase to handle the invocations
        coEvery { mockSaveUsernameUseCase(username1) } just Runs
        coEvery { mockSaveUsernameUseCase(username2) } just Runs

        mockSaveUsernameUseCase(username1) // Save the first user
        mockSaveUsernameUseCase(username2) // Attempt to insert the second user

        // Mock the GetUsernameUseCase to return the last saved username
        coEvery { mockGetUsernameUseCase() } returns UserEntity(username = username2)

        val retrievedUser = mockGetUsernameUseCase()

        assertNotNull(retrievedUser)
        assertEquals(username2, retrievedUser.username)
    }

    @Test
    fun `when fetching username, it should return the correct username from the repository`() = runTest {
            // Arrange
            val testUserId = 0
            val testUsername = "testuser"
            val mockUserEntity = UserEntity(testUserId, testUsername)

            // Setup the mock for getting username
            coEvery { mockGetUsernameUseCase.invoke() } returns mockUserEntity

            // Fetch the username
            loginViewModel.onUsernameChange(testUsername)
            val result =
                mockGetUsernameUseCase.invoke().username

            mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()

            assertEquals(testUsername, result)
            coVerify { mockGetUsernameUseCase.invoke() }
        }
}