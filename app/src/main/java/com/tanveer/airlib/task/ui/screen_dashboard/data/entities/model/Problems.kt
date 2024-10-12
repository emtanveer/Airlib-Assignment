package com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


//region Root Model Class

@Parcelize
data class ProblemsResponseModel(
    @SerializedName("problems") val problems: List<Problem>
) : Parcelable

@Parcelize
data class Problem(
    @SerializedName("Diabetes") val diabetes: List<Diabetes>? = null,
    @SerializedName("Asthma") val asthma: List<Asthma>? = null
) : Parcelable

@Parcelize
data class Diabetes(
    @SerializedName("medications") val medications: List<Medication>,
    @SerializedName("labs") val labs: List<Lab>
) : Parcelable

@Parcelize
data class Medication(
    @SerializedName("medicationsClasses") val medicationsClasses: List<MedicationClass>
) : Parcelable

@Parcelize
data class MedicationClass(
    @SerializedName("className") val className: List<DrugInfo>,
    @SerializedName("className2") val className2: List<DrugInfo>
) : Parcelable

@Parcelize
data class DrugInfo(
    @SerializedName("associatedDrug") val associatedDrug: List<Drug>,
    @SerializedName("associatedDrug#2") val associatedDrug2: List<Drug>
) : Parcelable

@Parcelize
data class Drug(
    @SerializedName("name") val name: String,
    @SerializedName("dose") val dose: String,
    @SerializedName("strength") val strength: String
) : Parcelable

@Parcelize
data class Lab(
    @SerializedName("missing_field") val missingField: String
) : Parcelable

@Parcelize
data class Asthma(
    @SerializedName("placeholder") val placeholder: List<Asthma>? = emptyList()
) : Parcelable


//endregion