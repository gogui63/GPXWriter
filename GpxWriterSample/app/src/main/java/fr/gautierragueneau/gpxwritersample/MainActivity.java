package fr.gautierragueneau.gpxwritersample;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.gautierragueneau.gpxwriter.GPX;
import fr.gautierragueneau.gpxwriter.IGPX;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;

public class MainActivity extends AppCompatActivity {

    private GPX.GPXBuilder mGPXBuilder;
    private TextView mTextView;
    private Button mButtonGenerate;
    private int mCompteur;
    private SmartLocation mSmartLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.activity_main_textview_gpx);
        mButtonGenerate = (Button) findViewById(R.id.activity_main_button_generate);

        mGPXBuilder = new GPX.GPXBuilder("Test").description("GPX de test").author("Gautier Ragueneau");
        mSmartLocation = SmartLocation.with(this);

        mSmartLocation.location().config(LocationParams.NAVIGATION).continuous().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {
                mGPXBuilder.addPoint(location);
            }
        });

        mButtonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGPXBuilder.build().generateGPX(new IGPX() {
                    @Override
                    public void generateListner(String gpx) {
                        mSmartLocation.location().stop();
                        Log.d("GPX", gpx);
                        mTextView.setText(gpx);
                    }
                });
            }
        });

    }
}
