package by.alexlevankou.redmineproject.model;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public boolean is_public() {
            return is_public;
        }

        public void is_public(boolean is_public) {
            this.is_public = is_public;
        }

        public String getDescription() {
            if(description != null) {
                return description;
            }else{
                return "";
            }
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }
}
