package com.example.data.usecases.episodes

import com.example.data.provider.ApiProvider
import com.example.data.provider.ApiProviderImpl
import com.example.domain.apiobjects.Episode
import com.example.domain.appobjects.AppResult
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EpisodesUseCase : KoinComponent {

    private val provider: ApiProvider by inject()

    suspend fun execute(id: Int): AppResult<List<Episode>> {

        return when (val characterResult = provider.getCharacter(id)) {
            is AppResult.Error -> throw characterResult.error
            is AppResult.Success -> {
                val episodes = characterResult.data.episode.map {
                    it.removePrefix(EPISODES_PREFIX)
                }
                when {
                    episodes.isEmpty() -> {
                        AppResult.Success(emptyList())
                    }

                    episodes.size == 1 -> {
                        return when (val episode =
                            provider.getSingleEpisode(episodes.joinToString())) {
                            is AppResult.Error -> AppResult.Error(episode.error)
                            is AppResult.Success -> AppResult.Success(listOf(episode.data))
                        }
                    }

                    else -> {
                        val episodesPath = episodes.joinToString()
                        provider.getEpisodes(episodesPath)
                    }
                }
            }
        }
    }

    companion object {
        private const val EPISODES_PREFIX = "https://rickandmortyapi.com/api/episode/"
    }
}