package by.alexlevankou.redmineproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import by.alexlevankou.redmineproject.model.IssueData;
import by.alexlevankou.redmineproject.activity.PropertyActivity;
import by.alexlevankou.redmineproject.R;

public class IssueEditFragment extends AbstractIssueFragment {

    private IssueData.Issues issue = PropertyActivity.issue;


    public IssueEditFragment() {
        // Required empty public constructor
    }

    protected void setData(){
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
    }
}
