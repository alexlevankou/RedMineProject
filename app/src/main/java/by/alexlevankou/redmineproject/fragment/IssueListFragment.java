package by.alexlevankou.redmineproject.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import by.alexlevankou.redmineproject.IssueComparator;
import by.alexlevankou.redmineproject.model.IssueData;
import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.adapter.RecyclerAdapter;
import by.alexlevankou.redmineproject.RedMineApplication;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class IssueListFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {

    public static SparseBooleanArray prefTracker, prefStatus, prefPriority;
    private View view;
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;
    private TextView number, status, priority, tracker ,project ,subject, description, start_date;

    private SharedPreferences pref;
    private Callback callback;
    private ArrayList<IssueData.Issues> list;


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
        initListHeader(view);
        setListener();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        formListHeader();
        getInfoFromApi();
    }

    @Override
    public void onRefresh() {
        getInfoFromApi();
        // stop refresh
        mSwipeLayout.setRefreshing(false);

    }

    private void initToolbar(){
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.issues);
    }

    private void initListHeader(View v) {
        number = (TextView) v.findViewById(R.id.number);
        status = (TextView) v.findViewById(R.id.status);
        priority = (TextView) v.findViewById(R.id.priority);
        tracker = (TextView) v.findViewById(R.id.tracker);
        project = (TextView) v.findViewById(R.id.project);
        subject = (TextView) v.findViewById(R.id.subject);
        description = (TextView) v.findViewById(R.id.description);
        start_date = (TextView) v.findViewById(R.id.start_date);
    }

    private void setListener(){

        View.OnClickListener tabClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Collections.sort(list, new IssueComparator(v.getId()));
                mAdapter.update(list);
            }
        };
        number.setOnClickListener(tabClickListener);
        status.setOnClickListener(tabClickListener);
        priority.setOnClickListener(tabClickListener);
        tracker.setOnClickListener(tabClickListener);
        project.setOnClickListener(tabClickListener);
        subject.setOnClickListener(tabClickListener);
        description.setOnClickListener(tabClickListener);
        start_date.setOnClickListener(tabClickListener);
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

        prefStatus = new SparseBooleanArray();
        prefStatus.put(1, pref.getBoolean("new_chb", true));
        prefStatus.put(2, pref.getBoolean("in_progress_chb", true));
        prefStatus.put(7, pref.getBoolean("on_hold_chb", true));
        prefStatus.put(8, pref.getBoolean("clarification_chb", true));
        prefStatus.put(3, pref.getBoolean("resolved_chb", true));
        prefStatus.put(10, pref.getBoolean("verified_chb", true));
        prefStatus.put(5,pref.getBoolean("closed_chb", true));
        prefStatus.put(6,pref.getBoolean("not_valid_chb",true));

        prefTracker = new SparseBooleanArray();
        prefTracker.put(2,pref.getBoolean("feature_chb",true));
        prefTracker.put(4, pref.getBoolean("grp_feature_chb", true));
        prefTracker.put(1, pref.getBoolean("defect_chb", true));
        prefTracker.put(5, pref.getBoolean("qa_chb", true));
        prefTracker.put(3, pref.getBoolean("support_chb", true));
        prefTracker.put(6, pref.getBoolean("suggestion_chb", true));

        prefPriority = new SparseBooleanArray();
        prefPriority.put(1,pref.getBoolean("low_chb", true));
        prefPriority.put(2,pref.getBoolean("normal_chb", true));
        prefPriority.put(3,pref.getBoolean("high_chb", true));
        prefPriority.put(4,pref.getBoolean("urgent_chb", true));
        prefPriority.put(5, pref.getBoolean("immediate_chb", true));
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
    }
}
