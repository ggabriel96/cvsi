package io.github.ggabriel96.cvsi.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.location.Location
import android.os.Build

@Entity(tableName = "picture_metadata")
data class PictureMetadata(
        @PrimaryKey
        @ColumnInfo(name = "img_filename") var imgFilename: String,
        @ColumnInfo(name = "location_latitude") var locationLatitude: Double,
        @ColumnInfo(name = "location_longitude") var locationLongitude: Double,
        @ColumnInfo(name = "location_altitude") var locationAltitude: Double,
        @ColumnInfo(name = "location_bearing") var locationBearing: Float,
        @ColumnInfo(name = "location_accuracy") var locationAccuracy: Float,
        @ColumnInfo(name = "location_altitude_accuracy") var locationAltitudeAccuracy: Float,
        @ColumnInfo(name = "location_bearing_accuracy") var locationBearingAccuracy: Float,
        @ColumnInfo(name = "location_time") var locationTime: Long,
        @ColumnInfo(name = "rotation_azimuth") var rotationAzimuth: Float,
        @ColumnInfo(name = "rotation_pitch") var rotationPitch: Float,
        @ColumnInfo(name = "rotation_roll") var rotationRoll: Float,
        @ColumnInfo(name = "rotation_accuracy") var rotationAccuracy: Int
)

fun fromMetadata(imgFilename: String, location: Location, azimuth: Float, pitch: Float, roll: Float,
                 rotationAccuracy: Int): PictureMetadata {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        PictureMetadata(imgFilename, location.latitude, location.longitude, location.altitude,
                location.bearing, location.accuracy, location.verticalAccuracyMeters,
                location.bearingAccuracyDegrees, location.time, azimuth, pitch, roll,
                rotationAccuracy)
    } else {
        PictureMetadata(imgFilename, location.latitude, location.longitude, location.altitude,
                location.bearing, location.accuracy, 0.0f,
                0.0f, location.time, azimuth, pitch, roll, rotationAccuracy)
    }
}
