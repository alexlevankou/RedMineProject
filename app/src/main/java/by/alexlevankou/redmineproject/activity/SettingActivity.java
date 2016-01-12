package by.alexlevankou.redmineproject.activity;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApplication;

public class SettingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.preferences);
        getFragmentManager().beginTransaction().replace(R.id.frame, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        public PreferenceScreen status_screen;
        public PreferenceScreen priority_screen;
        public PreferenceScreen tracker_screen;

        private ArrayList<String> trackerNames;
        private ArrayList<String> statusNames;
        private ArrayList<String> priorityNames;

        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            setPreferences();
        }

        private void setPreferences() {

            trackerNames = RedMineApplication.getTrackerData().getNameList();
            tracker_screen = (PreferenceScreen) findPreference("tracker_screen");
            for(String name: trackerNames) {
                CheckBoxPreference chb = new CheckBoxPreference(getActivity());
                chb.setKey("chb_" + name);
                chb.setDefaultValue(true);
                chb.setTitle(name);
                tracker_screen.addPreference(chb);
            }

            priorityNames = RedMineApplication.getPriorityData().getNameList();
            priority_screen = (PreferenceScreen) findPreference("priority_screen");
            for(String name: priorityNames){
                CheckBoxPreference chb = new CheckBoxPreference(getActivity());
                chb.setKey("chb_"+name);
                chb.setDefaultValue(true);
                chb.setTitle(name);
                priority_screen.addPreference(chb);
            }

            statusNames = RedMineApplication.getStatusData().getNameList();
            status_screen = (PreferenceScreen) findPreference("status_screen");
            for(String name: statusNames) {
                CheckBoxPreference chb = new CheckBoxPreference(getActivity());
                chb.setKey("chb_" + name);
                chb.setDefaultValue(true);
                chb.setTitle(name);
                status_screen.addPreference(chb);
            }
            /*Callback trackerCallback = new Callback() {
                @Override
                public void success(Object o, Response response) {
                    TrackerData trackerData = (TrackerData)o;
                    trackerNames = trackerData.getNameList();

                    tracker_screen = (PreferenceScreen) findPreference("tracker_screen");
                    for(String name: trackerNames){
                        CheckBoxPreference chb = new CheckBoxPreference(getActivity());
                        chb.setKey("chb_" + name);
                        chb.setDefaultValue(true);
                        chb.setTitle(name);
                        tracker_screen.addPreference(chb);
                    }
                }
                @Override
                public void failure(RetrofitError retrofitError) {
                    retrofitError.printStackTrace();
                }
            };

            Callback priorityCallback = new Callback() {
                @Override
                public void success(Object o, Response response) {
                    PriorityData priorData = (PriorityData)o;
                    priorityNames = priorData.getNameList();

                    priority_screen = (PreferenceScreen) findPreference("priority_screen");
                    for(String name: priorityNames){
                        CheckBoxPreference chb = new CheckBoxPreference(getActivity());
                        chb.setKey("chb_"+name);
                        chb.setDefaultValue(true);
                        chb.setTitle(name);
                        priority_screen.addPreference(chb);
                    }
                }
                @Override
                public void failure(RetrofitError retrofitError) {
                    retrofitError.printStackTrace();
                }
            };

            Callback statusCallback = new Callback() {
                @Override
                public void success(Object o, Response response) {
                    StatusData statusData = (StatusData)o;
                    statusNames = statusData.getNameList();

                    status_screen = (PreferenceScreen) findPreference("status_screen");
                    for(String name: statusNames){
                        CheckBoxPreference chb = new CheckBoxPreference(getActivity());
                        chb.setKey("chb_"+name);
                        chb.setDefaultValue(true);
                        chb.setTitle(name);
                        status_screen.addPreference(chb);
                    }
                }
                @Override
                public void failure(RetrofitError retrofitError) {
                    retrofitError.printStackTrace();
                }
            };
            RedMineApplication.redMineApi.getTrackers(trackerCallback);
            RedMineApplication.redMineApi.getPriorities(priorityCallback);
            RedMineApplication.redMineApi.getStatuses(statusCallback);*/
        }
    }
}
