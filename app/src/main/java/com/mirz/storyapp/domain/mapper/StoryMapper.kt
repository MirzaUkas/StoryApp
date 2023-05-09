package com.mirz.storyapp.domain.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.mirz.storyapp.data.response.StoryResponse
import com.mirz.storyapp.domain.entity.StoryEntity


fun StoryResponse.map() = let { story ->
    StoryEntity(
        id = story.id,
        name = story.name,
        description = story.description,
        photoUrl = story.photoUrl,
        lat = story.lat,
        lng = story.lon,
    )
}

fun List<StoryResponse>.map() = map { story ->
    StoryEntity(
        id = story.id,
        name = story.name,
        description = story.description,
        photoUrl = story.photoUrl,
        lat = story.lat,
        lng = story.lon,
    )
}

fun PagingData<StoryResponse>.map() = map { story ->
    StoryEntity(
        id = story.id,
        name = story.name,
        description = story.description,
        photoUrl = story.photoUrl,
        lat = story.lat,
        lng = story.lon,
    )
}