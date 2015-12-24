package by.alexlevankou.redmineproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.model.ProjectMembership;

public class MembershipAdapter extends ListAdapter{

    public MembershipAdapter(Context context, ArrayList<ProjectMembership.Member> arrayList) {
        super(context,arrayList);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        obj = list.get(position);
        ProjectMembership.Member member = (ProjectMembership.Member)obj;
        return member.getUserId();
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
        ProjectMembership.Member member = (ProjectMembership.Member)obj;
        userText.setText(member.getUsername());
        roleText.setText(member.getUserRoles());

        return view;
    }
}
