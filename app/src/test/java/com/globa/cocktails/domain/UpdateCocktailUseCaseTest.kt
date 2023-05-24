package com.globa.cocktails.domain

import com.globa.cocktails.MainDispatcherRule
import com.globa.cocktails.datalayer.models.Cocktail
import com.globa.cocktails.datalayer.repository.CocktailRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class UpdateCocktailUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val cocktailRepository = mockk<CocktailRepository>()
    private val updateCocktailUseCase = UpdateCocktailUseCase(cocktailRepository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateCocktailUseCaseTest() = runTest {
        coEvery { cocktailRepository.updateCocktail(any()) } returns Unit
        val cocktail = Cocktail(id = "1", drinkName = "Test")
        updateCocktailUseCase(cocktail)
        coVerify { cocktailRepository.updateCocktail(cocktail) }
    }

}