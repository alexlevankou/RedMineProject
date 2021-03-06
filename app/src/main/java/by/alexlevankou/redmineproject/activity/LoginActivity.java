package by.alexlevankou.redmineproject.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Field;

import by.alexlevankou.redmineproject.Constants;
import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApi;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.ServiceGenerator;
import by.alexlevankou.redmineproject.fragment.ErrorDialogFragment;
import by.alexlevankou.redmineproject.model.PriorityData;
import by.alexlevankou.redmineproject.model.StatusData;
import by.alexlevankou.redmineproject.model.TrackerData;
import by.alexlevankou.redmineproject.model.UserData;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private int syncAccess = 0;
    private CoordinatorLayout coordinatorLayout;
    private String name;
    private  String pass;
    private  String url;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCES, MODE_PRIVATE);
        name = sharedPreferences.getString(Constants.USERNAME, null);
        pass = sharedPreferences.getString(Constants.PASSWORD, null);
        url = sharedPreferences.getString(Constants.URL, null);

        if(name != null && pass != null && url != null) {
            enter();
        }else{
            initLogin();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login_toolbar, menu);
        final MenuItem menuItem = menu.findItem(R.id.connect_item);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        try {
            Field submitField = SearchView.class.getDeclaredField("mGoButton");
            submitField.setAccessible(true);
            ImageView subBtn = (ImageView) submitField.get(searchView);
            subBtn.setImageResource(R.drawable.ic_submit);
        } catch (NoSuchFieldException e) {
            Log.e("SearchView", e.getMessage(), e);
        } catch (IllegalAccessException e) {
            Log.e("SearchView", e.getMessage(), e);
        }

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(LoginActivity.this, "Submit", Toast.LENGTH_LONG).show();

                Editor ed = sharedPreferences.edit();
                ed.putString(Constants.URL, query);
                ed.apply();
                url = query;
                menuItem.collapseActionView();
                return true;
            }
        });
        return true;
    }

    private void initLogin() {
        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        FloatingActionButton submitButton;
        submitButton = (FloatingActionButton) findViewById(R.id.submit_fab);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.login_coordinator_layout);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fabClicked();
            }
        });
    }

    private void fabClicked(){
        name = username.getText().toString();
        pass = password.getText().toString();
        Editor ed = sharedPreferences.edit();
        ed.putString(Constants.USERNAME, name);
        ed.putString(Constants.PASSWORD, pass);
        ed.apply();
        enter();
    }

    private void getSTP(){
        syncAccess = 0;

        Callback trackerCallback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                TrackerData trackerData = (TrackerData) o;
                RedMineApplication.setTrackerData(trackerData);
                syncAccess++;
                connect();
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
                clearPreferences();
                initLogin();
            }
        };

        Callback priorityCallback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                PriorityData priorData = (PriorityData) o;
                RedMineApplication.setPriorityData(priorData);
                syncAccess++;
                connect();
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
                clearPreferences();
                initLogin();
            }
        };

        Callback statusCallback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                StatusData statusData = (StatusData) o;
                RedMineApplication.setStatusData(statusData);
                syncAccess++;
                connect();
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
                clearPreferences();
                initLogin();
            }
        };
        RedMineApplication.redMineApi.getTrackers(trackerCallback);
        RedMineApplication.redMineApi.getPriorities(priorityCallback);
        RedMineApplication.redMineApi.getStatuses(statusCallback);
    }

    private void connect(){
        if(syncAccess == 3){
            Intent intent = new Intent(LoginActivity.this,TaskListActivity.class);
            startActivity(intent);
        }
    }

    private void enter(){
        RedMineApplication.redMineApi = ServiceGenerator.createService(this,RedMineApi.class, name, pass, url);

        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                UserData userData = (UserData)o;
                RedMineApplication.setCurrentUser(userData.getUser());
                getSTP();
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
                clearPreferences();
                DialogFragment fragment = new ErrorDialogFragment();
                fragment.show(getSupportFragmentManager(), "Error");
            }
        };
        RedMineApplication.redMineApi.getUser(callback);
    }

    private void clearPreferences(){
        Editor ed = sharedPreferences.edit();
        ed.putString(Constants.USERNAME, null);
        ed.putString(Constants.PASSWORD, null);
        ed.apply();
    }
}
