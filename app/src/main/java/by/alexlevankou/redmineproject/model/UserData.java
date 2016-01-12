package by.alexlevankou.redmineproject.model;

public class UserData {

    private User user;

    public UserData(){
        user = new User();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser(){
        return user;
    }


    public class User{

        private int id;
        private String login;
        private String firstname;
        private String lastname;

        public User(){}

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getFirstname() {
            if(firstname != null) {
                return firstname;
            }else{
                return "";
            }
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            if(lastname != null){
                return lastname;
            }else{
                return "";
            }
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getFullName(){
            return getFirstname()+" "+getLastname();
        }
    }
}
