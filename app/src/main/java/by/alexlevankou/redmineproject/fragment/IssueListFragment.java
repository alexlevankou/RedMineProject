package by.alexlevankou.redmineproject.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import by.alexlevankou.redmineproject.IssueComparator;
import by.alexlevankou.redmineproject.IssueData;
import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RecyclerAdapter;
import by.alexlevankou.redmineproject.RedMineApi;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.TaskListActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class IssueListFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {

    private View view;
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;
    public TextView status, priority, tracker ,project ,subject, description, start_date;
    public static Map shownTracker, shownStatus, shownPriority;

    SharedPreferences pref;
    Callback callback;
    public ArrayList<IssueData.Issues> list;


    public IssueListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_issue_list,null);
        initToolbar();
        initSwipeRefresher();
        initRecycler();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initListHeader();
        formListHeader();
        getInfoFromApi();
    }

    @Override
    public void onRefresh() {
        getInfoFromApi();
        // stop refresh
        mSwipeLayout.setRefreshing(false);

    }

    public void onClick(View v){
        Collections.sort(list,new IssueComparator(v.getId()));
        mAdapter.update(list);
    }

    private void initToolbar(){
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.issues);
    }

    private void initListHeader() {
        status = (TextView) getActivity().findViewById(R.id.status);
        priority = (TextView) getActivity().findViewById(R.id.priority);
        tracker = (TextView) getActivity().findViewById(R.id.tracker);
        project = (TextView) getActivity().findViewById(R.id.project);
        subject = (TextView) getActivity().findViewById(R.id.subject);
        description = (TextView) getActivity().findViewById(R.id.description);
        start_date = (TextView) getActivity().findViewById(R.id.start_date);
    }

    private void formListHeader() {
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        checkVisibility(status,pref.getBoolean("status_chb",false));
        checkVisibility(priority,pref.getBoolean("priority_chb",false));
        checkVisibility(tracker,pref.getBoolean("tracker_chb",false));
        checkVisibility(project,pref.getBoolean("project_chb",false));
        checkVisibility(subject, pref.getBoolean("subject_chb", false));
        checkVisibility(description, pref.getBoolean("description_chb", false));
        checkVisibility(start_date, pref.getBoolean("start_date_chb", false));

        shownStatus = new HashMap<Integer,Boolean>();
        shownStatus.put(1,pref.getBoolean("new_chb",true));
        shownStatus.put(2,pref.getBoolean("in_progress_chb",true));
        shownStatus.put(7,pref.getBoolean("on_hold_chb", true));
        shownStatus.put(8,pref.getBoolean("clarification_chb", true));
        shownStatus.put(3,pref.getBoolean("resolved_chb", true));
        shownStatus.put(10,pref.getBoolean("verified_chb", true));
        shownStatus.put(5,pref.getBoolean("closed_chb", true));
        shownStatus.put(6,pref.getBoolean("not_valid_chb",true));

        shownTracker = new HashMap<Integer,Boolean>();
        shownTracker.put(2,pref.getBoolean("feature_chb",true));
        shownTracker.put(4, pref.getBoolean("grp_feature_chb", true));
        shownTracker.put(1, pref.getBoolean("defect_chb", true));
        shownTracker.put(5, pref.getBoolean("qa_chb", true));
        shownTracker.put(3, pref.getBoolean("support_chb", true));
        shownTracker.put(6, pref.getBoolean("suggestion_chb", true));

        shownPriority = new HashMap<Integer,Boolean>();
        shownPriority.put(1,pref.getBoolean("low_chb", true));
        shownPriority.put(2,pref.getBoolean("normal_chb", true));
        shownPriority.put(3,pref.getBoolean("high_chb", true));
        shownPriority.put(4,pref.getBoolean("urgent_chb", true));
        shownPriority.put(5, pref.getBoolean("immediate_chb", true));
    }

    private void initSwipeRefresher() {
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent
        );
    }

    private void initRecycler() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        // используем linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void checkVisibility(TextView textView, Boolean boo){
        if(boo){
            textView.setVisibility(View.VISIBLE);
        }else{
            textView.setVisibility(View.GONE);
        }
    }

    private void getInfoFromApi(){
       callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                IssueData issueData = (IssueData)o;
                list = issueData.issues;
                mAdapter.update(list);

            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };
        RedMineApplication.redMineApi.getIssues("me", callback);
        //TaskListActivity.redMineApi.getIssues("me", callback);
    }
}
