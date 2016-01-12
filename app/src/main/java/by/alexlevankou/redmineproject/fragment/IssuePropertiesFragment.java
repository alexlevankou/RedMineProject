package by.alexlevankou.redmineproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import by.alexlevankou.redmineproject.model.IssueData;
import by.alexlevankou.redmineproject.activity.PropertyActivity;
import by.alexlevankou.redmineproject.R;

public class IssuePropertiesFragment extends Fragment {

    private IssueData.Issues issue = PropertyActivity.issue;

    public IssuePropertiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_issue_properties,null);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    private void initData(){
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView tracker = (TextView) getActivity().findViewById(R.id.tracker_text);
        TextView subject = (TextView) getActivity().findViewById(R.id.subject_text);
        TextView status = (TextView) getActivity().findViewById(R.id.status_text);
        TextView priority = (TextView) getActivity().findViewById(R.id.priority_text);
        TextView author = (TextView) getActivity().findViewById(R.id.author_text);
        TextView assignee = (TextView) getActivity().findViewById(R.id.assignee_text);
        TextView start_date = (TextView) getActivity().findViewById(R.id.start_date_text);
        TextView update_date = (TextView) getActivity().findViewById(R.id.update_date_text);
        ProgressBar progressbar = (ProgressBar) getActivity().findViewById(R.id.done_ratio_progress);
        TextView progress = (TextView) getActivity().findViewById(R.id.done_ratio_text);
        TextView description = (TextView) getActivity().findViewById(R.id.description_text);

        toolbar.setTitle(issue.project.name);
        String track = issue.tracker.name + " #" + String.valueOf(issue.id);
        tracker.setText(track);
        subject.setText(issue.subject);
        String info = "Added by "+issue.author.name;
        author.setText(info);
        status.setText(issue.status.name);
        priority.setText(issue.priority.name);
        if(issue.assigned_to != null) {
            assignee.setText(issue.assigned_to.name);
        }
        start_date.setText(issue.start_date);
        update_date.setText(issue.updated_on);
        description.setText(issue.description);
        progress.setText(issue.done_ratio + "%");
        progressbar.setProgress(issue.done_ratio);
    }
}
