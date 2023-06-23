package com.globa.cocktails.domain.favorite

import com.globa.cocktails.data.favorite.api.Favorited
import com.globa.cocktails.data.favorite.api.FavoritedCocktailRepository
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
        val name = "Test"

        coEvery { repository.add(any()) } returns Unit
        setIsFavoriteUseCase(name, value = true)
        coVerify { repository.add(Favorited(name)) }
    }
}