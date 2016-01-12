package by.alexlevankou.redmineproject.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.RedMineApplication;
import by.alexlevankou.redmineproject.activity.PropertyActivity;
import by.alexlevankou.redmineproject.model.IssueCreator;
import by.alexlevankou.redmineproject.model.IssueData;
import by.alexlevankou.redmineproject.model.ProjectMembership;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class IssueEditFragment extends AbstractIssueFragment {

    private IssueData.Issues issue = PropertyActivity.issue;

    public AppCompatSpinner done_ratio;
    public TextView start_date;
    protected ImageButton date_picker;

    public IssueEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        setListener();
    }

    protected void setListener() {
        View.OnClickListener datePickerListener = new View.OnClickListener() {
            public void onClick(View v) {
                if(v.equals(date_picker)) {
                    DialogFragment fragment = new DatePickerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("date",START_DATE);
                    fragment.setArguments(bundle);
                    fragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                }
            }
        };
        date_picker.setOnClickListener(datePickerListener);
    }

    protected void initData() {
        super.initData();
        done_ratio = (AppCompatSpinner) getActivity().findViewById(R.id.done_ratio_spinner);
        start_date  = (TextView) getActivity().findViewById(R.id.start_date_text);
        date_picker = (ImageButton) getActivity().findViewById(R.id.date_picker);
    }

    protected void getSpinnerData(){
        super.getSpinnerData();

        Callback assigneeCallback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                ProjectMembership membership = (ProjectMembership)o;
                assigneeIdentifiers = membership.getIdList();
                assigneeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, membership.getNameList());
                assigneeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                assignee.setAdapter(assigneeAdapter);
                setData();
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                retrofitError.printStackTrace();
            }
        };
        String query = String.valueOf(issue.getProjectId());
        RedMineApplication.redMineApi.getProjectMembership(query, assigneeCallback);

    }

    protected void setData(){
        toolbar.setTitle(issue.getProjectName());
        subject.setText(issue.getSubject());
        description.setText(issue.getDescription());
        start_date.setText(issue.getStartDate());

        int selected = findSelected(issue.getTrackerId(),trackerIdentifiers);
        tracker.setSelection(selected);
        selected = findSelected(issue.getStatusId(),statusIdentifiers);
        status.setSelection(selected);
        selected = findSelected(issue.getPriorityId(),priorityIdentifiers);
        priority.setSelection(selected);
        try {
            selected = findSelected(issue.getAssigneeId(), assigneeIdentifiers);
            assignee.setSelection(selected);
        }catch (NullPointerException e){
            assignee.setSelection(0);
        }
        done_ratio.setSelection(issue.done_ratio/10);
    }

    public void prepareIssue(IssueCreator iss) {
        iss.setSubject(subject.getText().toString());
        iss.setDescription(description.getText().toString());
        iss.setStartDate(start_date.getText().toString());

        String selectedVal;
        selectedVal = trackerIdentifiers.get(tracker.getSelectedItemPosition()).toString();
        iss.setTracker(selectedVal);
        selectedVal = statusIdentifiers.get(status.getSelectedItemPosition()).toString();
        iss.setStatus(selectedVal);
        selectedVal = priorityIdentifiers.get(priority.getSelectedItemPosition()).toString();
        iss.setPriority(selectedVal);
        selectedVal = assigneeIdentifiers.get(assignee.getSelectedItemPosition()).toString();
        iss.setAssignee(selectedVal);
        iss.setDoneRatio(done_ratio.getSelectedItemPosition()*10);
    }
}
