package com.mirz.storyapp.data.source.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mirz.storyapp.data.response.StoryResponse

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(quote: List<StoryResponse>)

    @Query("SELECT * FROM story")
    fun getAllStories(): PagingSource<Int, StoryResponse>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}