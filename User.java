public class User {

    public String name;
    public String username;
    public String password;
    public boolean teacherPermission;

    public User(String name, String username, String password, boolean teacherPermission) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.teacherPermission = teacherPermission;
    }

    public User(String username, String password, boolean teacherPermission) {
        this.username = username;
        this.password = password;
        this.teacherPermission = teacherPermission;
    }

    public User() {
        this.name = "";
        this.username = "";
        this.password = "";
        this.teacherPermission = false;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTeacherPermission() {
        return teacherPermission;
    }

    public void setTeacherPermission(boolean teacherPermission) {
        this.teacherPermission = teacherPermission;
    }

    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append("Name: " + name + "\n");
        s.append("Username: " + username + "\n");
        s.append("Password: " + password + "\n");
        
        String isTeacher = teacherPermission ? "Yes" : "No";

        s.append("Is this a teacher: " + isTeacher + "\n");
        
        return s.toString();
    }
}
