package com.aaditx23.v2_assessment.ui.screens.submitted

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.aaditx23.v2_assessment.ui.components.ConfirmDeleteDialog
import com.aaditx23.v2_assessment.ui.components.DateAndTime
import com.aaditx23.v2_assessment.ui.components.SearchBar
import com.aaditx23.v2_assessment.util.FileUtil

@Composable
fun SubmittedScreen(
    viewModel: SubmittedViewModel = hiltViewModel(),
    navController: NavHostController
){
    val state = viewModel.submittedScreenState.collectAsState()
    val imagePath = viewModel.imagePath.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getAllSubmissions()
    }

    when (val subUiState = state.value){
        is SubmissionScreenState.Error -> {
            ErrorDialog(
                message = subUiState.error,
                onCancel = {
                    viewModel.getAllSubmissions()
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
            var query by remember { mutableStateOf("") }
            val filteredSubmissions = if(query.isBlank()) subUiState.submissions
                        else subUiState.submissions.filter{ it.id.toString().contains(query)}
            Column(
                modifier = Modifier
                    .padding(top = 50.dp, bottom = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Submissions",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(start = 16.dp)
                )
                SearchBar(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(filteredSubmissions, key = { it.id }) { submission ->
                        var showDeleteConfirmation by remember { mutableStateOf(false) }
                        val createdAt = getTime(submission.timestamp)
                        ElevatedCard(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth(),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "#${submission.id}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                ElevatedCard(
                                    modifier = Modifier,
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    ),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                    onClick = {
                                        navController.navigate("answer/${submission.id}")
                                    }
                                ) {
                                    Text(
                                        text = "View Submission",
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        viewModel.getImagePath(submission.id)
                                        showDeleteConfirmation = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.DeleteForever,
                                        contentDescription = "Delete entry",
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier
                                            .padding(8.dp)
                                    )
                                }
                                createdAt?.let {
                                    DateAndTime(createdAt)
                                }
                            }
                        }
                        if(showDeleteConfirmation){
                            ConfirmDeleteDialog(
                                onCancel = { showDeleteConfirmation = false },
                                onConfirm = {
                                    if (imagePath.value.isNotBlank()){
                                        FileUtil.deleteImageFile(context, imagePath.value)
                                        viewModel.deleteSubmission(submission.id)
                                        showDeleteConfirmation = false
                                    }
                                }
                            )
                        }
                    }
                }
            }



        }
    }
}