import java.io.*;
import java.net.Socket;
import java.util.*;

public class Manager {

    private ArrayList<Course> courses;
    private ArrayList<User> users;

    public Manager(ArrayList<Course> courses, ArrayList<User> users) {

        this.courses = courses;
        this.users = users;
    }

    public Manager() {
        this.courses = new ArrayList<Course>();
        this.users = new ArrayList<User>();
    }

    public ArrayList<Course> getCourses() {
        return this.courses;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public User getUser(int i) {
        return this.users.get(i);
    }

    public User getUser(String username, String password) {

        User u = null;

        for (int i = 0; i < users.size(); i++) {

            if (users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password)) {
                u = users.get(i);
                break;
            }
        }

        return u;
    }

    public Course getCourse(int i) {
        return this.courses.get(i);
    }

    public Course getCourse(String courseName) {

        Course c = null;
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equals(courseName)) {
                c = courses.get(i);
            }
        }

        return c;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public boolean isUser(String username, String password, boolean isTeacher) {

        boolean isU = false;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password) && users.get(i).teacherPermission == isTeacher) {
                isU = true;
            }
        }

        return isU;
    }

    public boolean addUser(User user) {

        boolean wasAdded = false;

        if (!containsUser(user)) {
            users.add(user);
            wasAdded = true;
        }

        return wasAdded;
    }

    public boolean containsUser(User user) {

        // Variable to determine if the user is in the list of users
        boolean doesContain = false;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                doesContain = true;
                break;
            }
        }

        return doesContain;
    }

    public boolean addCourse(Course course) {

        boolean wasAdded = false;

        if (!containsCourse(course)) {
            courses.add(course);
            wasAdded = true;
        }

        return wasAdded;
    }

    public boolean containsCourse(Course course) {

        // Variable to determine if the course is in the list of courses
        boolean doesContain = false;

        // Course names must be unique
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equals(course.getName())) {
                doesContain = true;
                break;
            }
        }

        return doesContain;
    }

    // Remove user
    public boolean removeUser(int i) {

        boolean didRemove = false;

        try {
            users.remove(i);
            didRemove = true;
        } catch (Exception e) {
            didRemove = false;
        }

        return didRemove;
    }

    public boolean removeUser(boolean isTeacher, String username, String password) {

        boolean didRemove = false;
        int index;
        if (isUser(username, password, isTeacher)) {
            index = users.indexOf(getUser(username, password));
            removeUser(index);
            didRemove = true;
        }

        return didRemove;
    }

    public boolean removeCourse(int i) {

        boolean didRemove = false;

        try {
            courses.remove(i);
            didRemove = true;
        } catch (Exception e) {
            didRemove = false;
        }

        return didRemove;
    }

    // Remove course

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append("Courses:\n");
        for (int i = 0; i < courses.size(); i++) {
            s.append(courses.get(i).toString());
        }

        s.append("\nUsers:\n");
        for (int i = 0; i < users.size(); i++) {
            s.append(users.get(i).toString());
        }

        return s.toString();
    }

    public void writeToFile() {

        try {
            Socket socket = new Socket("localhost", 4343);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());

            // print the data in manager to the file
            pw.println(this.toString());
            pw.flush();
            pw.close();
            socket.close();
        } catch (IOException e) {
            // System.out.println("Could not write to file");
        }
    }

    public void readFromFile() {

        Course c = null;
        User u = null;
        Quiz q = null;
        Question ques = null;

        try {
            Socket socket = new Socket("localhost", 4343);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println("Read");
            pw.flush();

            String line = br.readLine();
            while (line != null) {

                // If the line is empty, continue to the next line
                if (line.isBlank()) {
                    line = br.readLine();
                    continue;
                }

                // We have reached a new course
                if (line.contains("Course: ")) {

                    // Create a new course with the name in the file
                    c = new Course(line.substring(line.indexOf(":") + 2));
                    line = br.readLine();

                    // While we are still in this course, get all the data from it
                    while (!line.contains("Users:") && !line.contains("Course:") && line != null) {

                        // If the line is empty, continue to the next line
                        if (line.isBlank()) {
                            line = br.readLine();
                            continue;
                        }

                        // We have reached a new quiz
                        if (line.contains("Quiz:")) {

                            // Set the name of the quiz
                            q = new Quiz();
                            q.setName(line.substring(line.indexOf(":") + 2));
                            line = br.readLine();

                            // While we are still in this course and quiz, get all the data from it
                            while (!line.contains("Users:") && !line.contains("Course:") && !line.contains("Quiz:") && line != null) {

                                // If the line is empty, continue to the next line
                                if (line.isBlank()) {
                                    line = br.readLine();
                                    continue;
                                }

                                // We have reached a new question
                                if (line.contains("Question: ")) {

                                    ques = new Question();
                                    ques.setPrompt(line.substring(line.indexOf(":") + 2));
                                    line = br.readLine();

                                    // While we are getting all the answer choices
                                    while (!line.contains("Answer:")) {

                                        ques.addChoice(line);
                                        line = br.readLine();
                                    }

                                    // We have reached the answer
                                    ques.setAnswer(line.substring(line.indexOf(":") + 2));

                                    q.addQuestion(ques);
                                }

                                // If we have reached a new user or course or quiz, advance to next line
                                if (!(line.contains("Users:") || line.contains("Course:") || line.contains("Quiz:") || line == null)) {
                                    line = br.readLine();
                                }
                            }

                            c.addQuiz(q);
                        }

                        // If we have reached a new user or course, advance to next line
                        if (!(line.contains("Users:") || line.contains("Course:") || line.contains("Quiz:") || line == null)) {
                            line = br.readLine();
                        }
                    }

                    courses.add(c);
                }

                // We have reached a new user
                if (line.contains("Name:")) {

                    u = new User();
                    boolean isTeacher = true;

                    // Read the name of the user
                    u.setName(line.substring(line.indexOf(":") + 2));
                    line = br.readLine();

                    // Read the username of the user
                    u.setUsername(line.substring(line.indexOf(":") + 2));
                    line = br.readLine();

                    // Read the password of the user
                    u.setPassword(line.substring(line.indexOf(":") + 2));
                    line = br.readLine();

                    // Read if the user is a teacher
                    String isTeach = line.substring(line.indexOf(":") + 2);
                    if (isTeach.equals("Yes")) {
                        isTeacher = true;
                    } else {
                        isTeacher = false;
                    }
                    u.setTeacherPermission(isTeacher);

                    // This is a teacher
                    if (u.isTeacherPermission()) {
                        users.add(new Teacher(u.getName(), u.getUsername(), u.getPassword(), u.isTeacherPermission()));

                        // This is a student
                    } else {
                        users.add(new Student(u.getName(), u.getUsername(), u.getPassword(), u.isTeacherPermission()));
                    }
                }

                // If this is not the beginning of a course or user, or if this is the start of courses or users
                if (!(line.contains("Course") || line.contains("Name")) || line.contains("Courses") || line.contains("Users")) {
                    line = br.readLine();
                }
            }

            br.close();
            pw.close();
            socket.close();
        } catch (IOException e) {
            //System.out.println("Could not read from file");
        }
    }
}
