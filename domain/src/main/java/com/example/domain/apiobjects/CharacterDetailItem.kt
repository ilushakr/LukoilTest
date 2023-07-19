package com.example.domain.apiobjects

data class CharacterDetailItem(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val location: Location,
    val episode: List<String>,
    val image: String?,
)

data class Location(
    val name: String
)