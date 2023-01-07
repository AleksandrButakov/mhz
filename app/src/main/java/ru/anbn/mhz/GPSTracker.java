package ru.anbn.mhz;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class GPSTracker implements LocationListener {
    Context context;

    public GPSTracker(Context c) {
        context = c;
    }

    public Location getLocation() {

        // проверяем что разрешение получено
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Разрешение GPS не предоставлено",
                    Toast.LENGTH_LONG).show();

            MainActivity.bGPSCoordinatesFound = false;
            return null;
        }

        //подключаем менеджер локаций
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // проверяем что GPS включен
        if (isGPSEnabled) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    6000, 100, this);
            Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            MainActivity.bGPSCoordinatesFound = true;
            return l;
        } else {
            Toast.makeText(context, "Включите GPS...", Toast.LENGTH_LONG).show();
            MainActivity.bGPSCoordinatesFound = false;
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}