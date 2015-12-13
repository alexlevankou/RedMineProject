package by.alexlevankou.redmineproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import by.alexlevankou.redmineproject.Constants;
import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApi;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.ServiceGenerator;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private CoordinatorLayout coordinatorLayout;
    private String name;
    private  String pass;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCES, MODE_PRIVATE);
        name = sharedPreferences.getString(Constants.USERNAME, null);
        pass = sharedPreferences.getString(Constants.PASSWORD, null);

        if(name != null && pass != null) {
            RedMineApplication.redMineApi = ServiceGenerator.createService(this, RedMineApi.class, name, pass);
            Intent intent = new Intent(LoginActivity.this,TaskListActivity.class);
            startActivity(intent);
        }else{
            initLogin();
        }
    }

    private void initLogin() {
        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        toolbar.setTitle(R.string.app_name);
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

        RedMineApplication.redMineApi = ServiceGenerator.createService(this,RedMineApi.class, name, pass);

        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                Intent intent = new Intent(LoginActivity.this,TaskListActivity.class);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Snackbar.make(coordinatorLayout,"Wrong Login or Password",Snackbar.LENGTH_LONG).show();
            }
        };

        RedMineApplication.redMineApi.login("me",callback);
    }
}
