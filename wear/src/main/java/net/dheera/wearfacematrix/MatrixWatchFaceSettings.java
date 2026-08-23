package net.dheera.wearfacematrix;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.app.Activity;


public class MatrixWatchFaceSettings extends Activity {

/*

digital.ttf
ds_digib.ttf
led_counter.ttf
matrix.ttf
miltown2.ttf
mouseledumod.ttf
orbitron_medium.otf
pixellcd_7.ttf
subwayticker.ttf

*/

    public static final String DIGITAL = "digital";
    public static final String DS_DIGIB = "ds_digib";
    public static final String LED_COUNTER = "led_counter";
    public static final String MATRIX = "matrix";
    public static final String MILTOWN2 = "miltown2";
    public static final String MOUSELEDUMOD = "mouseledumod";
    public static final String ORBITRON_MEDIUM = "orbitron_medium";
    public static final String PIXELLCD_7 = "pixellcd_7";
	public static final String SUBWAYTICKER = "subwayticker";
    public static final String PATH_CONFIG = "/MatrixWatchFace/Config/";


    public static CheckBox digital;
    public static CheckBox ds_digib;
    public static CheckBox led_counter;
    public static CheckBox matrix;
    public static CheckBox miltown2;
    public static CheckBox mouseledumod;
    public static CheckBox orbitron_medium;
    public static CheckBox pixellcd_7;
    public static CheckBox subwayticker;
    ///private GoogleApiClient googleApiClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///initializeWearable();
        setContentView(R.layout.fontpicker);
        digital = (CheckBox) findViewById(R.id.digital);
        ds_digib = (CheckBox) findViewById(R.id.ds_digib);
        led_counter = (CheckBox) findViewById(R.id.led_counter);
        matrix = (CheckBox) findViewById(R.id.matrix);
        miltown2 = (CheckBox) findViewById(R.id.miltown2);
        mouseledumod = (CheckBox) findViewById(R.id.mouseledumod);
        orbitron_medium = (CheckBox) findViewById(R.id.orbitron_medium);
        pixellcd_7 = (CheckBox) findViewById(R.id.pixellcd_7);
        subwayticker = (CheckBox) findViewById(R.id.subwayticker);
        Button apply = (Button) findViewById(R.id.applybutton);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                saveValues();
                //pushValuesToWearable();

            }
        });

        loadValues();

    }


    public void saveValues() {
        setBoolean(this, DIGITAL, digital.isChecked());
        setBoolean(this, DS_DIGIB, ds_digib.isChecked());
        setBoolean(this, LED_COUNTER, led_counter.isChecked());
        setBoolean(this, MATRIX, matrix.isChecked());
        setBoolean(this, MILTOWN2, miltown2.isChecked());
        setBoolean(this, MOUSELEDUMOD, mouseledumod.isChecked());
        setBoolean(this, ORBITRON_MEDIUM, orbitron_medium.isChecked());
        setBoolean(this, PIXELLCD_7, pixellcd_7.isChecked());
        setBoolean(this, SUBWAYTICKER, subwayticker.isChecked());
    }

    public void loadValues() {
        digital.setChecked(getBoolean(this, DIGITAL, false));
        ds_digib.setChecked(getBoolean(this, DS_DIGIB, false));
        led_counter.setChecked(getBoolean(this, LED_COUNTER, false));
        matrix.setChecked(getBoolean(this, MATRIX, false));
        miltown2.setChecked(getBoolean(this, MILTOWN2, false));
        mouseledumod.setChecked(getBoolean(this, MOUSELEDUMOD, false));
        orbitron_medium.setChecked(getBoolean(this, ORBITRON_MEDIUM, false));
        pixellcd_7.setChecked(getBoolean(this, PIXELLCD_7, false));
        subwayticker.setChecked(getBoolean(this, SUBWAYTICKER, true));
    }


    public static String getString(final Context context, final String key, final String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
    }

    public static int getInt(final Context context, final String key, final int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue);
    }

    public static boolean getBoolean(final Context context, final String key,
                                     final boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
    }

    public static void setString(final Context context, final String key, final String value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setInt(final Context context, final String key, final int value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setBoolean(final Context context, final String key, final boolean value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }




}
