package by.alexlevankou.redmineproject;

import by.alexlevankou.redmineproject.model.IssueCreator;
import by.alexlevankou.redmineproject.model.IssueData;
import by.alexlevankou.redmineproject.model.PriorityData;
import by.alexlevankou.redmineproject.model.ProjectData;
import by.alexlevankou.redmineproject.model.ProjectMembership;
import by.alexlevankou.redmineproject.model.StatusData;
import by.alexlevankou.redmineproject.model.TrackerData;
import by.alexlevankou.redmineproject.model.UserData;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.HEAD;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface RedMineApi {

    //RxJava
    @GET("/users/current.json")
    Observable<UserData> getUserRx();

    @GET("/trackers.json")
    Observable<TrackerData> getTrackerRx();

    @GET("/enumerations/issue_priorities.json")
    Observable<PriorityData> getPriorityRx();

    @GET("/issue_statuses.json")
    Observable<StatusData> getStatusRx();

    @GET("/issues.json?status_id=*")
    Observable<IssueData> getIssues( @Query("assigned_to_id") String user_id );

    @GET("/issues.json?status_id=*")
    Observable<IssueData> getProjectIssues( @Query("project_id") String project_id);

////
    @GET("/issues/{id}.json")
    Observable<IssueData> showIssue( @Path("id") String id );

    @PUT("/issues/{id}.json")
    Observable<IssueData> updateIssue( @Body IssueCreator creator, @Path("id") String id );

    @POST("/issues.json")
    Observable<IssueData> createIssue( @Body IssueCreator creator );

    @GET("/projects.json?limit=200")
    Observable<ProjectData> getProjects();

    @GET("/projects/{id}.json")
    Observable<ProjectData> showProject( @Path("id") String id );

    @GET("/projects/{id}/memberships.json")
    Observable<ProjectMembership> getProjectMembership( @Path("id") String id );

    ///////////////////////////////////////////////////////////////////////

    @POST("/issues.json")
    void createIssue(
            @Body IssueCreator creator,
            Callback<IssueData> callback
    );

    //membership
    @GET("/projects/{id}/memberships.json")
    void getProjectMembership(
            @Path("id") String id,
            Callback<ProjectMembership> callback
    );
}
