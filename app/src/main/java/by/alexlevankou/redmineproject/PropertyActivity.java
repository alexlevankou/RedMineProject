package by.alexlevankou.redmineproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import by.alexlevankou.redmineproject.fragment.IssueEditFragment;
import by.alexlevankou.redmineproject.fragment.IssuePropertiesFragment;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PropertyActivity extends AppCompatActivity {

    public static RedMineApi redMineApi;
    IssueData issueData;
    public static IssueData.Issues issue;
    public static long id;
    private FloatingActionButton fab;
    private Callback callback;
    SharedPreferences sharedPreferences;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.submit_fab);
        fab.hide();
        Intent intent = getIntent();
        id = intent.getLongExtra("id",-1);

        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCES, MODE_PRIVATE);
        String name = sharedPreferences.getString(Constants.USERNAME, null);
        String pass = sharedPreferences.getString(Constants.PASSWORD, null);
        redMineApi = ServiceGenerator.createService(this, RedMineApi.class, name, pass);

        getInfoFromApi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem menuItem = menu.findItem(R.id.settings_item);
        menuItem.setVisible(false);
        menuItem = menu.findItem(R.id.edit_item);
        menuItem.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.edit_item:
                item.setVisible(false);
                fragment = new IssueEditFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commit();
                fab.show();
                break;
            default:
                break;
        }
        return true;
    }

    public void onClickEditFragment(View v) {
        IssueEditFragment frag = (IssueEditFragment) fragment;
        frag.showDatePickerDialog(v);
    }

    public void onClickFAB(View v){

        Callback cb = new Callback() {
            @Override
            public void success(Object o, Response response) {
                Intent intent = new Intent(PropertyActivity.this,TaskListActivity.class);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };

       // IssueData iss = new IssueData();
        IssueCreator iss  = new IssueCreator();
        IssueEditFragment editFragment = (IssueEditFragment) fragment;

        iss.setSubject(editFragment.subject.getText().toString());
        iss.setDescription(editFragment.description.getText().toString());
        iss.setStartDate(editFragment.start_date.getText().toString());

        String selectedVal;
        selectedVal = getResources().getStringArray(R.array.tracker_values)[editFragment.tracker.getSelectedItemPosition()];
        iss.setTracker(selectedVal);
        selectedVal = getResources().getStringArray(R.array.status_values)[editFragment.status.getSelectedItemPosition()];
        iss.setStatus(selectedVal);
        selectedVal = getResources().getStringArray(R.array.priority_values)[editFragment.priority.getSelectedItemPosition()];
        iss.setPriority(selectedVal);

       //New-In Progress-3 Resolved-4 Resolved-Closed-Not Valid-On Hold-Wait for Clarification-ReOpen-11 Verified
       //Defect-Feature-Support-GRP-QA-Suggest
        //Low-Normal-High-Urgent-Immediate
        //сделать отдельные массивы для дефекта и других

        String taskId = String.valueOf(id);
        redMineApi.updateIssue(iss,taskId, cb);
    }

    private void getInfoFromApi(){

        callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                issueData = (IssueData)o;
                issue = issueData.issue;
                fragment = new IssuePropertiesFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frame, fragment);
                fragmentTransaction.commit();
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };

        String query = String.valueOf(id);
        redMineApi.showIssue(query, callback);
    }
}
