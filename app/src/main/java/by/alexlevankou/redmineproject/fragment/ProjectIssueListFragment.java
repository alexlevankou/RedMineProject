package by.alexlevankou.redmineproject.fragment;


import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.activity.ProjectActivity;
import by.alexlevankou.redmineproject.model.IssueData;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProjectIssueListFragment extends IssueListFragment{

    public ProjectIssueListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_issue_list,null);

        initToolbar();
        initSwipeRefresher();
        initRecycler();
        initListHeader(view);
        setListener();
        return view;
    }

    public static ProjectIssueListFragment getInstance(Context context) {
        Bundle args = new Bundle();
        ProjectIssueListFragment fragment = new ProjectIssueListFragment();
        fragment.setArguments(args);
        fragment.setTitle(context.getString(R.string.tab_issues));
        return fragment;
    }

    protected void setSortImage(View v) {
        if(headView != null) {
            headView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        TextView tv = (TextView)v;
        tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sort_black, 0, 0, 0);
        tv.setCompoundDrawablePadding(0);
        headView = tv;
    }

    public void getInfoFromApi(){

        callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                IssueData issueData = (IssueData) o;
                list = issueData.issues;
                mAdapter.update(list);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };
        String query = String.valueOf(ProjectActivity.id);
        RedMineApplication.redMineApi.getProjectIssues(query, callback);
    }
}
