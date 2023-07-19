package com.example.domain.apiobjects

data class CharacterListResult(
    val info: CharacterListInfo,
    val results: List<CharacterListItem>
)

data class CharacterListInfo(
    val next: String?,
    val prev: String?
)

data class CharacterListItem(
    val id: Int,
    val name: String,
    val image: String?
)