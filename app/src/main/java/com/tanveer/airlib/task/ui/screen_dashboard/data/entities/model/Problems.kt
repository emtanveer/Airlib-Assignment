package com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


//region Root Model Class

@Parcelize
data class ProblemsResponseModel(
    val problems: List<Problem>
) : Parcelable {}

// Problem model class
@Parcelize
data class Problem(
    val name: String,
    val medications: List<Medication>? = null,
    val labs: List<Lab>? = null
) : Parcelable {}

// Medication model class
@Parcelize
data class Medication(
    val medicationsClasses: List<MedicationClass>
) : Parcelable {}

// MedicationClass model class
@Parcelize
data class MedicationClass(
    val className: List<ClassDetail>? = null,
    val className2: List<ClassDetail>? = null
) : Parcelable {}

// ClassDetail model class
@Parcelize
data class ClassDetail(
    val associatedDrug: List<AssociatedDrug>? = null,
    val associatedDrug2: List<AssociatedDrug>? = null // Changed to `associatedDrug2` for proper naming
) : Parcelable {}

// AssociatedDrug model class
@Parcelize
data class AssociatedDrug(
    val name: String,
    val dose: String,
    val strength: String
) : Parcelable {}

// Lab model class
@Parcelize
data class Lab(
    val missing_field: String // You can rename this to something more meaningful
) : Parcelable {}

//endregion