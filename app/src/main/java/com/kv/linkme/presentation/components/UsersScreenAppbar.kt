package com.kv.linkme.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreenAppbar() {

    Surface(
        shadowElevation = 2.dp
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "User Data",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            navigationIcon = {

            },
            actions = {

            },
        )
    }
}