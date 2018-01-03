package com.speedometer.speedy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Formatter;
import java.util.Locale;
import android.location.Location;
import android.location.LocationManager;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements IBaseGpsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        this.updateSpeed(null);

        MainActivity.this.updateSpeed(null);
    }


    public void finish()
    {
        super.finish();
        System.exit(0);
    }

    private void updateSpeed(CLocation location) {
        // TODO Auto-generated method stub
        float nCurrentSpeed = 0;

        if(location != null)
        {
            location.setUseMetricunits(true);
            nCurrentSpeed = location.getSpeed();
        }

        Formatter fmt = new Formatter(new StringBuilder());
        fmt.format(Locale.US, "%5.1f", nCurrentSpeed);
        String strCurrentSpeed = fmt.toString();
        strCurrentSpeed = strCurrentSpeed.replace(' ', '0');

        String strUnits = "meters/second";

        float f = Float.valueOf(strCurrentSpeed);

        float degrees = (f*100)/180;
        if(degrees>180){
            degrees=180;
        }else if(degrees<0){
            degrees=0;
        }

        View v = (View)findViewById(R.id.needle);
        v.setRotation(degrees);

        TextView txtCurrentSpeed = (TextView) this.findViewById(R.id.txtCurrentSpeed);
        txtCurrentSpeed.setText(strCurrentSpeed + " " + strUnits);
    }

    //    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        if(location != null)
        {
            CLocation myLocation = new CLocation(location, true);
            this.updateSpeed(myLocation);
        }
    }

    //    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    //    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    //    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    //    @Override
    public void onGpsStatusChanged(int event) {
        // TODO Auto-generated method stub

    }

}