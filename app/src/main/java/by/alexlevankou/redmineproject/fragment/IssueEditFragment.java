package by.alexlevankou.redmineproject.fragment;

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

public class IssueEditFragment extends Fragment {

    private View view;
    private Toolbar toolbar;
    public AppCompatSpinner tracker;
    public EditText subject;
    public EditText description;
    public AppCompatSpinner status;
    public AppCompatSpinner priority;
    public AppCompatSpinner assignee;
    public AppCompatSpinner done_ratio;
    public  TextView start_date;
    private ImageButton date_picker;

    private final static int START_DATE = 1;
    private final static int DUE_DATE = 2;


    private IssueData.Issues issue = PropertyActivity.issue;

    ArrayAdapter<?> assignee_adapter;


    public IssueEditFragment() {
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
        setData();
    }


    public void showDatePickerDialog(View v){

        if(v.equals(date_picker)) {
            DialogFragment fragment = new DatePickerFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("date",START_DATE);
            fragment.setArguments(bundle);
            fragment.show(getActivity().getSupportFragmentManager(), "datePicker");
        }
    }

    private void initData() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        tracker  = (AppCompatSpinner) getActivity().findViewById(R.id.tracker_spinner);

        subject = (EditText) getActivity().findViewById(R.id.subject_text);
        description  = (EditText) getActivity().findViewById(R.id.description_text);
        status  = (AppCompatSpinner) getActivity().findViewById(R.id.status_spinner);
        priority  = (AppCompatSpinner) getActivity().findViewById(R.id.priority_spinner);
        assignee  = (AppCompatSpinner) getActivity().findViewById(R.id.assignee_spinner);
        done_ratio = (AppCompatSpinner) getActivity().findViewById(R.id.done_ratio_spinner);
        start_date  = (TextView) getActivity().findViewById(R.id.start_date_text);
        date_picker = (ImageButton) getActivity().findViewById(R.id.date_picker);
    }

    private void setData() {
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

    private int findSelected(String s,int id){
        String[] array = getResources().getStringArray(id);
        for(int i=0; i < array.length; i++){
            if(s.equals(array[i])) return  i;
        }
        return -1;
    }
}
