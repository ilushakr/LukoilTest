package com.example.data.provider

import com.example.data.api.Api
import com.example.domain.apiobjects.ApiResult
import com.example.domain.apiobjects.CharacterDetailItem
import com.example.domain.apiobjects.CharacterListResult
import com.example.domain.apiobjects.Episode
import com.example.domain.appobjects.AppResult

internal class ApiProviderImpl(private val api: Api) : AbstractProvider(), ApiProvider {

    override suspend fun getApi(): AppResult<ApiResult> {
        return execute {
            api.getApi()
        }
    }

    override suspend fun getCharacterList(url: String): AppResult<CharacterListResult> {
        return execute {
            api.getCharacterList(url)
        }
    }

    override suspend fun getCharacter(id: Int): AppResult<CharacterDetailItem> {
        return execute {
            api.getCharacter(id)
        }
    }

    override suspend fun getSingleEpisode(episode: String): AppResult<Episode> {
        return execute {
            api.getSingleEpisode(episode)
        }
    }
    override suspend fun getEpisodes(episodes: String): AppResult<List<Episode>> {
        return execute {
            api.getEpisodes(episodes)
        }
    }
}