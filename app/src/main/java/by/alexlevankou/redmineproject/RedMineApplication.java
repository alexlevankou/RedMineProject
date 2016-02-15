package by.alexlevankou.redmineproject;

import android.app.Application;

import by.alexlevankou.redmineproject.model.PriorityData;
import by.alexlevankou.redmineproject.model.StatusData;
import by.alexlevankou.redmineproject.model.TrackerData;
import by.alexlevankou.redmineproject.model.UserData;

public class RedMineApplication extends Application {

    public static RedMineApi redMineApi;
    public static final int EMPTY_INTENT = -1;

    private static UserData.User currentUser;
    private static StatusData statusData;
    private static TrackerData trackerData;
    private static PriorityData priorityData;

    public static UserData.User getCurrentUser() {
        return currentUser;
    }

    public static StatusData getStatusData() {
        return statusData;
    }

    public static TrackerData getTrackerData() {
        return trackerData;
    }

    public static PriorityData getPriorityData() {
        return priorityData;
    }

    public static void setCurrentUser(UserData.User currentUser) {
        RedMineApplication.currentUser = currentUser;
    }

    public static void setStatusData(StatusData statusData) {
        RedMineApplication.statusData = statusData;
    }

    public static void setPriorityData(PriorityData priorityData) {
        RedMineApplication.priorityData = priorityData;
    }

    public static void setTrackerData(TrackerData trackerData) {
        RedMineApplication.trackerData = trackerData;
    }
}
