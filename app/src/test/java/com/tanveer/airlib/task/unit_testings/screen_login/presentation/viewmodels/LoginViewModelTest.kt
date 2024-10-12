package com.tanveer.airlib.task.unit_testings.screen_login.presentation.viewmodels

import com.tanveer.airlib.task.shared.business.use_cases.GetUsernameUseCase
import com.tanveer.airlib.task.shared.business.use_cases.SaveUsernameUseCase
import com.tanveer.airlib.task.shared.data.UserEntity
import com.tanveer.airlib.task.ui.screen_login.presentation.LoginViewModel
import com.tanveer.airlib.task.unit_testings.shared.presentation.utils.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
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

        // Initialize the LoginViewModel with the mocked use cases
        loginViewModel = LoginViewModel(mockSaveUsernameUseCase, mockGetUsernameUseCase)
    }

    @Test
    fun `when username changes it should also update UI State for it`() = runTest {
        val testUsername = "testuser"
        loginViewModel.onUsernameChange(testUsername)

        assertEquals(testUsername, loginViewModel.uiState.first().username)
    }

    @Test
    fun `when Login button is pressed It should call onSuccess with the correct provided username inserted to database`() =
        runTest {
            val testUsername = "testuser"

            // Create mocks for the use cases
            val saveUsernameUseCase: SaveUsernameUseCase = mockk(relaxed = true)
            val getUsernameUseCase: GetUsernameUseCase = mockk(relaxed = true)

            // Initialize the LoginViewModel with the mocked use cases
            val loginViewModel = LoginViewModel(saveUsernameUseCase, getUsernameUseCase)

            // Simulate username change
            loginViewModel.onUsernameChange(testUsername)

            // Variable to capture the username received in onSuccess
            var receivedUsername: String? = null

            // Call the onLogin function
            loginViewModel.onLogin(
                onSuccess = { username -> receivedUsername = username },
                onError = { /* handle failure */ }
            )

            // Advance the dispatcher to ensure all coroutines are completed
            mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()

            // Verify that saveUsernameUseCase was called with the correct username
            coVerify { saveUsernameUseCase.invoke(testUsername) }

            // Check if onSuccess was called with the correct username
            assertEquals(testUsername, receivedUsername)
        }

    @Test
    fun `when fetching username, it should return the correct username from the repository`() =
        runTest {
            // Arrange
            val testUsername = "testuser"
            val mockUserEntity = UserEntity(testUsername)

            // Setup the mock for getting username
            coEvery { mockGetUsernameUseCase.invoke(testUsername) } returns mockUserEntity

            // Set the username in the ViewModel
            loginViewModel.onUsernameChange(testUsername)

            // Act
            loginViewModel.getUsername()

            // Advance the dispatcher to allow the coroutine to complete
            mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()

            // Assert
            val result = loginViewModel.fetchedUsername.value
            assertEquals(mockUserEntity, result)
            coVerify { mockGetUsernameUseCase.invoke(testUsername) }
        }

}