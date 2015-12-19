package by.alexlevankou.redmineproject.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import by.alexlevankou.redmineproject.R;
import by.alexlevankou.redmineproject.activity.ProjectActivity;
import by.alexlevankou.redmineproject.model.ProjectData;

public class RecyclerProjectAdapter extends RecyclerView.Adapter<RecyclerProjectAdapter.ViewHolder> {

    private ArrayList<ProjectData.Project> list;
    private Context context;


    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        private RelativeLayout item;
        private int projectId;
        private TextView projectName;
        private TextView projectDescription;
        private MyListListener mListener;


        public ViewHolder(View v, MyListListener listener) {
            super(v);
            mListener = listener;
            item = (RelativeLayout) v.findViewById(R.id.list_item);
            projectName = (TextView) v.findViewById(R.id.project_name);
            projectDescription = (TextView) v.findViewById(R.id.project_description);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = this.projectId;
            mListener.onChoose(id);
        }

        private void checkVisibility(TextView textView){
            if(textView.getText().equals("")){
                textView.setVisibility(View.GONE);
            }else{
                textView.setVisibility(View.VISIBLE);
            }
        }

        public interface MyListListener {
            void onChoose(int id);
        }
    }

    // Конструктор
    public RecyclerProjectAdapter(Context context) {
        list = new ArrayList<ProjectData.Project>();
        this.context = context;
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public RecyclerProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_project_item, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        ViewHolder vh = new ViewHolder(v, new RecyclerProjectAdapter.ViewHolder.MyListListener() {

            public void onChoose(int id) {
                //Log.e("listener","Project id: "+id);
                Intent intent = new Intent(context, ProjectActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            };
        });
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.projectId = list.get(position).getId();
        holder.projectName.setText(list.get(position).getName());
        holder.projectDescription.setText(list.get(position).getDescription());

        holder.checkVisibility(holder.projectDescription);
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(ArrayList<ProjectData.Project> projects){
        list = projects;
        notifyDataSetChanged();
    }
}
