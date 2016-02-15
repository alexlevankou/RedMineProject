package by.alexlevankou.redmineproject.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import by.alexlevankou.redmineproject.FragmentLifecycle;
import by.alexlevankou.redmineproject.IssueComparator;
import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.adapter.RecyclerAdapter;
import by.alexlevankou.redmineproject.model.IssueData;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IssueListFragment extends AbstractFragment implements SwipeRefreshLayout.OnRefreshListener, FragmentLifecycle {

    public static SparseBooleanArray prefTracker, prefStatus, prefPriority;
    protected View view;
    protected TextView headView;
    protected SwipeRefreshLayout mSwipeLayout;
    protected RecyclerView mRecyclerView;
    protected RecyclerAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected Toolbar toolbar;
    protected TextView number, status, priority, tracker ,project ,subject, description, start_date;

    protected SharedPreferences pref;
    protected ArrayList<IssueData.Issues> list;
    protected String title;


    public IssueListFragment() {
        // Required empty public constructor
    }

    public static IssueListFragment getInstance(Context context) {
        Bundle args = new Bundle();
        IssueListFragment fragment = new IssueListFragment();
        fragment.setArguments(args);
        fragment.setTitle(context.getString(R.string.tab_issues));
        return fragment;
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

    @Override
    public void onPauseFragment() {}

    @Override
    public void onResumeFragment() {}

    protected void initToolbar(){
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        //toolbar.setTitle(R.string.issues);
    }

    protected void initListHeader(View v) {
        number = (TextView) v.findViewById(R.id.number);
        status = (TextView) v.findViewById(R.id.status);
        priority = (TextView) v.findViewById(R.id.priority);
        tracker = (TextView) v.findViewById(R.id.tracker);
        project = (TextView) v.findViewById(R.id.project);
        subject = (TextView) v.findViewById(R.id.subject);
        description = (TextView) v.findViewById(R.id.description);
        start_date = (TextView) v.findViewById(R.id.start_date);

        headView = null;
    }

    protected void setListener(){

        View.OnClickListener tabClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Collections.sort(list, new IssueComparator(v.getId()));
                mAdapter.update(list);
                setSortImage(v);
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

    protected void setSortImage(View v) {
        if(headView != null) {
            headView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        TextView tv = (TextView)v;
        tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sort, 0, 0, 0);
        tv.setCompoundDrawablePadding(0);
        headView = tv;
    }

    protected void formListHeader() {
        PreferenceManager.setDefaultValues(getContext(), R.xml.preferences, false);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        checkVisibility(status,pref.getBoolean("status_chb",true));
        checkVisibility(priority,pref.getBoolean("priority_chb",true));
        checkVisibility(tracker,pref.getBoolean("tracker_chb",true));
        checkVisibility(project,pref.getBoolean("project_chb",true));
        checkVisibility(subject, pref.getBoolean("subject_chb", false));
        checkVisibility(description, pref.getBoolean("description_chb", false));
        checkVisibility(start_date, pref.getBoolean("start_date_chb", false));

        HashMap<Integer, String> trackerMap = RedMineApplication.getTrackerData().getMappedData();
        prefTracker = new SparseBooleanArray();
        for (Map.Entry<Integer, String> entry : trackerMap.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            prefTracker.put(key, pref.getBoolean("chb_" + value, true));
        }
        HashMap<Integer, String> priorityMap = RedMineApplication.getPriorityData().getMappedData();
        prefPriority = new SparseBooleanArray();
        for (Map.Entry<Integer, String> entry : priorityMap.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            prefPriority.put(key, pref.getBoolean("chb_" + value, true));
        }

        HashMap<Integer, String> statusMap = RedMineApplication.getStatusData().getMappedData();
        prefStatus = new SparseBooleanArray();
        for (Map.Entry<Integer, String> entry : statusMap.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            prefStatus.put(key, pref.getBoolean("chb_" + value, true));
        }
    }

    protected void initSwipeRefresher() {
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent
        );
    }

    protected void initRecycler() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        // используем linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void checkVisibility(TextView textView, Boolean boo){
        if(boo){
            textView.setVisibility(View.VISIBLE);
        }else{
            textView.setVisibility(View.GONE);
        }
    }

    public void getInfoFromApi(){
            RedMineApplication.redMineApi.getIssues("me")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        issueData -> {
                            list = issueData.issues;
                            mAdapter.update(list);
                });
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public RecyclerAdapter getAdapter(){
        return mAdapter;
    }
}
