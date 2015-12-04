package by.alexlevankou.redmineproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IssueData{

    public Issues issue;
    public ArrayList<Issues> issues;
    public int total_count;
    public int offset;
    public int limit;

    public IssueData(){
        issue = new Issues();
    }

    public IssueData(int total_count,int offset,int limit){
        this.total_count = total_count;
        this.offset = offset;
        this.limit = limit;
        issue = new Issues();
    }

    public void setSubject(String s){
        issue.subject = s;
    }

    public void setDescription(String s){
        issue.description = s;
    }

    public void setStartDate(String s){

        issue.start_date = s;
    }


    public String getStatusName(){
        return issue.status.name;
    }
    public int getPriorityId(){
        return issue.priority.id;
    }

    public String getSubject(){
        return issue.subject;
    }

    public String getDescription(){
        return issue.description;
    }

    public String getStartDate(){
        return issue.start_date;
    }


    public class Issues{

        public long id;

        public Basics tracker; //in_list
        public Basics status; //in_list
        public Basics priority; //in_list
        public String subject; //in_list
        public String description;
        public String start_date;
        public int done_ratio;
        public String created_on;
        public String updated_on;

        public Basics author;
        public Basics assigned_to;

        public Basics project; //in_list


        public long getNumber(){
            return id;
        }
        public String getStatusName(){
            return status.name;
        }
        public int getStatusId(){
            return status.id;

        }

        public String getTrackerName(){
            return tracker.name;
        }
        public int getTrackerId(){
            return tracker.id;
        }

        public int getPriorityId(){
            return priority.id;
        }

        public String getSubject(){
            return subject;
        }

        public String getProjectName(){
            return project.name;
        }

        public String getDescription(){
            return description;
        }

        public String getStartDate(){
            return start_date;
        }


        //  public int status_id;
       // public int priority_id;
    //    public int tracker_id;

        public Issues(){}

        public class Basics{
            public int id;
            public String name;
        }
    }
}
