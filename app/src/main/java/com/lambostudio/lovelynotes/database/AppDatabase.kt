package com.lambostudio.lovelynotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NoteEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun NoteDao(): NoteDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AppDatabase.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }

}
