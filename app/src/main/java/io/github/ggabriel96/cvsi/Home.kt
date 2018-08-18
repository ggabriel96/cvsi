package io.github.ggabriel96.cvsi

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.LensPositionSelector
import io.fotoapparat.selector.back
import io.fotoapparat.selector.front
import io.github.ggabriel96.cvsi.observer.LocationListener
import io.github.ggabriel96.cvsi.observer.RotationObserver
import kotlinx.android.synthetic.main.activity_home.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Home : AppCompatActivity() {

    private val tag = this.javaClass.simpleName

    private val requiredPermissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    )

    private var backLens = true
    private var fotoStarted = false
    private var fotoapparat: Fotoapparat? = null
    private var cameraConf = CameraConfiguration()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US)

    private lateinit var locationListener: LocationListener
    private lateinit var rotationObserver: RotationObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(tag, "onCreate")
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_home)
        this.setClickListeners()
        this.checkPermissions()
    }

    override fun onStart() {
        Log.d(tag, "onStart")
        super.onStart()
        this.checkPermissions()
        this.startCamera()
        this.rotationObserver = RotationObserver(this)
        this.lifecycle.addObserver(this.rotationObserver)
    }

    override fun onResume() {
        Log.d(tag, "onResume")
        super.onResume()
        if (!this.fotoStarted) this.startCamera()
    }

    override fun onStop() {
        Log.d(tag, "onStop")
        super.onStop()
        this.fotoapparat?.stop()
        this.fotoStarted = false
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        Log.d(tag, "onWindowFocusChanged")
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) this.hideSystemUI()
    }

    private fun setClickListeners() {
        switchButton.setOnClickListener {
            this.switchCamera()
        }

        // named lambda parameter to avoid shadowing with inner-lambda
        shootButton.setOnClickListener { btn ->
            val photoResult = this.fotoapparat?.takePicture()
            val photoFile = this.getPhotoFile()
            val pendingSave = photoResult?.saveToFile(photoFile)
            pendingSave?.whenAvailable { this.broadcastNewPicture(photoFile) }
            Log.d(tag, "angles: ${this.rotationObserver.angles?.joinToString()}")
        }

        settingsButton.setOnClickListener {
            Toast.makeText(this, "This is a placeholder", Toast.LENGTH_SHORT).show()
        }
    }

    // https://developer.android.com/training/system-ui/immersive
    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Hide the nav bar and status bar
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                )
    }

    private fun uniqueImageName(): String {
        val currentDate = this.dateFormat.format(Calendar.getInstance().time)
        return "JPEG_$currentDate.jpg"
    }

    private fun getPhotoFile(): File {
        val publicPicturesDirectory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES)
        val picturesDirectory = File(publicPicturesDirectory.path
                + File.separator
                + resources.getString(R.string.app_name))
        if (!picturesDirectory.exists()) picturesDirectory.mkdir()
        val filename = this.uniqueImageName()
        Log.d(tag, "pictures directory: $picturesDirectory.path")
        Log.d(tag, "picture filename: $filename")
        return File(picturesDirectory.path + File.separator + filename)
    }

    private fun broadcastNewPicture(file: File) {
        Log.d(tag, "broadcastNewPicture")
        val contentUri = Uri.fromFile(file)
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri)
        this.sendBroadcast(mediaScanIntent)
    }

    private fun switchLens(): LensPositionSelector {
        val lens = if (this.backLens) front() else back()
        this.backLens = !this.backLens
        return lens
    }

    private fun switchCamera() {
        this.fotoapparat?.switchTo(lensPosition = this.switchLens(),
                cameraConfiguration = this.cameraConf)
    }

    private fun startCamera() {
        if (this.fotoapparat != null) {
            this.fotoapparat!!.start()
            this.fotoStarted = true
        }
    }

    private fun setupCamera() {
        this.fotoapparat = Fotoapparat(
                context = this,
                view = cameraView,
                scaleType = ScaleType.CenterInside,
                lensPosition = if (this.backLens) back() else front()
        )
    }

    private fun checkPermissions() {
        Dexter.withActivity(this)
                .withPermissions(this.requiredPermissions)
                .withListener(object : BaseMultiplePermissionsListener() {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report != null) {
                            for (granted in report.grantedPermissionResponses) {
                                when (granted.permissionName) {
                                    Manifest.permission.CAMERA -> {
                                        this@Home.setupCamera()
                                    }
                                    Manifest.permission.ACCESS_FINE_LOCATION -> {
                                        this@Home.locationListener = LocationListener(
                                                this@Home)
                                        this@Home.lifecycle.addObserver(this@Home.locationListener)
                                    }
                                }
                            }
                            /**
                             * @TODO convert Toasts to Dialogs or Snackbars
                             */
                            for (denied in report.deniedPermissionResponses) {
                                when (denied.permissionName) {
                                    Manifest.permission.CAMERA -> {
                                        Toast.makeText(this@Home,
                                                "Camera permission is required to take" +
                                                        " pictures!",
                                                Toast.LENGTH_LONG).show()
                                    }
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                                        Toast.makeText(this@Home,
                                                "External storage permission is required to" +
                                                        " save your pictures!",
                                                Toast.LENGTH_LONG).show()
                                    }
                                    Manifest.permission.ACCESS_FINE_LOCATION -> {
                                        Toast.makeText(this@Home,
                                                "Location permission is required to geotag" +
                                                        " your pictures!",
                                                Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                            permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                        super.onPermissionRationaleShouldBeShown(permissions, token)
                    }
                })
                .check()
    }
}
