package com.example.numberguesser.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.numberguesser.R

@Composable
fun MainsScreen(
    navController: NavController,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.Game))
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            navController.navigate("Game")
        }) {
            Text(stringResource(R.string.Startgame))
        }
        Button(onClick = {
            navController.navigate("Info")
        }) {
            Text(stringResource(R.string.info))
        }

    }
}
