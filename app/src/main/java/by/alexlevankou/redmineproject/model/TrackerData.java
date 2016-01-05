package by.alexlevankou.redmineproject.model;

import java.util.ArrayList;
import java.util.HashMap;

public class TrackerData {

    private ArrayList<Tracker> trackers;

    public ArrayList<Tracker> getTrackers() {
        return trackers;
    }

    public ArrayList<String> getNameList(){
        ArrayList<String> names = new ArrayList<>();
        for(Tracker tracker: trackers){
            names.add(tracker.getName());
        }
        return names;
    }

    public ArrayList<Integer> getIdList(){
        ArrayList<Integer> ids = new ArrayList<>();
        for(Tracker tracker: trackers){
            ids.add(tracker.getId());
        }
        return ids;
    }

    public HashMap<Integer,String> getMappedData(){
        HashMap<Integer,String> map = new HashMap<>();
        for(Tracker tracker: trackers){
            map.put(tracker.getId(), tracker.getName());
        }
        return map;
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
