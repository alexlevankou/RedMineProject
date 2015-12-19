package by.alexlevankou.redmineproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import by.alexlevankou.redmineproject.Constants;
import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.fragment.IssueListFragment;
import by.alexlevankou.redmineproject.fragment.ProjectListFragment;


public class TaskListActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Fragment issueListFragment;
    private Fragment projectListFragment;
    private FragmentTransaction fragmentTransaction;
    private final int PROJECTS_FRAGMENT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        issueListFragment = new IssueListFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame, issueListFragment);
        fragmentTransaction.commit();

        Intent intent = getIntent();
        int  type = intent.getIntExtra("fragment", RedMineApplication.EMPTY_INTENT);
        if(type == PROJECTS_FRAGMENT){
            projectListFragment = new ProjectListFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, projectListFragment);
            fragmentTransaction.commit();
        }

        initNavigation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem menuItem = menu.findItem(R.id.edit_item);
        menuItem.setVisible(false);
        menuItem = menu.findItem(R.id.settings_item);
        menuItem.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settings_item:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
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

                switch(menuItem.getItemId()) {

                    case R.id.actionNotificationProjects:
                        projectListFragment = new ProjectListFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, projectListFragment);
                        fragmentTransaction.commit();
                        break;

                    case R.id.actionNotificationIssues:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, issueListFragment);
                        fragmentTransaction.commit();
                        break;

                    case R.id.actionNotificationLogout:
                        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCES, MODE_PRIVATE);
                        SharedPreferences.Editor ed = sharedPreferences.edit();
                        ed.putString(Constants.USERNAME, null);
                        ed.putString(Constants.PASSWORD, null);
                        ed.apply();
                        TaskListActivity.this.finishAffinity();
                        break;
                }
                return true;
            }
        });
    }
}
