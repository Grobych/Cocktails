package com.globa.cocktails.domain

import com.globa.cocktails.domain.repo.FavoritedCocktailRepository
import com.globa.cocktails.domain.favorites.Favorited
import com.globa.cocktails.domain.favorites.SetIsFavoriteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SetIsFavoriteUseCaseTest {
    private val repository = mockk<FavoritedCocktailRepository>()
    private val setIsFavoriteUseCase = SetIsFavoriteUseCase(repository)

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun setIsFavoriteTest() = runTest {
        val id = 1
        val name = "Test"
        val testCocktail = Cocktail(
            id = id,
            drinkName = name
        )
        coEvery { repository.add(any()) } returns Unit
        setIsFavoriteUseCase(name, value = true)
        coVerify { repository.add(Favorited(testCocktail.drinkName)) }
    }
}