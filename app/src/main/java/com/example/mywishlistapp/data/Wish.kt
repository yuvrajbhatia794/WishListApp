package com.example.mywishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wish-table")
data class Wish(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "wish-title")
    val title: String = "",

    @ColumnInfo(name = "wish-desc")
    val description: String = ""
)


object DummyWish{
    val wishList = listOf(
        Wish(title = "Item 1", description = "Description 1"),
        Wish(title = "Item 2", description = "Description 2"),
        Wish(title = "Item 3", description = "Description 3"),
        Wish(title = "Item 4", description = "Description 4")
    )
}