package com.tanveer.airlib.task.ui.screen_dashboard.presentation.utils

import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.ExtractDrugsUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.GreetingsGeneratorUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases.ProblemsListingUseCase
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Diabetes
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Drug
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.DrugInfo
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Medication
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.MedicationClass
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Problem
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.ProblemsResponseModel

//region Helping Methods/Functions for Preview Dashboard Screen View
class MockProblemsListingUseCase : ProblemsListingUseCase {

    override suspend fun invoke(): ProblemsResponseModel {
        return ProblemsResponseModel(
            problems = listOf(
                Problem(
                    diabetes = listOf(
                        Diabetes(
                            medications = listOf(
                                Medication(
                                    medicationsClasses = listOf(
                                        MedicationClass(
                                            className = listOf(
                                                DrugInfo(
                                                    associatedDrug = listOf(
                                                        Drug("asprin", "", "500 mg"),
                                                        Drug("somethingElse", "", "500 mg")
                                                    ),
                                                    associatedDrug2 = listOf(
                                                        Drug("Extor", "", "50 mg"),
                                                        Drug("Jentin Met", "", "50/100 mg")
                                                    )
                                                )
                                            ),
                                            className2 = emptyList()
                                        )
                                    )
                                )
                            ),
                            labs = emptyList()
                        )
                    ),
                    asthma = emptyList()
                )
            )
        )
    }
}

class MockExtractDrugsUseCase : ExtractDrugsUseCase {
    // Implement methods to return mock data
    override fun invoke(problems: List<Problem>): List<Drug> {
        // Return mock drugs based on problems
        return listOf(
            Drug("asprin", "", "500 mg"),
            Drug("somethingElse", "", "500 mg"),
            Drug("Extor", "", "50 mg"),
            Drug("Jentin Met", "", "50/100 mg")
        )
    }
}

class MockGreetingsGeneratorUseCase : GreetingsGeneratorUseCase {
    override suspend fun invoke(): String {
        // Return a mock greeting message
        return "Hello, Mock User!"
    }
}

//endregion