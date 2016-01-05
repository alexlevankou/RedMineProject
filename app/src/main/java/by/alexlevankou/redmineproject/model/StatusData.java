package by.alexlevankou.redmineproject.model;

import java.util.ArrayList;
import java.util.HashMap;

public class StatusData {

    private ArrayList<Status> issue_statuses;

    public ArrayList<Status> getStatuses() {
        return issue_statuses;
    }

    public ArrayList<String> getNameList(){
        ArrayList<String> names = new ArrayList<>();
        for(Status status: issue_statuses){
            names.add(status.getName());
        }
        return names;
    }

    public ArrayList<Integer> getIdList(){
        ArrayList<Integer> ids = new ArrayList<>();
        for(Status status: issue_statuses){
            ids.add(status.getId());
        }
        return ids;
    }

    public HashMap<Integer,String> getMappedData(){
        HashMap<Integer,String> map = new HashMap<>();
        for(Status status: issue_statuses){
            map.put(status.getId(), status.getName());
        }
        return map;
    }

    public class Status{

        private int id;
        private String name;

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
    }
}
