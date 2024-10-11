package com.tanveer.airlib.task.unit_testings.screen_dashboard.presentation.utils

import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Diabetes
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Drug
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.DrugInfo
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Medication
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.MedicationClass
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Problem
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.ProblemsResponseModel

class ProblemsResponseModelFactory {

    fun create(): ProblemsResponseModel {
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