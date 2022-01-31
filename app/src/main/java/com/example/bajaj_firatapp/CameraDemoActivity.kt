package com.example.bajaj_firatapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.CancelableCallback
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener
import com.google.android.gms.maps.GoogleMap.OnCameraMoveCanceledListener
import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions


class CameraDemoActivity :
    AppCompatActivity(),
    OnCameraMoveStartedListener,
    OnCameraMoveListener,
    OnCameraMoveCanceledListener,
    OnCameraIdleListener,
    OnMapReadyCallback {

    private val SCROLL_BY_PX = 100
    private val TAG = CameraDemoActivity::class.java.name
    private val sydneyLatLng = LatLng(-33.87365, 151.20689)
    private val bondiLocation: CameraPosition = CameraPosition.Builder()
        .target(LatLng(-33.891614, 151.276417))
        .zoom(15.5f)
        .bearing(300f)
        .tilt(50f)
        .build()

    private val sydneyLocation: CameraPosition = CameraPosition.Builder().
    target(LatLng(-33.87365, 151.20689))
        .zoom(15.5f)
        .bearing(0f)
        .tilt(25f)
        .build()
    // [END_EXCLUDE]

    private lateinit var map: GoogleMap
    // [START_EXCLUDE silent]
    private lateinit var animateToggle: CompoundButton
    private lateinit var customDurationToggle: CompoundButton
    private lateinit var customDurationBar: SeekBar
    private var currPolylineOptions: PolylineOptions? = null
    private var isCanceled = false
    // [END_EXCLUDE]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_demo)
        // [START_EXCLUDE silent]
        animateToggle = findViewById(R.id.animate)
        customDurationToggle = findViewById(R.id.duration_toggle)
        customDurationBar = findViewById(R.id.duration_bar)

        updateEnabledState()
        // [END_EXCLUDE]

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    // [START_EXCLUDE silent]
    override fun onResume() {
        super.onResume()
        updateEnabledState()
    }
    // [END_EXCLUDE]

    override fun onMapReady(googleMap: GoogleMap?) {
        // return early if the map was not initialised properly
        map = googleMap ?: return

        with(googleMap) {
            setOnCameraIdleListener(this@CameraDemoActivity)
            setOnCameraMoveStartedListener(this@CameraDemoActivity)
            setOnCameraMoveListener(this@CameraDemoActivity)
            setOnCameraMoveCanceledListener(this@CameraDemoActivity)

            uiSettings.isZoomControlsEnabled = false
            uiSettings.isMyLocationButtonEnabled = true

            moveCamera(CameraUpdateFactory.newLatLngZoom(sydneyLatLng, 10f))
        }
    }


    private fun checkReadyThen(stuffToDo: () -> Unit) {
        if (!::map.isInitialized) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show()
        } else {
            stuffToDo()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun onGoToBondi(view: View) {
        checkReadyThen {
            changeCamera(CameraUpdateFactory.newCameraPosition(bondiLocation))
        }
    }


    @Suppress("UNUSED_PARAMETER")
    fun onGoToSydney(view: View) {
        checkReadyThen {
            changeCamera(CameraUpdateFactory.newCameraPosition(sydneyLocation),
                object : CancelableCallback {
                    override fun onFinish() {
                        Toast.makeText(baseContext, "Animation to Sydney complete",
                            Toast.LENGTH_SHORT).show()
                    }

                    override fun onCancel() {
                        Toast.makeText(baseContext, "Animation to Sydney canceled",
                            Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }


    @Suppress("UNUSED_PARAMETER")
    fun onStopAnimation(view: View) = checkReadyThen { map.stopAnimation() }

    @Suppress("UNUSED_PARAMETER")
    fun onZoomIn(view: View) = checkReadyThen { changeCamera(CameraUpdateFactory.zoomIn()) }


    @Suppress("UNUSED_PARAMETER")
    fun onZoomOut(view: View) = checkReadyThen { changeCamera(CameraUpdateFactory.zoomOut()) }


    @Suppress("UNUSED_PARAMETER")
    fun onTiltMore(view: View) {
        checkReadyThen {

            val newTilt = Math.min(map.cameraPosition.tilt + 10, 90F)
            val cameraPosition = CameraPosition.Builder(map.cameraPosition).tilt(newTilt).build()

            changeCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }


    @Suppress("UNUSED_PARAMETER")
    fun onTiltLess(view: View) {
        checkReadyThen {

            val newTilt = Math.max(map.cameraPosition.tilt - 10, 0F)
            val cameraPosition = CameraPosition.Builder(map.cameraPosition).tilt(newTilt).build()

            changeCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun onScrollLeft(view: View) {
        checkReadyThen {
            changeCamera(CameraUpdateFactory.scrollBy((-SCROLL_BY_PX).toFloat(),0f))
        }
    }


    @Suppress("UNUSED_PARAMETER")
    fun onScrollRight(view: View) {
        checkReadyThen {
            changeCamera(CameraUpdateFactory.scrollBy(SCROLL_BY_PX.toFloat(), 0f))
        }
    }


    @Suppress("UNUSED_PARAMETER")
    fun onScrollUp(view: View) {
        checkReadyThen {
            changeCamera(CameraUpdateFactory.scrollBy(0f, (-SCROLL_BY_PX).toFloat()))
        }
    }


    @Suppress("UNUSED_PARAMETER")
    fun onScrollDown(view: View) {
        checkReadyThen {
            changeCamera(CameraUpdateFactory.scrollBy(0f, SCROLL_BY_PX.toFloat()))
        }
    }


    @Suppress("UNUSED_PARAMETER")
    fun onToggleAnimate(view: View) = updateEnabledState()


    @Suppress("UNUSED_PARAMETER")
    fun onToggleCustomDuration(view: View) = updateEnabledState()


    private fun updateEnabledState() {
        customDurationToggle.isEnabled = animateToggle.isChecked
        customDurationBar.isEnabled = animateToggle.isChecked && customDurationToggle.isChecked
    }

    private fun changeCamera(update: CameraUpdate, callback: CancelableCallback? = null) {
        if (animateToggle.isChecked) {
            if (customDurationToggle.isChecked) {
                // The duration must be strictly positive so we make it at least 1.
                map.animateCamera(update, Math.max(customDurationBar.progress, 1), callback)
            } else {
                map.animateCamera(update, callback)
            }
        } else {
            map.moveCamera(update)
        }
    }
    // [END_EXCLUDE]

    override fun onCameraMoveStarted(reason: Int) {
        // [START_EXCLUDE silent]
        if (!isCanceled) map.clear()
        // [END_EXCLUDE]

        var reasonText = "UNKNOWN_REASON"
        // [START_EXCLUDE silent]
        currPolylineOptions = PolylineOptions().width(5f)
        // [END_EXCLUDE]
        when (reason) {
            OnCameraMoveStartedListener.REASON_GESTURE -> {
                // [START_EXCLUDE silent]
                currPolylineOptions?.color(Color.BLUE)
                // [END_EXCLUDE]
                reasonText = "GESTURE"
            }
            OnCameraMoveStartedListener.REASON_API_ANIMATION -> {
                // [START_EXCLUDE silent]
                currPolylineOptions?.color(Color.RED)
                // [END_EXCLUDE]
                reasonText = "API_ANIMATION"
            }
            OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION -> {
                // [START_EXCLUDE silent]
                currPolylineOptions?.color(Color.GREEN)
                // [END_EXCLUDE]
                reasonText = "DEVELOPER_ANIMATION"
            }
        }
        Log.d(TAG, "onCameraMoveStarted($reasonText)")
        // [START_EXCLUDE silent]
        addCameraTargetToPath()
        // [END_EXCLUDE]
    }

    private fun checkPolylineThen(stuffToDo: () -> Unit) {
        if (currPolylineOptions != null) stuffToDo()
    }


    override fun onCameraMove() {
        Log.d(TAG, "onCameraMove")

        checkPolylineThen { addCameraTargetToPath() }
    }

    override fun onCameraMoveCanceled() {

        checkPolylineThen {
            addCameraTargetToPath()
            map.addPolyline(currPolylineOptions)
        }

        isCanceled = true  // Set to clear the map when dragging starts again.
        currPolylineOptions = null
        // [END_EXCLUDE]
        Log.d(TAG, "onCameraMoveCancelled")
    }

    override fun onCameraIdle() {
        checkPolylineThen {
            addCameraTargetToPath()
            map.addPolyline(currPolylineOptions)
        }

        currPolylineOptions = null
        isCanceled = false

        Log.d(TAG, "onCameraIdle")
    }
    private fun addCameraTargetToPath() {
        currPolylineOptions?.add(map.cameraPosition.target)
    }
}