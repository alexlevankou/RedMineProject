package by.alexlevankou.redmineproject;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Toolbar toolbar;
    private FloatingActionButton submitButton;
    private CoordinatorLayout coordinatorLayout;
    private static final String API_URL = "http://192.168.1.17:3001";
    private  String user_id = "me";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initLogin();
    }

    private void initLogin() {
        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
        toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        toolbar.setTitle("RedMine");
        submitButton = (FloatingActionButton) findViewById(R.id.submit_fab);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.login_coordinator_layout);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fabClicked();
            }
        });
    }
    private void fabClicked(){

        String name = username.getText().toString();
        String pass = password.getText().toString();


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API_URL).build();
        RedMineApi redMine = restAdapter.create(RedMineApi.class);
        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                IssueData issueData = (IssueData)o;
                int count = issueData.getTotalCount();
                Snackbar.make(coordinatorLayout,count,Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
                Snackbar.make(coordinatorLayout,"Failure",Snackbar.LENGTH_LONG).show();

            }
        };
        redMine.getIssues("me", callback);

    }

}
