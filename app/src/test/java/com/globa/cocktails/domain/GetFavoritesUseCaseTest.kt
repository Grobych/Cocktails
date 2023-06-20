package com.globa.cocktails.domain

import com.globa.cocktails.domain.repo.FavoritedCocktailRepository
import com.globa.cocktails.domain.favorites.Favorited
import com.globa.cocktails.domain.favorites.GetFavoritesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetFavoritesUseCaseTest {
    private val repository = mockk<FavoritedCocktailRepository>()
    private val getFavoritesUseCase = GetFavoritesUseCase(repository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getFavoritesTest() = runTest {
        val repoFavorited = listOf(Favorited("1"),Favorited("3"),Favorited("5"))
        coEvery { repository.getFavorites() } returns flowOf(repoFavorited)

        val res = getFavoritesUseCase()
        assert(res.first() == repoFavorited)
    }
}