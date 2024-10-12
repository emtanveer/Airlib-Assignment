package com.tanveer.airlib.task.ui.screen_medicine_detail.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tanveer.airlib.task.ui.screen_dashboard.data.entities.model.Drug

@Composable
fun MedicineDetailScreen(drug: Drug?) {
    DrugDetailView(drug)
}

//region Medicine Detail View Methods
@Composable
fun DrugDetailView(drug: Drug?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(300.dp)
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Medicine Details",
                    style = MaterialTheme.typography.titleLarge.copy(
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(30.dp))

                DrugDetailRow(label = "Name:", value = drug?.name)
                Spacer(modifier = Modifier.height(8.dp))
                DrugDetailRow(label = "Dose:", value = drug?.dose)
                Spacer(modifier = Modifier.height(8.dp))
                DrugDetailRow(label = "Strength:", value = drug?.strength)
            }
        }
    }
}

@Composable
fun DrugDetailRow(label: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.Underline),
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = value ?: "N/A",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
//endregion

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewShouldShowMedicineDetailContent() {
    //mock data for preview only!
    val drug = Drug(name = "Aspirin", dose = "1 tablet", strength = "500 mg")

    DrugDetailView(drug)
}
