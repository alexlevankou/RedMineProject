package by.alexlevankou.redmineproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.adapter.RecyclerAdapter;
import by.alexlevankou.redmineproject.adapter.RecyclerProjectAdapter;
import by.alexlevankou.redmineproject.model.ProjectData;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ProjectListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerProjectAdapter projectAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Callback callback;
    private ArrayList<ProjectData.Project> list;

    public ProjectListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_list,null);
        initToolbar();
        initRecycler(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getInfoFromApi();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.projects);
    }

    private void initRecycler(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        // используем linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        projectAdapter = new RecyclerProjectAdapter(getContext());
        mRecyclerView.setAdapter(projectAdapter);
    }

    private void getInfoFromApi() {
        callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                ProjectData projectData = (ProjectData)o;
                list = projectData.projects;
                projectAdapter.update(list);
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };
        RedMineApplication.redMineApi.getProjects(callback);
    }

    public RecyclerProjectAdapter getAdapter(){
        return projectAdapter;
    }


}
