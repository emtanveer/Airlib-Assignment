package com.tanveer.airlib.task.ui.screen_dashboard.business.use_cases

import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Drug
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Problem
import javax.inject.Inject

interface ExtractDrugsUseCase {
    operator fun invoke(problems: List<Problem>): List<Drug>
}

class ExtractDrugsUseCaseImpl @Inject constructor() : ExtractDrugsUseCase {

    override fun invoke(problems: List<Problem>): List<Drug> {
        val drugs = mutableListOf<Drug>()

        problems.forEach { problem ->
            problem.diabetes?.forEach { diabetes ->
                diabetes.medications.forEach { medication ->
                    medication.medicationsClasses.forEach { medicationClass ->
                        medicationClass.className.forEach { drugInfo ->
                            drugInfo.associatedDrug?.let { drugs.addAll(it) }
                            drugInfo.associatedDrug2?.let { drugs.addAll(it) }
                        }
                        medicationClass.className2.forEach { drugInfo2 ->
                            drugInfo2.associatedDrug?.let { drugs.addAll(it) }
                            drugInfo2.associatedDrug2?.let { drugs.addAll(it) }
                        }
                    }
                }
            }
        }
        return drugs
    }
}