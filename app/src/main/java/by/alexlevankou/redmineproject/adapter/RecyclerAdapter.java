package by.alexlevankou.redmineproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

import by.alexlevankou.redmineproject.activity.PropertyActivity;
import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.fragment.IssueListFragment;
import by.alexlevankou.redmineproject.model.IssueData;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<IssueData.Issues> list;
    private ArrayList<IssueData.Issues> defaultList;
    private Context context;
    private static SharedPreferences pref;


    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        private TableLayout item;
        private TextView number;
        private TextView tracker;
        private TextView status;
        private TextView priority;
        private TextView subject;
        private TextView project;
        private TextView start_date;
        private TextView description;

        private MyListListener mListener;


        public ViewHolder(View v, MyListListener listener) {
            super(v);
            mListener = listener;
            item = (TableLayout) v.findViewById(R.id.list_item);
            number = (TextView) v.findViewById(R.id.number);
            status =  (TextView) v.findViewById(R.id.status);
            tracker = (TextView) v.findViewById(R.id.tracker);
            priority = (TextView) v.findViewById(R.id.priority);
            subject = (TextView) v.findViewById(R.id.subject);
            project = (TextView) v.findViewById(R.id.project);
            description = (TextView) v.findViewById(R.id.description);
            start_date = (TextView) v.findViewById(R.id.start_date);

            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            number = (TextView) v.findViewById(R.id.number);
            Long id = Long.parseLong(number.getText().toString());
            mListener.onChoose(id);
        }

        private void checkVisibility(TextView textView, Boolean boo){
            if(boo){
                textView.setVisibility(View.VISIBLE);
            }else{
                textView.setVisibility(View.GONE);
            }
        }

        public interface MyListListener {
            void onChoose(Long id);
        }

    }

    // Конструктор
    public RecyclerAdapter(Context context) {
        list = new ArrayList<IssueData.Issues>();
        defaultList = new ArrayList<IssueData.Issues>();
        this.context = context;
        PreferenceManager.setDefaultValues(context, R.xml.preferences, false);
        pref = PreferenceManager.getDefaultSharedPreferences(context);

    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        ViewHolder vh = new ViewHolder(v, new RecyclerAdapter.ViewHolder.MyListListener() {

            public void onChoose(Long id) {
                Intent intent = new Intent(context, PropertyActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            };
        });
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String id = String.valueOf(list.get(position).id);
        holder.number.setText(id);
        holder.status.setText(list.get(position).status.name);
        holder.priority.setText(list.get(position).priority.name);
        holder.tracker.setText(list.get(position).tracker.name);
        holder.subject.setText(list.get(position).subject);
        holder.project.setText(list.get(position).project.name);
        holder.description.setText(list.get(position).description);
        holder.start_date.setText(list.get(position).start_date);

        holder.checkVisibility(holder.status, pref.getBoolean("status_chb", true));
        holder.checkVisibility(holder.priority, pref.getBoolean("priority_chb", true));
        holder.checkVisibility(holder.tracker, pref.getBoolean("tracker_chb", true));
        holder.checkVisibility(holder.project, pref.getBoolean("project_chb",true));
        holder.checkVisibility(holder.subject, pref.getBoolean("subject_chb", false));
        holder.checkVisibility(holder.description, pref.getBoolean("description_chb", false));
        holder.checkVisibility(holder.start_date, pref.getBoolean("start_date_chb", false));
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(ArrayList<IssueData.Issues> issues){
        defaultList = issues;
        filter();
        notifyDataSetChanged();
    }

    private void filter(){
        list.clear();
        for(IssueData.Issues item: defaultList){
            if(
                IssueListFragment.prefPriority.get(item.getPriorityId())&&
                IssueListFragment.prefTracker.get(item.getTrackerId())&&
                IssueListFragment.prefStatus.get(item.getStatusId())
            ){
                list.add(item);
            }
        }
        notifyDataSetChanged();
    }
}
