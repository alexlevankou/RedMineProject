package by.alexlevankou.redmineproject;

import android.os.Parcelable;
import android.os.Parcel;
import java.util.List;
import java.util.Map;

public class IssueData implements Parcelable {

    public List<Issue> issue;
    public int total_count;
    public int offset;
    public int limit;

    public IssueData(int total_count,int offset,int limit){
        this.total_count = total_count;
        this.offset = offset;
        this.limit = limit;
    }

    public void setTotalCount(int total_count){
        this.total_count = total_count;
    }
    public int getTotalCount(){
        return total_count;
    }
    public void setOffset(int offset){
        this.offset = offset;
    }
    public int getOffset(){
        return offset;
    }
    public void setLimit(int limit){
        this.limit = limit;
    }
    public int getLimit(){
        return limit;
    }

    public class Issue{

        long issue_id;
        Map<Integer,String> project;
        Map<Integer,String> tracker;
        Map<Integer,String> author;
        String subject;

        Issue(){}
        Issue(long issue_id, Map<Integer,String> project, Map<Integer,String> tracker, Map<Integer,String> author, String subject){
            this.issue_id = issue_id;
            this.project = project;
            this.tracker = tracker;
            this.author = author;
            this.subject = subject;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       // dest.writeStringArray(new String[] { mName, mWhiskers, mPaws, mTail });
    }

    public static final Parcelable.Creator<IssueData> CREATOR = new Parcelable.Creator<IssueData>() {

        @Override
        public IssueData createFromParcel(Parcel source) {
          //  return new IssueData(source);
        return null;
        }

        @Override
        public IssueData[] newArray(int size) {
            return new IssueData[size];
        }
    };


}
