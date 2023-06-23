package com.globa.cocktails.domain.favorite

import com.globa.cocktails.data.favorite.api.Favorited
import com.globa.cocktails.data.favorite.api.FavoritedCocktailRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class IsFavoriteCocktailUseCaseTest {
    private val repository = mockk<FavoritedCocktailRepository>()
    private val isFavoriteCocktailUseCase = IsFavoriteCocktailUseCase(repository)
    private val repoList = listOf(
        Favorited("1"),
        Favorited("2")
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun isFavoriteCocktailTest() = runTest {
        coEvery { repository.getFavorites() } returns flowOf(repoList)

        val name = "2"
        val isFavorite = isFavoriteCocktailUseCase(name).first()

        assert(isFavorite)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun isNotFavoriteCocktailTest() = runTest {
        coEvery { repository.getFavorites() } returns flowOf(repoList)

        val name = "5"
        val isFavorite = isFavoriteCocktailUseCase(name).first()

        assert(isFavorite.not())
    }
}