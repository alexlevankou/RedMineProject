package by.alexlevankou.redmineproject;

import by.alexlevankou.redmineproject.model.IssueCreator;
import by.alexlevankou.redmineproject.model.IssueData;
import by.alexlevankou.redmineproject.model.PriorityData;
import by.alexlevankou.redmineproject.model.ProjectData;
import by.alexlevankou.redmineproject.model.ProjectMembership;
import by.alexlevankou.redmineproject.model.StatusData;
import by.alexlevankou.redmineproject.model.TrackerData;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.HEAD;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public interface RedMineApi {

    // issue

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

    @GET("/issues.json")
    void getProjectIssues(
            @Query("project_id") String project_id,
            Callback<IssueData> callback
    );



    @GET("/trackers.json")
    void getTrackers(
            Callback<TrackerData> callback
    );

    @GET("/enumerations/issue_priorities.json")
    void getPriorities(
            Callback<PriorityData> callback
    );

    @GET("/issue_statuses.json")
    void getStatuses(
            Callback<StatusData> callback
    );


    @GET("/issues/{id}.json")
    void showIssue(
            @Path("id") String id,
            Callback<IssueData> callback
    );

    // Creator or Data?
    @PUT("/issues/{id}.json")
    void updateIssue(
            @Body IssueCreator creator,
            @Path("id") String id,
            Callback<IssueData> callback
    );

    // Creator or Data?
    @POST("/issues.json")
    void createIssue(
            @Body IssueCreator creator,
            Callback<IssueData> callback
    );

    //project

    @GET("/projects.json")
    void getProjects(
            Callback<ProjectData> callback
    );


    @GET("/projects/{id}.json")
    void showProject(
            @Path("id") String id,
            Callback<ProjectData> callback
    );

    //membership
    @GET("/projects/{id}/memberships.json")
    void getProjectMembership(
            @Path("id") String id,
            Callback<ProjectMembership> callback
    );
}
