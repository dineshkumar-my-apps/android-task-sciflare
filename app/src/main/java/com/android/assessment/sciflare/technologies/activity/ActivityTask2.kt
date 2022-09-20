package com.android.assessment.sciflare.technologies.activity

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.assessment.sciflare.technologies.R
import com.android.assessment.sciflare.technologies.databinding.ActivityMapsBinding
import com.android.assessment.sciflare.technologies.support.AppSharedPreference
import com.android.assessment.sciflare.technologies.support.AppUtils
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.util.*

class ActivityTask2 : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    var sharedPreference = AppSharedPreference()

    var latitude: Any? = null
    var longitude: Any? = null
    var address: Any? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getLocationPermission()
    }

    private fun getLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkPermission()) {
                showAlert()
            } else {
                checkLocationService()
            }
        } else {
            checkLocationService()
        }
    }

    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this@ActivityTask2,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this@ActivityTask2,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun checkLocationService() {
        val lm = getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        var gps_enabled = false
        var network_enabled = false
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }
        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder(this@ActivityTask2)
                .setCancelable(false)
                .setTitle("Location Permission")
                .setMessage("To get current location, you need to allow location permission.")
                .setPositiveButton(
                    "Open Location Settings"
                ) { paramDialogInterface, paramInt ->
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                // .setNegativeButton("Cancel", null)
                .show()
        } else {
            getLocation()
        }
    }

    private fun showAlert() {
        val LOCATION_PERMISSION_RESULT =
            sharedPreference.getInt(this@ActivityTask2, "LOCATION_PERMISSION_RESULT")
        val data = if (LOCATION_PERMISSION_RESULT == 2) {
            "To get current location, you need to allow location permission in app settings."
        } else {
            "To get current location, you need to allow location permission."
        }
        val builder = AlertDialog.Builder(this@ActivityTask2)
        builder.setTitle("Location Permission")
        builder.setMessage(data)
            .setCancelable(false)
            .setPositiveButton(
                "Request Permission"
            ) { dialog, id ->
                dialog.cancel()
                if (LOCATION_PERMISSION_RESULT == 2) {
                    val intent = Intent()
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", this@ActivityTask2.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } else {
                    try {
                        ActivityCompat.requestPermissions(
                            this@ActivityTask2, arrayOf(
                                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION
                            ), 100
                        )
                    } catch (e: Exception) {
                        println("-----LOCATION_PERMISSION_RESULT : " + e.message)
                    }
                }
            }

        //Creating dialog box
        val alert = builder.create()
        //Setting the title manually
        alert.setTitle("Location Permission")
        alert.show()
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        println("---onRequestPermissionsResult1 : " + "start")
        println("---onRequestPermissionsResult : requestCode - $requestCode")
        if (requestCode == 100) {
            var result = false
            if (grantResults.isNotEmpty()) {
                for (i in grantResults.indices) {
                    println("---onRequestPermissionsResult grantResults : " + grantResults[i])
                    result = grantResults[i] == PackageManager.PERMISSION_GRANTED
                }
            }
            if (result) {
                println("---onRequestPermissionsResult : " + "PERMISSION_GRANTED")
                sharedPreference.putInt(this@ActivityTask2, "LOCATION_PERMISSION_RESULT", 0)
                getLocation()
            } else {
                var denied = false
                for (i in permissions.indices) {
                    println("---onRequestPermissionsResult permissions : " + permissions[i])
                    val showRationale = shouldShowRequestPermissionRationale(permissions[i])
                    if (!showRationale) {
                        denied = true
                    }
                }
                if (!denied) {
                    println("---onRequestPermissionsResult : " + "PERMISSION_DENIED 1")
                    sharedPreference.putInt(this@ActivityTask2, "LOCATION_PERMISSION_RESULT", 1)
                    getLocationPermission()
                } else {
                    println("---onRequestPermissionsResult : " + "PERMISSION_DENIED 2")
                    sharedPreference.putInt(this@ActivityTask2, "LOCATION_PERMISSION_RESULT", 2)
                    getLocationPermission()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        try {
            AppUtils.loading(this@ActivityTask2, "Finding Location...", false).show()
            println("---LOCATION_SERVICE : START")
            val fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this@ActivityTask2)

            fusedLocationProviderClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                        CancellationTokenSource().token

                    override fun isCancellationRequested() = false
                })
                .addOnSuccessListener { location: Location? ->
                    if (location == null)
                        Toast.makeText(
                            this@ActivityTask2,
                            "Cannot get location.",
                            Toast.LENGTH_SHORT
                        ).show()
                    else {
                        println("---LOCATION_SERVICE : SUCCESS")
                        val geocoder = Geocoder(this@ActivityTask2, Locale.getDefault())
                        val addresses =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val place = addresses[0].getAddressLine(0)
                        val text =
                            "latitude : " + location.latitude + " longtitude : " + location.longitude + " Address : " + place
                        println("----current location : $text")

                        latitude = location.latitude
                        longitude = location.longitude
                        address = place

                        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                        mapFragment.getMapAsync(this)
                    }
                    AppUtils.loadingDialog.dismiss()
                }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            println("---error1 : " + e.message)
            Toast.makeText(
                this@ActivityTask2,
                "Cannot get location.",
                Toast.LENGTH_SHORT
            ).show()
            AppUtils.loadingDialog.dismiss()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

      /*  Toast.makeText(
            this@MapsActivity,
            "Map called",
            Toast.LENGTH_SHORT
        ).show()

*/
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val marker = LatLng(latitude as Double, longitude as Double)
        val maker = mMap.addMarker(MarkerOptions().position(marker).title(address.toString()))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 15.0f))

        maker!!.showInfoWindow()

    }
}