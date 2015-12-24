package by.alexlevankou.redmineproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class ListAdapter<T> extends BaseAdapter{

    protected Context context;
    protected LayoutInflater lInflater;
    protected ArrayList<T> list = new ArrayList<>();
    protected T obj;

    public ListAdapter(Context context, ArrayList<T> arrayList) {

        this.context = context;
        list = arrayList;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return list.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public abstract long getItemId(int position);
    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
