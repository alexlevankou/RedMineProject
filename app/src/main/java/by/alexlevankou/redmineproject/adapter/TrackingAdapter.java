package by.alexlevankou.redmineproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

import by.alexlevankou.redmineproject.Constants;
import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.activity.ProjectActivity;
import by.alexlevankou.redmineproject.model.IssueData;
import by.alexlevankou.redmineproject.model.TrackerData;

public class TrackingAdapter extends ListAdapter{

    private IssueData issueData;

    public TrackingAdapter(Context context, ArrayList<TrackerData.Tracker> arrayList, IssueData data) {
        super(context,arrayList);
        issueData = data;
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        obj = list.get(position);
        TrackerData.Tracker tracker = (TrackerData.Tracker)obj;
        return tracker.getId();
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.text_item, parent, false);
        }
        TextView userText = (TextView)view.findViewById(R.id.user_text);
        TextView roleText = (TextView)view.findViewById(R.id.role_text);

        obj = list.get(position);
        TrackerData.Tracker tracker = (TrackerData.Tracker)obj;
        userText.setText(tracker.getTitle());
        roleText.setText(tracker.getState(issueData));

        return view;
    }
}
