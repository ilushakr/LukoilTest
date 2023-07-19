package com.example.data.provider

import com.example.domain.apiobjects.ApiResult
import com.example.domain.apiobjects.CharacterDetailItem
import com.example.domain.apiobjects.CharacterListItem
import com.example.domain.apiobjects.CharacterListResult
import com.example.domain.apiobjects.Episode
import com.example.domain.appobjects.AppResult
import retrofit2.http.Path

internal interface ApiProvider {

    suspend fun getApi(): AppResult<ApiResult>

    suspend fun getCharacterList(url: String): AppResult<CharacterListResult>

    suspend fun getCharacter(id: Int): AppResult<CharacterDetailItem>

    suspend fun getEpisodes(episodes: String): AppResult<List<Episode>>

    suspend fun getSingleEpisode(episode: String): AppResult<Episode>
}