package lab1.tppa.laboratortest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            Log.d("Settings-activity", "Setting from resouce.");

            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            Log.d("Settings-activity", "Preference manager getting default preferences.");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());

            Log.d("Settings-activity", "Getting settings.");

            boolean storedDarkMode = preferences.getBoolean("dark-mode", false);
            String storedProductsShown = preferences.getString("products-shown", "0");
            boolean storedNotifications = preferences.getBoolean("notifications", false);

            Log.d("Settings-activity", "Setting settings.");

            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.putBoolean("dark-mode", storedDarkMode);
            editor.putString("products-shown", storedProductsShown);
            editor.putBoolean("notifications", storedNotifications);
            editor.commit();

            Log.d("Settings-activity", "Finished.");
        }
    }
}