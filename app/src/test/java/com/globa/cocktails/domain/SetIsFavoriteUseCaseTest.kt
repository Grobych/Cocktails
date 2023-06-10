package com.globa.cocktails.domain

import com.globa.cocktails.datalayer.repository.CocktailRepository
import com.globa.cocktails.domain.setfavorite.SetIsFavoriteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SetIsFavoriteUseCaseTest {
    private val repository = mockk<CocktailRepository>()
    private val getCocktailByIdUseCase = GetCocktailByIdUseCase(repository)

    private val setIsFavoriteUseCase = SetIsFavoriteUseCase(repository, getCocktailByIdUseCase)

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun setIsFavoriteTest() = runTest {
    val id = 1
        val testCocktail = Cocktail(
            id = id,
            drinkName = "Test",
            isFavorite = false
        )
        coEvery { repository.getCocktail(id) } returns flowOf(testCocktail)
        coEvery { repository.updateCocktail(any()) } returns Unit
        setIsFavoriteUseCase(id, value = true)
        coVerify { repository.updateCocktail(testCocktail.copy(isFavorite = true)) }
    }
}