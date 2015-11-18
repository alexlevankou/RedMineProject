package by.alexlevankou.redmineproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TaskListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.RedTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
    }
}
