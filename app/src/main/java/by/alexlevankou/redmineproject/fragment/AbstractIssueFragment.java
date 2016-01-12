package by.alexlevankou.redmineproject.fragment;


import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import java.util.ArrayList;

import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.model.IssueCreator;
import by.alexlevankou.redmineproject.model.PriorityData;
import by.alexlevankou.redmineproject.model.StatusData;
import by.alexlevankou.redmineproject.model.TrackerData;

public abstract class AbstractIssueFragment  extends AbstractFragment {

    protected int sync =0;
    protected View view;
    protected Toolbar toolbar;

    public AppCompatSpinner tracker;
    public EditText subject;
    public EditText description;
    public AppCompatSpinner status;
    public AppCompatSpinner priority;
    public AppCompatSpinner assignee;


    protected final static int START_DATE = 1;
    protected final static int DUE_DATE = 2;

    protected ArrayAdapter<String> assigneeAdapter;
    protected ArrayAdapter<String> trackerAdapter;
    protected ArrayAdapter<String> statusAdapter;
    protected ArrayAdapter<String> priorityAdapter;

    protected ArrayList<Integer> assigneeIdentifiers;
    protected ArrayList<Integer> trackerIdentifiers;
    protected ArrayList<Integer> statusIdentifiers;
    protected ArrayList<Integer> priorityIdentifiers;

    public AbstractIssueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_issue_edit,null);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
        getSpinnerData();
    }


    protected abstract void setData();
    protected abstract IssueCreator prepareIssue(IssueCreator issueCreator);

    protected void initData() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        tracker  = (AppCompatSpinner) getActivity().findViewById(R.id.tracker_spinner);
        subject = (EditText) getActivity().findViewById(R.id.subject_text);
        description  = (EditText) getActivity().findViewById(R.id.description_text);
        status  = (AppCompatSpinner) getActivity().findViewById(R.id.status_spinner);
        priority  = (AppCompatSpinner) getActivity().findViewById(R.id.priority_spinner);
        assignee  = (AppCompatSpinner) getActivity().findViewById(R.id.assignee_spinner);
    }

    protected void getSpinnerData() {

        TrackerData trackerData = RedMineApplication.getTrackerData();
        trackerIdentifiers = trackerData.getIdList();
        trackerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, trackerData.getNameList());
        trackerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tracker.setAdapter(trackerAdapter);

        PriorityData priorData = RedMineApplication.getPriorityData();
        priorityIdentifiers = priorData.getIdList();
        priorityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, priorData.getNameList());
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priority.setAdapter(priorityAdapter);

        StatusData statusData = RedMineApplication.getStatusData();
        statusIdentifiers = statusData.getIdList();
        statusAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, statusData.getNameList());
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(statusAdapter);


        /*sync=0;
        Callback trackerCallback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                TrackerData trackerData = (TrackerData)o;
                trackerIdentifiers = trackerData.getIdList();
                trackerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, trackerData.getNameList());
                trackerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tracker.setAdapter(trackerAdapter);
                sync++;
                allowToSet();
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };

        Callback priorityCallback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                PriorityData priorData = (PriorityData)o;
                priorityIdentifiers = priorData.getIdList();
                priorityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, priorData.getNameList());
                priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                priority.setAdapter(priorityAdapter);
                sync++;
                allowToSet();
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };

        Callback statusCallback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                StatusData statusData = (StatusData)o;
                statusIdentifiers = statusData.getIdList();
                statusAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, statusData.getNameList());
                statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                status.setAdapter(statusAdapter);
                sync++;
                allowToSet();
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };

        RedMineApplication.redMineApi.getTrackers(trackerCallback);
        RedMineApplication.redMineApi.getPriorities(priorityCallback);
        RedMineApplication.redMineApi.getStatuses(statusCallback);
        */
    }

    protected void allowToSet() {
        if(sync == 4){
            setData();
        }
    }

    protected int findSelected(int id,ArrayList<Integer> list){
        for(int i=0; i < list.size(); i++){
            if(list.get(i).equals(id)) return  i;
        }
        return -1;
    }

}
