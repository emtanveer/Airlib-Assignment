package com.tanveer.airlib.task.unit_testings.screen_dashboard.presentation.viewmodels


import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.ExtractDrugsUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.GreetingsGeneratorUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.ProblemsListingUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Drug
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.ProblemsResponseModel
import com.tanveer.airlib.task.ui.screen_dashboard.presentation.DashboardViewModel
import com.tanveer.airlib.task.unit_testings.screen_dashboard.presentation.utils.MainDispatcherRule
import com.tanveer.airlib.task.unit_testings.screen_dashboard.presentation.utils.ProblemsResponseModelFactory
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var getProblemsUseCase: ProblemsListingUseCase

    @MockK
    lateinit var extractDrugsUseCase: ExtractDrugsUseCase

    @MockK
    lateinit var greetingUseCase: GreetingsGeneratorUseCase

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var problemsResponseModel: ProblemsResponseModel

    @Before
    fun setUp() {
        // Initialize MockK
        MockKAnnotations.init(this)

        dashboardViewModel =
            DashboardViewModel(getProblemsUseCase, extractDrugsUseCase, greetingUseCase)

        val factory = ProblemsResponseModelFactory()
        problemsResponseModel = factory.create()
    }

    @Test
    fun `fetch Problem List & It should update UI State with Actual Data on Success`() = runTest {
        val mockResponse = problemsResponseModel
        val extractedDrugs = listOf(Drug("asprin", "", "500 mg"))

        // Mocking the UseCases
        coEvery { getProblemsUseCase.invoke() } returns mockResponse
        coEvery { extractDrugsUseCase.invoke(mockResponse.problems) } returns extractedDrugs

        dashboardViewModel.fetchProblemList()

        // Simulating state updates in the ViewModel
        mainDispatcherRule.dispatcher.scheduler.advanceUntilIdle()

        val uiState = dashboardViewModel.uiState.value
        Assert.assertFalse(uiState.isLoading)  // Check that loading state is false
        Assert.assertTrue(uiState.problems.isNotEmpty())  // Check taht problemsList is not empty
        Assert.assertEquals(
            "asprin",
            uiState.problems[0].diabetes?.get(0)!!.medications[0].medicationsClasses[0].className[0].associatedDrug[0].name
        )  // to be check the drug name
        Assert.assertEquals(1, uiState.problems.size)
    }

    @Test
    fun `extractDrugs method should return list of drugs against successful API hit`() = runTest {
        val mockResponse = problemsResponseModel.problems

        coEvery { extractDrugsUseCase.invoke(mockResponse) } returns listOf(
            Drug(
                "asprin",
                "",
                "500 mg"
            )
        )
        val drugs = dashboardViewModel.extractDrugs(mockResponse)

        assertEquals(1, drugs.size)
        assertEquals("asprin", drugs[0].name)
    }

    @Test
    fun `should update UI State with Error Message upon failure`() = runTest {
        coEvery { getProblemsUseCase.invoke() } throws Exception("API Error")
        dashboardViewModel.fetchProblemList()

        val uiState = dashboardViewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals("", uiState.error)
    }

    @After
    fun tearDown() {
        // Clean up resources if needed
    }
}