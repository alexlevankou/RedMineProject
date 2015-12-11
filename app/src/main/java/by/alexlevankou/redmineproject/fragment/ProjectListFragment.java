package by.alexlevankou.redmineproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.alexlevankou.redmineproject.R;


public class ProjectListFragment extends Fragment {

    public ProjectListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_list,null);
        initToolbar();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

      //  getInfoFromApi();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.projects);
    }

    private void getInfoFromApi() {
    }

}
