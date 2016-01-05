package by.alexlevankou.redmineproject.model;

import java.util.ArrayList;

public class ProjectMembership {

    private ArrayList<Member> memberships;

    public ArrayList<Member> getMembers() {
        return memberships;
    }

    public ArrayList<String> getNameList(){
        ArrayList<String> names = new ArrayList<>();
        for(Member member: memberships){
            names.add(member.getName());
        }
        return names;
    }

    public ArrayList<Integer> getIdList(){
        ArrayList<Integer> ids = new ArrayList<>();
        for(Member member: memberships){
            ids.add(member.getUserId());
        }
        return ids;
    }

    public class Member{

        private Basics user;
        private ArrayList<Basics> roles;

        public Member(){
            user = new Basics();
            roles = new ArrayList<>();
        }

        public Basics getUser() {
            return user;
        }
        public ArrayList<Basics> getRoles() {
            return roles;
        }
        public int getUserId(){
            return user.getId();
        }

        public String getName(){
            return user.getName();
        }

        public String getUsername(){
            return user.getName()+": ";
        }

        public String getRole(int i){
            return roles.get(i).getName();
        }
        private int getNumberOfRoles(){
            return roles.size();
        }

        public String getUserRoles(){
            StringBuilder str = new StringBuilder();
            int last = getNumberOfRoles();
            for (int i = 0; i < last; i++){
                str.append(getRole(i));
                if(i < (last-1)){
                    str.append(", ");
                }
            }
            return str.toString();
        }


        public class Basics{

            private int id;
            private String name;

            public int getId() {
                return id;
            }
            public String getName() {
                return name;
            }

        }
    }
}
