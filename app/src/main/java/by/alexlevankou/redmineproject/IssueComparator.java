package by.alexlevankou.redmineproject;

import java.util.Comparator;

import by.alexlevankou.redmineproject.model.IssueData;

public class IssueComparator implements Comparator<IssueData.Issues> {

    public static final int COMPARE_BY_NUMBER = R.id.number;
    public static final int COMPARE_BY_STATUS = R.id.status;
    public static final int COMPARE_BY_PRIORITY = R.id.priority;
    public static final int COMPARE_BY_TRACKER = R.id.tracker;
    public static final int COMPARE_BY_START_DATE = R.id.start_date;
    public static final int COMPARE_BY_SUBJECT = R.id.subject;
    public static final int COMPARE_BY_PROJECT = R.id.project;

    private int compare_mode = COMPARE_BY_PRIORITY;

    public IssueComparator() {}

    public IssueComparator(int compare_mode) {
        this.compare_mode = compare_mode;
    }

    @Override
    public int compare(IssueData.Issues o1, IssueData.Issues o2) {
        switch (compare_mode) {
            case COMPARE_BY_PRIORITY:
                return o1.getPriorityId() > o2.getPriorityId() ? 1 : (o1.getPriorityId() < o2.getPriorityId() ? -1 : 0);
            case COMPARE_BY_TRACKER:
                return o1.getTrackerName().compareTo(o2.getTrackerName());
            case COMPARE_BY_START_DATE:
                return o1.getStartDate().compareTo(o2.getStartDate());
            case COMPARE_BY_SUBJECT:
                return o1.getSubject().compareTo(o2.getSubject());
            case COMPARE_BY_PROJECT:
                return o1.getProjectName().compareTo(o2.getProjectName());
            case COMPARE_BY_STATUS:
                return o1.getStatusName().compareTo(o2.getStatusName());
            case COMPARE_BY_NUMBER:
                return o1.getNumber() > o2.getNumber() ? 1 : (o1.getNumber() < o2.getNumber() ? -1 : 0);
            default:
                return o1.getPriorityId() > o2.getPriorityId() ? 1 : (o1.getPriorityId() < o2.getPriorityId() ? -1 : 0);
        }
    }
}
