package by.alexlevankou.redmineproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import by.alexlevankou.redmineproject.Constants;
import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.adapter.ViewPagerAdapter;
import by.alexlevankou.redmineproject.fragment.ExitDialogFragment;
import by.alexlevankou.redmineproject.fragment.OverviewFragment;
import by.alexlevankou.redmineproject.model.IssueData;
import by.alexlevankou.redmineproject.model.ProjectData;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProjectActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private ViewPager viewPager;

    public static ProjectData projectData;
    public static IssueData projectIssueData;
    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", RedMineApplication.EMPTY_INTENT);

        getInfoFromApi(id);
        //initToolbar();
        initNavigation();
        initTabs();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(projectData.project.getName());
    }

    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getInfoFromApi(int id){

        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                projectData = (ProjectData)o;
                initToolbar();
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };

        Callback callbackProjectIssues = new Callback() {
            @Override
            public void success(Object o, Response response) {
                projectIssueData = (IssueData)o;
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };

        String query = String.valueOf(id);
        RedMineApplication.redMineApi.showProject(query, callback);
        RedMineApplication.redMineApi.getProjectIssues(query, callbackProjectIssues);

    }

    private void initNavigation(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {

                    case R.id.actionNotificationProjects:
                        Intent intent = new Intent(ProjectActivity.this, TaskListActivity.class);
                        intent.putExtra("fragment", 1);
                        startActivity(intent);
                        break;

                    case R.id.actionNotificationIssues:
                        Intent purpose = new Intent(ProjectActivity.this, TaskListActivity.class);
                        startActivity(purpose);
                        break;

                    case R.id.actionNotificationLogout:
                        DialogFragment fragment = new ExitDialogFragment();
                        fragment.show(getSupportFragmentManager(), "EXIT");
                        break;
                }
                return true;
            }
        });
    }
}
