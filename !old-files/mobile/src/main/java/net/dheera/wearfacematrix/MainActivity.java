package net.dheera.wearfacematrix;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

/*
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
*/


public class MainActivity extends Activity {

    private CheckBox matrixfont;
    private CheckBox subwaytickerfont;
    ///private GoogleApiClient googleApiClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///initializeWearable();
        setContentView(R.layout.activity_main);
        matrixfont = (CheckBox) findViewById(R.id.matrixfont);
        subwaytickerfont = (CheckBox) findViewById(R.id.subwaytickerfont);
        Button apply = (Button) findViewById(R.id.applybutton);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                saveValues();
                //pushValuesToWearable();
            }
        });

        loadValues();

    }


    private void saveValues() {
        Settings.setBoolean(this, Settings.KEY_MATRIXFONT, matrixfont.isChecked());
        Settings.setBoolean(this, Settings.KEY_SUBWAYTICKERFONT, subwaytickerfont.isChecked());
    }

    private void loadValues() {
        matrixfont.setChecked(Settings.getBoolean(this, Settings.KEY_MATRIXFONT, Settings.KEY_MATRIXFONT_DEF));
        subwaytickerfont.setChecked(Settings.getBoolean(this, Settings.KEY_SUBWAYTICKERFONT, Settings.KEY_SUBWAYTICKERFONT_DEF));



    }


    /*
    private void pushValuesToWearable() {
        boolean matrixfont_set = matrixfont.isChecked();
        boolean subwaytickerfont_set = subwaytickerfont.isChecked();
        PutDataMapRequest dataMap = PutDataMapRequest.create(Settings.PATH_WITH_FEATURE);

        dataMap.getDataMap().putBoolean(Settings.KEY_MATRIXFONT, matrixfont_set);
        dataMap.getDataMap().putBoolean(Settings.KEY_SUBWAYTICKERFONT, subwaytickerfont_set);

        PutDataRequest request = dataMap.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi
                .putDataItem(googleApiClient, request);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    private void initializeWearable() {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)
        == ConnectionResult.SUCCESS) {
        googleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle connectionHint) {
                // Now you can use the data layer API
                pushValuesToWearable();
            }

            @Override
            public void onConnectionSuspended(int cause) {

            }
        })
        .addOnConnectionFailedListener(
                new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(
                            ConnectionResult result) {

                    }
                }
        )
        .addApi(Wearable.API)
        .build();
        }
        }
*/


}



