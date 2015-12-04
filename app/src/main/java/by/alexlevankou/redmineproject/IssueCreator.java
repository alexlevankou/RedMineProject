package by.alexlevankou.redmineproject;

public class IssueCreator {

    public IssueCreator(){
        issue = new Issue();
    }

    public Issue issue;

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

        public void setStatus(String s) {
            issue.status_id = s;
        }


    public class Issue {

        public String subject;
        public String description;
        public String start_date;
        public String status_id;
        public String priority_id;
        public String tracker_id;
    }
}
