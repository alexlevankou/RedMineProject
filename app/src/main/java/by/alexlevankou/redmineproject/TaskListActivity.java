package by.alexlevankou.redmineproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;


public class TaskListActivity extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeLayout;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.RedTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        initSwipeRefresher();

        ArrayList<String> myDataset = getDataSet();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // если мы уверены, что изменения в контенте не изменят размер layout-а RecyclerView
        // передаем параметр true - это увеличивает производительность
        mRecyclerView.setHasFixedSize(true);

        // используем linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // создаем адаптер
        mAdapter = new RecyclerAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onRefresh() {
        Log.d("my_tag", "refresh");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // stop refresh
                mSwipeLayout.setRefreshing(false);
            }
        }, 5000);
    }

    private void initSwipeRefresher() {
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent
        );
    }

    private ArrayList<String> getDataSet() {

        ArrayList<String> list = new ArrayList<String>(100);
        for (int i = 0; i < 100; i++) {
            list.add("item" + i);
        }
        return list;
    }

}
