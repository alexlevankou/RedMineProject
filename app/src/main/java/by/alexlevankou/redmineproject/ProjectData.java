package by.alexlevankou.redmineproject;

import java.util.ArrayList;

public class ProjectData {

    public Project project;
    public ArrayList<Project> projects;


    public ProjectData(){
        project = new Project();
    }


    public void setId(int i){
       project.id = i;
    }
    public void setName(String s){
        project.name = s;
    }
    public void setIdentifier(String s){
        project.identifier = s;
    }
    public void setDescription(String s){
        project.description = s;
    }
    public void setPublic(boolean b){
        project.is_public = b;
    }


    public int getId(){
        return project.id;
    }
    public String getName(){
        return project.name;
    }
    public String getIdentifier(){
        return project.identifier;
    }
    public String getDescription(){
        return project.description;
    }
    public boolean getPublic(){
        return project.is_public;
    }

    public class Project{
        private int id;
        private String name;
        private String identifier;
        private String description;
        private boolean is_public;
    }
}
