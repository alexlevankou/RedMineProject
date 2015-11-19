package by.alexlevankou.redmineproject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.HEAD;
import retrofit.http.Path;
import retrofit.http.Query;

public interface RedMineApi {

    @HEAD("/issues.json")
    void login(
            @Query("assigned_to_id") String user_id,
            Callback<IssueData> callback
    );

    @GET("/issues.json")
    void getIssues(
            @Query("assigned_to_id") String user_id,
            Callback<IssueData> callback
    );

}
