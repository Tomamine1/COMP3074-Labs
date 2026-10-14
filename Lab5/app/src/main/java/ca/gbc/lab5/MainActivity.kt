
package ca.gbc.lab5

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                GPSApp()
            }
        }
    }
}

@Composable
fun GPSApp() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val manager = remember {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    var latitude by remember { mutableStateOf("Not available") }
    var longitude by remember { mutableStateOf("Not available") }
    var status by remember { mutableStateOf("Press the button to get your GPS location.") }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        status = if (granted) {
            "Permission granted. Press the button again."
        } else {
            "Location permission denied."
        }
    }

    fun getGPSLocation() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }

        try {
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                status = "Please enable GPS on your device."
                return
            }

            status = "Getting GPS location..."

            val listener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()
                    status = "GPS location received successfully."

                    manager.removeUpdates(this)
                }
            }

            manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0L,
                0f,
                listener,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            status = "Location permission is required."
        } catch (e: Exception) {
            status = "Unable to get GPS location."
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("GPS Location", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(32.dp))

            Text("Latitude", style = MaterialTheme.typography.titleMedium)
            Text(latitude, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(20.dp))

            Text("Longitude", style = MaterialTheme.typography.titleMedium)
            Text(longitude, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(28.dp))

            Button(onClick = { getGPSLocation() }) {
                Text("Get Current Location")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(status, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
