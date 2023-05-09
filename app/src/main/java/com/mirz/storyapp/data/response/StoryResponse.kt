package com.mirz.storyapp.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "story")
data class StoryResponse(
    @PrimaryKey @field:SerializedName("id") val id: String,
    val createdAt: String,
    val description: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val photoUrl: String
)