package by.alexlevankou.redmineproject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface RedMineApi {
    @GET("/issues.json")
    void getIssues(
            @Query("assigned_to_id") String user_id,
            Callback<IssueData> callback
    );

}
