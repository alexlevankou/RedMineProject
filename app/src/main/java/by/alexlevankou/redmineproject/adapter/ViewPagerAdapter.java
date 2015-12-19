package by.alexlevankou.redmineproject.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import by.alexlevankou.redmineproject.fragment.AbstractTabFragment;
import by.alexlevankou.redmineproject.fragment.IssueListFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        initTabsMap(context);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap(Context context) {
        tabs = new HashMap<>();
        tabs.put(0, IssueListFragment.getInstance(context));
        tabs.put(1, IssueListFragment.getInstance(context));
        tabs.put(2, IssueListFragment.getInstance(context));
        tabs.put(3, IssueListFragment.getInstance(context));
    }
}
