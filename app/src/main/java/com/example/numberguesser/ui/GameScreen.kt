package com.example.numberguesser.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.numberguesser.viewmodel.GameViewModel
import androidx.navigation.NavController
import com.example.numberguesser.R

@Composable
fun GameScreen(navController: NavController, viewModel: GameViewModel = viewModel()) {
    val currentCard = viewModel.currentCard.collectAsState().value
    val score by viewModel.score.collectAsState()
    val guesses by viewModel.guesses.collectAsState()
    val isGameActive by viewModel.gameActive.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState() // Lataa tila

    Column(modifier = Modifier.fillMaxSize()) {
        Bars(title = stringResource(R.string.card_guesser), navController = navController)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = stringResource(R.string.loading), fontSize = 24.sp)
            } else {
                if (isGameActive) {
                    Text(text = stringResource(R.string.pulled), fontSize = 24.sp)

                    Spacer(modifier = Modifier.height(16.dp))
                    if (currentCard != null) {
                        AsyncImage(
                            model = currentCard.image,
                            contentDescription = "${currentCard.value} of ${currentCard.suit}",
                            modifier = Modifier.size(240.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = { viewModel.makeGuess(false) }) {
                            Text(stringResource(R.string.guesslower))
                        }
                        Button(onClick = { viewModel.makeGuess(true) }) {
                            Text(stringResource(R.string.guesshigher))
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = stringResource(R.string.points, score), fontSize = 18.sp)
                    val percentage = if (guesses > 0) (score.toDouble() / guesses * 100).toInt() else 0
                    Text(text = stringResource(R.string.gpercent, percentage), fontSize = 18.sp)
                    Text(text = "$percentage %")
                } else {
                    Text(text = stringResource(R.string.drawn), fontSize = 24.sp)

                    Spacer(modifier = Modifier.height(16.dp))
                    if (currentCard != null) {
                        AsyncImage(
                            model = currentCard.image,
                            contentDescription = "${currentCard.value} of ${currentCard.suit}",
                            modifier = Modifier.size(240.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = stringResource(R.string.deckout), fontSize = 24.sp)
                    Text(text = stringResource(R.string.end, score), fontSize = 18.sp)

                    val percentage = if (guesses > 0) (score.toDouble() / guesses * 100).toInt() else 0
                    Text(text = stringResource(R.string.gpercent, percentage), fontSize = 18.sp)
                    Text(text = "$percentage %")

                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = { viewModel.resetGame(1, false) }) {
                        Text(stringResource(R.string.newgame))
                    }
                }
            }
        }
    }
}

