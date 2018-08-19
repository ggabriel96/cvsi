package io.github.ggabriel96.cvsi.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import io.github.ggabriel96.cvsi.R


@Database(entities = [Metadata::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pictureMetadataDao(): MetadataDao

    companion object Singleton {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (this.instance == null) {
                synchronized(AppDatabase::class) {
                    if (this.instance == null) {
                        this.instance = Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                context.resources.getString(R.string.db_name)
                        ).build()
                    }
                }
            }
            return this.instance!!
        }
    }
}
