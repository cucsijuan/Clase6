package juan.com.clase6;

//https://developer.android.com/training/location/retrieve-current.html

import android.*;
import android.Manifest;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {


    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    TextView mLatitudeText,mLongitudeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         mLatitudeText = (TextView) findViewById(R.id.latitud);
         mLongitudeText = (TextView) findViewById(R.id.longitud);

        GoogleApiClient.Builder builder = new  GoogleApiClient.Builder(this);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        builder.addApi(LocationServices.API);

        mGoogleApiClient = builder.build();

        findViewById(R.id.nuevo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLastLocation();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkLastLocation();
    }

    private void checkLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this,"No hay permisos",Toast.LENGTH_LONG).show();
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }else{
            Toast.makeText(this,"No hay nada",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
