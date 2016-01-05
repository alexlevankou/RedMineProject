package by.alexlevankou.redmineproject.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import by.alexlevankou.redmineproject.FragmentLifecycle;
import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.activity.ProjectActivity;
import by.alexlevankou.redmineproject.adapter.ListAdapter;
import by.alexlevankou.redmineproject.adapter.MembershipAdapter;
import by.alexlevankou.redmineproject.adapter.TrackingAdapter;
import by.alexlevankou.redmineproject.model.ProjectMembership;
import by.alexlevankou.redmineproject.model.TrackerData;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OverviewFragment extends AbstractFragment implements FragmentLifecycle {

    private ArrayList<TrackerData.Tracker> trackers;
    private ArrayList<ProjectMembership.Member> members;

    private ListAdapter<TrackerData.Tracker> trackingAdapter;
    private ListAdapter<ProjectMembership.Member> membershipAdapter;

    private ListView trackerListView;
    private ListView memberListView;

    public OverviewFragment() {
        // Required empty public constructor
    }

    public static OverviewFragment getInstance(Context context) {
        Bundle args = new Bundle();
        OverviewFragment fragment = new OverviewFragment();
        fragment.setArguments(args);
        fragment.setTitle(context.getString(R.string.tab_overview));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_overview,null);
        initRecycler(view);
        getInfoFromApi();
        return view;
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }

    private void initRecycler(View view) {
        trackerListView = (ListView)view.findViewById(R.id.lvTrackers);
        memberListView = (ListView)view.findViewById(R.id.lvMembers);
    }

    private void getInfoFromApi(){

        Callback callbackTrackers = new Callback() {
            @Override
            public void success(Object o, Response response) {
                TrackerData trackerData = (TrackerData)o;
                trackers  = trackerData.getTrackers();
                trackingAdapter = new TrackingAdapter(getContext(),trackers);
                trackerListView.setAdapter(trackingAdapter);
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };

        Callback callbackMembers = new Callback() {
            @Override
            public void success(Object o, Response response) {
                ProjectMembership membership = (ProjectMembership)o;
                members  = membership.getMembers();
                membershipAdapter = new MembershipAdapter(getContext(),members);
                memberListView.setAdapter(membershipAdapter);
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };

        String query = String.valueOf(ProjectActivity.id);
        RedMineApplication.redMineApi.getTrackers(callbackTrackers);
        RedMineApplication.redMineApi.getProjectMembership(query, callbackMembers);
    }
}
