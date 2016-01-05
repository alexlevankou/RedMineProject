package by.alexlevankou.redmineproject.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import by.alexlevankou.redmineproject.FragmentLifecycle;
import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.activity.ProjectActivity;
import by.alexlevankou.redmineproject.model.ProjectMembership;
import by.alexlevankou.redmineproject.model.StatusData;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class IssueCreateFragment extends AbstractIssueFragment implements FragmentLifecycle {

    public IssueCreateFragment() {
        // Required empty public constructor
    }

    public static IssueCreateFragment getInstance(Context context) {
        Bundle args = new Bundle();
        IssueCreateFragment fragment = new IssueCreateFragment();
        fragment.setArguments(args);
        fragment.setTitle(context.getString(R.string.tab_new_issue));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_issue_create,null);
        return view;
    }

    @Override
    public void onPauseFragment() {
        FloatingActionButton add_fab = (FloatingActionButton)getActivity().findViewById(R.id.add_fab);
        add_fab.hide();
    }

    @Override
    public void onResumeFragment() {
        FloatingActionButton add_fab = (FloatingActionButton)getActivity().findViewById(R.id.add_fab);
        add_fab.show();
    }

    public String getSelectedTrackerId(){
        return String.valueOf(trackerIdentifiers.get(tracker.getSelectedItemPosition()));
    }
    public String getSelectedPriorityId(){
        return String.valueOf(priorityIdentifiers.get(tracker.getSelectedItemPosition()));
    }
    public String getSelectedStatusId(){
        return String.valueOf(statusIdentifiers.get(tracker.getSelectedItemPosition()));
    }

    protected void getSpinnerData(){
        super.getSpinnerData();

        Callback assigneeCallback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                ProjectMembership membership = (ProjectMembership)o;
                assigneeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, membership.getNameList());
                assigneeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                assignee.setAdapter(assigneeAdapter);
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };
        String query = String.valueOf(ProjectActivity.id);
        RedMineApplication.redMineApi.getProjectMembership(query,assigneeCallback);

    }

    protected void setData(){    }
}
