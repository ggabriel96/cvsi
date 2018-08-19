package io.github.ggabriel96.cvsi

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import io.github.ggabriel96.cvsi.room.AppDatabase
import io.github.ggabriel96.cvsi.room.Metadata
import io.github.ggabriel96.cvsi.room.MetadataDao
import io.github.ggabriel96.cvsi.room.fromPictureMetadata
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class MetadataDatabaseTest {
    private var appDatabase: AppDatabase? = null
    private var metadataDao: MetadataDao? = null

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        this.appDatabase = Room.inMemoryDatabaseBuilder(
                context, AppDatabase::class.java).build()
        this.metadataDao = this.appDatabase?.pictureMetadataDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        this.appDatabase?.close()
    }

    private fun mockInsert(): Metadata {
        val filename = "Filename"
        val metadata = fromPictureMetadata(filename)
        this.metadataDao?.insert(metadata)
        return metadata
    }

    @Test
    fun writeAndFind() {
        val metadata = mockInsert()
        val byFilename = this.metadataDao?.find(metadata.imgFilename)
        assertThat(byFilename, equalTo(metadata))
    }

    @Test
    fun writeAndList() {
        val metadata = mockInsert()
        val list = this.metadataDao?.list()
        // list has sort by
        assertThat(list?.first(), equalTo(metadata))
    }
}

