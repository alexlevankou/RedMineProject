package by.alexlevankou.redmineproject.activity;

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

import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.fragment.IssueEditFragment;
import by.alexlevankou.redmineproject.fragment.IssuePropertiesFragment;
import by.alexlevankou.redmineproject.fragment.SubmitDialogFragment;
import by.alexlevankou.redmineproject.model.IssueCreator;
import by.alexlevankou.redmineproject.model.IssueData;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PropertyActivity extends AppCompatActivity {

    public static IssueData.Issues issue;
    private static long id;
    private FloatingActionButton fab;
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

        IssueCreator iss  = new IssueCreator();
        IssueEditFragment editFragment = (IssueEditFragment) fragment;
        editFragment.prepareIssue(iss);
        String taskId = String.valueOf(id);

        RedMineApplication.redMineApi.updateIssue(iss, taskId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            DialogFragment fragment = new SubmitDialogFragment();
                            fragment.show(getSupportFragmentManager(), "Submit");
                        });
    }

    private void getInfoFromApi(){
        String query = String.valueOf(id);
        RedMineApplication.redMineApi.showIssue(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        issueData -> {
                            issue = issueData.issue;
                            fragment = new IssuePropertiesFragment();
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.frame, fragment);
                            fragmentTransaction.commit();
                        });
    }
}
