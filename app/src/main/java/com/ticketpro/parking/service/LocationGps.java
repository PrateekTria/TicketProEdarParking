package com.ticketpro.parking.service;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.ticketpro.parking.dar.FieldContract;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class LocationGps extends Worker {
    public LocationGps(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return ListenableWorker.Result.success();
            }
            else {
                Task<Location> locationTask = fusedLocationClient.getLastLocation();
                android.location.Location location = Tasks.await(locationTask);

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    String completeaddres=addresses.get(0).getAddressLine(0);
                    FieldContract.updateTextViewOnUiThread(completeaddres);
                    // Use the location to resolve the address
                    //   String address = getAddressFromLocation(location);
                }catch (Exception e)
                {

                }
                Data outputData = new Data.Builder()
                        .putString("address", "ss")
                        .build();
            }
           /* Task<Location> locationTask = fusedLocationClient.getLastLocation();
            android.location.Location location = Tasks.await(locationTask);

            // Use the location to resolve the address
         //   String address = getAddressFromLocation(location);

            Data outputData = new Data.Builder()
                    .putString("address", "ss")
                    .build();*/

            Data outputData = new Data.Builder()
                    .putString("address", "ss")
                    .build();

            return ListenableWorker.Result.success(outputData);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ListenableWorker.Result.failure();
        }
    }
}