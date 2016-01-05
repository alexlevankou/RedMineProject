package by.alexlevankou.redmineproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.alexlevankou.redmineproject.FragmentLifecycle;
import by.alexlevankou.redmineproject.R;

public class HistoryFragment extends AbstractFragment implements FragmentLifecycle {

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment getInstance(Context context) {
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        fragment.setTitle(context.getString(R.string.tab_activity));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history,null);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        //initData();
      //  setData();
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }

}
