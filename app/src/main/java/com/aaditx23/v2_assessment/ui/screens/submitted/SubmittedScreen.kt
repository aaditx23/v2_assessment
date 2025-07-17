package com.aaditx23.v2_assessment.ui.screens.submitted

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aaditx23.v2_assessment.ui.components.ErrorDialog
import com.aaditx23.v2_assessment.ui.components.HSpace
import com.aaditx23.v2_assessment.ui.components.LoadingDialog
import com.aaditx23.v2_assessment.util.getTime
import java.time.Instant
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon

@Composable
fun SubmittedScreen(
    viewModel: SubmittedViewModel = hiltViewModel()
){
    val state = viewModel.submittedScreenUiState.collectAsState()
    val records = viewModel.records.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    when (val subUiState = state.value){
        is SubmissionScreenState.Error -> {
            ErrorDialog(
                message = subUiState.error,
                onCancel = {
                    viewModel.refresh()
                }
            )
        }
        SubmissionScreenState.Loading -> {
            LoadingDialog("Please wait...")
        }
        SubmissionScreenState.NoSubmission ->{
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text("No Submissions yet.")
            }
        }
        is SubmissionScreenState.SubmissionFound -> {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 50.dp, bottom = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = "Submissions",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                    )
                }
                itemsIndexed(subUiState.submissions) { index, submission ->
                    val createdAt = getTime(submission.timestamp)
                    ElevatedCard(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = "#${submission.id}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            HSpace(16)
                            ElevatedCard(
                                modifier = Modifier,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                onClick = {

                                }
                            ) {
                                Text(
                                    text = "View Submission",
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                            HSpace(16)
                            createdAt?.let {
                                val date = createdAt.substringBefore("|")
                                val time = createdAt.substringAfter("|")
                                Column(
                                    modifier = Modifier.padding(start = 16.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.CalendarMonth,
                                            contentDescription = "Date",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.padding(end = 4.dp)
                                        )
                                        Text(
                                            text = date,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.AccessTime,
                                            contentDescription = "Time",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.padding(end = 4.dp)
                                        )
                                        Text(
                                            text = time,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}