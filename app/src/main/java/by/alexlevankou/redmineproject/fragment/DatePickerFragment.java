package by.alexlevankou.redmineproject.fragment;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.widget.TextView;

import by.alexlevankou.redmineproject.model.IssueData;
import by.alexlevankou.redmineproject.activity.PropertyActivity;
import by.alexlevankou.redmineproject.R;


public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private IssueData.Issues issue = PropertyActivity.issue;
    private int date_type;
    private TextView start_date;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        date_type = bundle.getInt("date");
        start_date = (TextView) getActivity().findViewById(R.id.start_date_text);


        String date_string;
        if(date_type == 1) {

            date_string = start_date.getText().toString();
        }else{
            date_string = issue.updated_on; // where is due date????????
        }
        return parseDateString(date_string);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        if(date_type == 1) {
            month++;
            String date_string = year+"-"+month+"-"+day;
            start_date.setText(date_string);
        }
    }

    private DatePickerDialog parseDateString(String date){ // пока только для стартовой даты

        String[] date_array;
        date_array = date.split("-");

        int year = Integer.valueOf(date_array[0]);
        int month = Integer.valueOf(date_array[1]);
        month--;
        int day = Integer.valueOf(date_array[2]);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

}
