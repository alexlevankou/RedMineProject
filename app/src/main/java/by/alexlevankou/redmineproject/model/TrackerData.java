package by.alexlevankou.redmineproject.model;

import java.util.ArrayList;

public class TrackerData {

    private ArrayList<Tracker> trackers;

    public ArrayList<Tracker> getTrackers() {
        return trackers;
    }


    public class Tracker{

        private int id;
        private String name;

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getTitle() {
            return name+": ";
        }

        public String getState(IssueData issueData){

            ArrayList<IssueData.Issues> list = issueData.issues;
            int total=0, opened=0;
            for(IssueData.Issues elem : list){
                if(elem.getTrackerId()== this.getId()){
                    total++;
                    if (!elem.getStatusName().equals("Closed")){
                        opened++;
                    }
                }
            }
            return String.format("%s opened / %s", opened, total);
        }
    }
}
