package by.alexlevankou.redmineproject.fragment;


import android.content.Context;
import android.os.Bundle;

import by.alexlevankou.redmineproject.R;


public class IssueCreateFragment extends AbstractIssueFragment{

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

    protected void setData(){
        /*
        toolbar.setTitle(issue.project.name);

        subject.setText(issue.subject);
        description.setText(issue.description);
        start_date.setText(issue.start_date);

        int selected = findSelected(issue.tracker.name,R.array.tracker_array);
        tracker.setSelection(selected);
        selected = findSelected(issue.status.name,R.array.status_array);
        status.setSelection(selected);
        selected = findSelected(issue.priority.name,R.array.priority_array);
        priority.setSelection(selected);
        done_ratio.setSelection(issue.done_ratio/10-1);
        */
    }
}
