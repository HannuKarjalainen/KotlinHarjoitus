package com.example.numberguesser.model

import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException

data class Card(val suit: String, val value: String, val image: String) {
    val numericValue: Int
        get() = getCardNumericValue(value)

    private fun getCardNumericValue(value: String): Int {
        return when (value) {
            "ACE" -> 14
            "KING" -> 13
            "QUEEN" -> 12
            "JACK" -> 11
            "JOKER" -> 14
            else -> value.toIntOrNull() ?: 0
        }
    }
}

data class DeckResponse(val success: Boolean, val deck_id: String, val cards: List<Card>, val remaining: Int)
data class DrawCardResponse(val success: Boolean, val deck_id: String, val cards: List<Card>, val remaining: Int)

interface DeckOfCardsApi {
    @GET("api/deck/new/shuffle/")
    suspend fun createShuffledDeck(
        @Query("deck_count") deckCount: Int = 1,
        @Query("jokers_enabled") jokersEnabled: Boolean = false
    ): DeckResponse

    @GET("api/deck/{deck_id}/draw/")
    suspend fun drawCards(@Path("deck_id") deckId: String, @Query("count") count: Int): DrawCardResponse
}

class GameModel {
    private val api: DeckOfCardsApi
    private var deckId: String? = null
    internal var currentCard: Card? = null
    private var previousCard: Card? = null
    var score: Int = 0
    var guesses: Int = 0
    var gameActive: Boolean = true
    var deckCount: Int = 1
    var jokersIncluded: Boolean = false

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.deckofcardsapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(DeckOfCardsApi::class.java)
    }

    suspend fun startApiGame(deckCount: Int, jokersIncluded: Boolean) {
        score = 0
        guesses = 0
        gameActive = true
        this.deckCount = deckCount
        this.jokersIncluded = jokersIncluded
        println("GameModel: Starting API game with deckCount = $deckCount, jokersIncluded = $jokersIncluded")

        try {
            val response = api.createShuffledDeck(deckCount, jokersEnabled = jokersIncluded)
            if (response.success) {
                deckId = response.deck_id
                drawNextCardFromApi()
            } else {
                println("Pelin aloitus epäonnistui. Vastaus epäonnistui: ${response.success}")
                gameActive = false
            }
        } catch (e: IOException) {
            println("Verkkovirhe: ${e.message}")
            gameActive = false
        } catch (e: Exception) {
            println("Tuntematon virhe: ${e.message}")
            gameActive = false
        }
    }

    private suspend fun drawNextCardFromApi() {
        deckId?.let {
            try {
                val response = api.drawCards(it, 1)

                if (response.success && response.cards.isNotEmpty()) {
                    previousCard = currentCard
                    currentCard = response.cards[0]
                } else {
                    println("Kortin hakeminen epäonnistui. Vastaus epäonnistui: ${response.success}")
                    gameActive = false
                }

                delay(100)
            } catch (e: IOException) {
                println("Verkkovirhe korttia haettaessa: ${e.message}")
                gameActive = false
            } catch (e: Exception) {
                println("Tuntematon virhe korttia haettaessa: ${e.message}")
                gameActive = false
            }
        }
    }

    suspend fun makeGuess(isBigger: Boolean): Boolean {
        if (!gameActive || deckId == null || previousCard == null) return false

        drawNextCardFromApi()
        val nextCard = currentCard ?: return false

        val isGuessCorrect = when {
            isBigger && nextCard.numericValue > previousCard!!.numericValue -> true
            !isBigger && nextCard.numericValue < previousCard!!.numericValue -> true
            nextCard.numericValue == 14 || previousCard!!.numericValue == 14 -> true
            nextCard.numericValue == previousCard!!.numericValue -> true
            nextCard.numericValue == 0 || previousCard!!.numericValue == 0 -> true
            else -> false
        }
        guesses++

        if (isGuessCorrect) {
            score++
        }

        if (deckId == null) {
            gameActive = false
        }

        return true
    }
}
