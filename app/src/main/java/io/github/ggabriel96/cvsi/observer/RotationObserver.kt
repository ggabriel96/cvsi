package io.github.ggabriel96.cvsi.observer

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.widget.Toast

class RotationObserver(context: Context) : LifecycleObserver, SensorEventListener {

    private val tag = this.javaClass.simpleName

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = this.sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    var values: FloatArray? = null
        private set
    var accuracy: Int = 0
        private set
    val angles: FloatArray?
        get() {
            return this.values?.also {
                val rotationMatrix = FloatArray(9)
                val orientationAngles = FloatArray(3)
                SensorManager.getRotationMatrixFromVector(rotationMatrix, it)
                SensorManager.getOrientation(rotationMatrix, orientationAngles)
                return orientationAngles
            }
        }

    init {
        if (this.sensor == null) {
            val msg = "Cannot determine smartphone orientation because the rotation" +
                    " vector sensor is not available!"
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            Log.e(tag, msg)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        Log.d(tag, "onResume")
        this.sensor?.also {
            this@RotationObserver.sensorManager
                    .registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        Log.d(tag, "onPause")
        this.sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        this.accuracy = accuracy
    }

    override fun onSensorChanged(event: SensorEvent?) {
        this.values = event?.values?.copyOf()
    }

}
