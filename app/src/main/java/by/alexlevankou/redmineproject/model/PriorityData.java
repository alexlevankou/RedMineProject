package by.alexlevankou.redmineproject.model;

import java.util.ArrayList;
import java.util.HashMap;

public class PriorityData {

    private ArrayList<Priority> issue_priorities;

    public ArrayList<Priority> getTrackers() {
        return issue_priorities;
    }

    public ArrayList<String> getNameList(){
        ArrayList<String> names = new ArrayList<>();
        for(Priority priority: issue_priorities){
            names.add(priority.getName());
        }
        return names;
    }

    public ArrayList<Integer> getIdList(){
        ArrayList<Integer> ids = new ArrayList<>();
        for(Priority priority: issue_priorities){
            ids.add(priority.getId());
        }
        return ids;
    }

    public HashMap<Integer,String> getMappedData(){
        HashMap<Integer,String> map = new HashMap<>();
        for(Priority priority: issue_priorities){
            map.put(priority.getId(),priority.getName());
        }
        return map;
    }

    public class Priority{

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
