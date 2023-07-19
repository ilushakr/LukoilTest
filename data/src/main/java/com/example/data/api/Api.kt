package com.example.data.api

import com.example.domain.apiobjects.ApiResult
import com.example.domain.apiobjects.CharacterDetailItem
import com.example.domain.apiobjects.CharacterListItem
import com.example.domain.apiobjects.CharacterListResult
import com.example.domain.apiobjects.Episode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface Api {

    @GET("api/")
    suspend fun getApi(): ApiResult

    @GET
    suspend fun getCharacterList(@Url url: String): CharacterListResult

    @GET("api/character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterDetailItem

    @GET("api/episode/{episodes}")
    suspend fun getEpisodes(@Path("episodes") episodes: String): List<Episode>

    @GET("api/episode/{episodes}")
    suspend fun getSingleEpisode(@Path("episodes") episodes: String): Episode
}