package by.alexlevankou.redmineproject.activity;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import by.alexlevankou.redmineproject.Constants;
import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.adapter.RecyclerAdapter;
import by.alexlevankou.redmineproject.fragment.ErrorDialogFragment;
import by.alexlevankou.redmineproject.fragment.IssueEditFragment;
import by.alexlevankou.redmineproject.fragment.IssuePropertiesFragment;
import by.alexlevankou.redmineproject.fragment.ProjectIssueListFragment;
import by.alexlevankou.redmineproject.fragment.SubmitDialogFragment;
import by.alexlevankou.redmineproject.model.IssueCreator;
import by.alexlevankou.redmineproject.model.IssueData;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PropertyActivity extends AppCompatActivity {

    public static IssueData.Issues issue;
    private static long id;
    private FloatingActionButton fab;
    private Callback callback;
    private IssueData issueData;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.submit_fab);
        fab.hide();
        Intent intent = getIntent();
        id = intent.getLongExtra("id", -1);

        getInfoFromApi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem menuItem = menu.findItem(R.id.settings_item);
        menuItem.setVisible(false);
        menuItem = menu.findItem(R.id.search_item);
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

    public void onClickFAB(View v){

        Callback cb = new Callback() {
            @Override
            public void success(Object o, Response response) {
                DialogFragment fragment = new SubmitDialogFragment();
                fragment.show(getSupportFragmentManager(), "Submit");
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };

        IssueCreator iss  = new IssueCreator();
        IssueEditFragment editFragment = (IssueEditFragment) fragment;
        iss = editFragment.prepareIssue(iss);

        String taskId = String.valueOf(id);
        RedMineApplication.redMineApi.updateIssue(iss,taskId, cb);
    }

/*
    @TargetApi(21)
    private void createAnimation() {
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.frame);
        int x = (int)fab.getX();
        int y = (int)fab.getY();
        int radius = frameLayout.getWidth();
        Animator anim = ViewAnimationUtils.createCircularReveal(frameLayout, x, y, 0, radius);
        anim.start();
    }
*/

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
        RedMineApplication.redMineApi.showIssue(query, callback);
    }
}
