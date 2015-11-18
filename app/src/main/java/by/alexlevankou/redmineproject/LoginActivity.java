package by.alexlevankou.redmineproject;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
    private FloatingActionButton submitButton;
    private CoordinatorLayout coordinatorLayout;
    public String local_host = "192.168.1.17:3001";
    private  String user_id = "me";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.RedTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initLogin();
    }

    private void initLogin() {
        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
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
        StringBuilder base_url = new StringBuilder();
        base_url.append(name);
        base_url.append(":");
        base_url.append(pass);
        base_url.append("@");
        base_url.append(local_host);
        String api_url = base_url.toString();
        Log.e("tag",api_url);

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(api_url).build();
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
