package by.alexlevankou.redmineproject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.HEAD;
import retrofit.http.PUT;
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

    @GET("/projects.json")
    void getProjects(
            Callback<ProjectData> callback
    );


    @GET("/issues/{id}.json")
    void showIssue(
            @Path("id") String id,
            Callback<IssueData> callback
    );

    @PUT("/issues/{id}.json")
    void updateIssue(
            @Body IssueCreator creator,
            @Path("id") String id,
            Callback<IssueData> callback
    );





    @GET("/issues/{id}.json")
    IssueData.Issues synchroGet(@Path("id") String id);


}
