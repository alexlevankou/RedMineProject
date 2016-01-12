package by.alexlevankou.redmineproject.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import by.alexlevankou.redmineproject.Constants;
import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.adapter.RecyclerAdapter;
import by.alexlevankou.redmineproject.adapter.RecyclerProjectAdapter;
import by.alexlevankou.redmineproject.fragment.ExitDialogFragment;
import by.alexlevankou.redmineproject.fragment.IssueListFragment;
import by.alexlevankou.redmineproject.fragment.ProjectListFragment;


public class TaskListActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Fragment issueListFragment;
    private Fragment projectListFragment;
    private FragmentTransaction fragmentTransaction;
    private final int PROJECTS_FRAGMENT = 1;

    private RecyclerAdapter recyclerAdapter;
    private RecyclerProjectAdapter recyclerProjectAdapter;

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
        //call from ProjectActivity
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

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search_item);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {

                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                if (fragment instanceof IssueListFragment) {
                    IssueListFragment frag = (IssueListFragment) fragment;
                    recyclerAdapter = frag.getAdapter();
                    recyclerAdapter.search(newText);
                } else if (fragment instanceof ProjectListFragment) {
                    ProjectListFragment frag = (ProjectListFragment) fragment;
                    recyclerProjectAdapter = frag.getAdapter();
                    recyclerProjectAdapter.search(newText);
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

        });


     /*   MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                Log.e("SearchView", "Collapsed");

                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
                if (fragment instanceof IssueListFragment) {
                   //not working
                    recyclerAdapter.filter();
                } else if (fragment instanceof ProjectListFragment) {
                    ProjectListFragment frag = (ProjectListFragment) fragment;
                    RecyclerProjectAdapter recyclerProjectAdapter = frag.getAdapter();
                    recyclerProjectAdapter.update();
                }
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;  // Return true to expand action view
            }
        });*/
        searchItem.setVisible(true);

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
                        DialogFragment fragment = new ExitDialogFragment();
                        fragment.show(getSupportFragmentManager(), "EXIT");
                        break;
                }
                return true;
            }
        });
    }
}
