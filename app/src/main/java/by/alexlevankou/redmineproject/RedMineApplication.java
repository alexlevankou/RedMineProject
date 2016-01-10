package by.alexlevankou.redmineproject;

import android.app.Application;

import by.alexlevankou.redmineproject.model.StatusData;

public class RedMineApplication extends Application {
    public static RedMineApi redMineApi;
    public static final int EMPTY_INTENT = -1;

    private StatusData.Status statusData;
    private StatusData.Status trackerData;
    private StatusData.Status priorityData;

    public StatusData.Status getStatusData() {
        return statusData;
    }

    public StatusData.Status getTrackerData() {
        return trackerData;
    }

    public StatusData.Status getPriorityData() {
        return priorityData;
    }


    public void setStatusData(StatusData.Status statusData) {
        this.statusData = statusData;
    }

    public void setTrackerData(StatusData.Status trackerData) {
        this.trackerData = trackerData;
    }

    public void setPriorityData(StatusData.Status priorityData) {
        this.priorityData = priorityData;
    }


}
