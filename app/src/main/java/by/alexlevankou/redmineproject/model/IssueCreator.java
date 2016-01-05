package by.alexlevankou.redmineproject.model;

public class IssueCreator {

    public IssueCreator(){
        issue = new Issue();
    }

    public Issue issue;

    public void setProject(String s) {
        issue.project_id = s;
    }

    public void setSubject(String s) {
        issue.subject = s;
    }

    public void setDescription(String s) {
        issue.description = s;
    }

    public void setStartDate(String s) {
        issue.start_date = s;
    }

    public void setTracker(String s) {
        issue.tracker_id = s;
    }

    public void setPriority(String s) {
        issue.priority_id = s;
    }

    public void setAssignee(String s) {
        issue.assigned_to_id = s;
    }

    public void setStatus(String s) {
        issue.status_id = s;
    }

    public void setDoneRatio(int i){
        issue.done_ratio = i;
    }

    public class Issue {

        public String project_id;
        public String status_id;
        public String priority_id;
        public String tracker_id;
        public String assigned_to_id;
        public String subject;
        public String description;
        public String start_date;
        public int done_ratio;
    }
}
