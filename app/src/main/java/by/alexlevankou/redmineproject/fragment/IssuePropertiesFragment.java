package by.alexlevankou.redmineproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import by.alexlevankou.redmineproject.IssueData;
import by.alexlevankou.redmineproject.PropertyActivity;
import by.alexlevankou.redmineproject.R;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class IssuePropertiesFragment extends Fragment {

    private View view;
    private Toolbar toolbar;
    private TextView tracker;
    private TextView subject;
    private TextView status;
    private  TextView priority;
    private  TextView author;
    private  TextView assignee;
    private  TextView start_date;
    private  TextView update_date;
    private  TextView description;
    private ProgressBar progressbar;

    private IssueData.Issues issue = PropertyActivity.issue;


    public IssuePropertiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_issue_properties,null);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    private void initData(){
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        tracker  = (TextView) getActivity().findViewById(R.id.tracker_text);
        subject = (TextView) getActivity().findViewById(R.id.subject_text);
        status  = (TextView) getActivity().findViewById(R.id.status_text);
        priority  = (TextView) getActivity().findViewById(R.id.priority_text);
        author  = (TextView) getActivity().findViewById(R.id.author_text);
        assignee  = (TextView) getActivity().findViewById(R.id.assignee_text);
        start_date  = (TextView) getActivity().findViewById(R.id.start_date_text);
        update_date  = (TextView) getActivity().findViewById(R.id.update_date_text);
        progressbar = (ProgressBar) getActivity().findViewById(R.id.done_ratio_progress);
        description  = (TextView) getActivity().findViewById(R.id.description_text);


        toolbar.setTitle(issue.project.name);
        String track = issue.tracker.name + " #" + String.valueOf(issue.id);
        tracker.setText(track);
        subject.setText(issue.subject);
        status.setText(issue.status.name);
        priority.setText(issue.priority.name);
        author.setText(issue.author.name);
        assignee.setText(issue.assigned_to.name);
        start_date.setText(issue.start_date);
        update_date.setText(issue.updated_on);
        description.setText(issue.description);

        progressbar.setProgress(issue.done_ratio);
    }



}
