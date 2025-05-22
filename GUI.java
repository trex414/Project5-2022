import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;

/*
Things to be done:
2. Editing quiz (quiz name, quiz questions, quiz new choices) needs to be worked on - kind of works
    a. It seems like quiz new choices is the one that's not working, quiz name and quiz questions look to be working fine
3. Viewing and Grading student submissions still needs to be implemented. Currently throws a null pointer exception

I have yet to test add quiz with a file
*/

public class GUI extends JComponent implements Runnable {
    JFrame frameStart; // Start up screen with options 1. Set up Teacher account ... 5. Exit
    JFrame frameSetupA; // Creating a teacher account
    JFrame frameSignIn; // Sign in (either as a teacher or a student)
    JFrame frameDeleteA; // Delete account (either a teacher or student)
    JFrame frameEditAO; // Change name, username, password of an account
    JFrame frameCourseO; // Add course, join course, edit course, remove course
    JFrame frameAddCourse; // adding a course by inputting its name
    JFrame frameJoinC;
    JFrame frameDeleteC;
    JFrame frameEditCO;
    JFrame frameTO;
    JFrame frameEditQO;
    JFrame frameEditQ1;
    JFrame frameEditQ2;
    JFrame frameDeleteQ;
    JFrame frameSO;
    JFrame frameCheckGrade;
    JFrame frameDisplayGrade;
    JFrame frameTQA;
    JFrame frameTQB;
    JFrame frameTQ1;
    JFrame frameTQ2;
    JFrame frameExit;
    JFrame frameCQ1;
    JFrame frameCQF;
    JFrame frameCQ;
    JFrame frameCQ2;
    JFrame frameEQ3A;
    JFrame frameEQ3B;

    JFrame frameVSG;
    JFrame frameVSG1;
    JFrame frameVSG3;
    JButton enterVSG;
    JButton enterVSG1;
    JButton enterVSG3;
    JButton enterDelQuiz; // JButton for deleting a quiz, they will enter the name of the quiz to be deleted and then press enter
    JButton enterQuizEditOp; // JButton for editing a quiz, making the choice to edit name, questions, choices
    // JButton enterStuTakeQuiz;

    // Options we need
    int option;
    // Buttons we need
    JButton enterU;
    JButton enterS;
    JButton enterTO;
    JButton enterDA;
    JButton enterCO;
    JButton enterAC;
    JButton enterJC;
    JButton enterRC;
    JButton enterSO;
    JButton enterQSO;
    JButton enterCQ;
    JButton enter;
    JButton exitPanel;
    JButton logIn;
    JButton userAccount1;
    JButton userAccount2;
    JButton buttonOne;
    JButton enterQO;
    JButton enterCG;
    JButton enterDG;
    JButton enterQC;
    JButton enterUA;
    JButton enterFA;
    // JButton enterDQ;
    JButton enterEQO;
    JButton enterEQ1;
    JButton enterEQ2;
    JButton enterEQ3;
    JButton enterEQ3B;
    JButton enterQC1;
    JButton enterQC2;

    // exit panel
    JButton exit;
    JButton signOut;

    // JLabels we need
    JLabel name;
    JLabel username;
    JLabel password;
    JLabel labelOne;
    JLabel labelTwo;
    JLabel labelThree;
    // JTextFields
    JTextField textName;
    JTextField textUsername;
    JTextField textPassword;
    JTextField textOne;
    JTextField textTwo;
    JTextField textThree;
    JTextField textFour;
    // combo box
    JComboBox comboBoxOne;
    // JPanels
    JPanel panelOne;
    JPanel panelTwo;
    JPanel panelThree;
    JPanel panelFour;
    JPanel panelFive;
    JPanel panelSix;
    JPanel panelSeven;
    JPanel panelEight;

    ArrayList<String> usernames = new ArrayList<String>();

    Manager m = new Manager();

    Course c; // temporary variable to hold all the instances of course
    Quiz q; // generic variable to hold a quiz

    // current user
    User current = null;

    Question ques = null; // generic variable to hold a question

    // course
    Teacher t;
    Student s;

    boolean isTeacher;
    boolean isValidInput = true;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GUI());
    }

    @Override
    public void run() {
        startupScreen();
        m.readFromFile();
        if (m.getUsers() != null) {
            for (int i = 0; i < m.getUsers().size(); i++) {
                users.add(m.getUser(i));
            }
        }
    }

    int amountQuestions = 0;
    String quizName;
    String amount;
    int count = 1;

    // For holding users?
    ArrayList<User> users = new ArrayList<>();

    // For holding the questions
    ArrayList<String> questionsS = new ArrayList<>();

    // For holding the options to each question
    ArrayList<String> options = new ArrayList<>();

    // For holding the answer choices
    ArrayList<Integer> answers = new ArrayList<>();

    ArrayList<String> responses = new ArrayList<>();

    ArrayList<String> grades = new ArrayList<>();

    Hashtable<Quiz, ArrayList<String>> submissions = new Hashtable<>();
    Hashtable<Quiz, String> quizGrades = new Hashtable<>();

    String courseName;

    String gradeRepresentation = "";

    ActionListener actionListener = new ActionListener() {

        boolean isTeacher;

        public void actionPerformed(ActionEvent e) {

            // DEBUGGING
            // JOptionPane.showMessageDialog(null, m.toString(), "Manager", JOptionPane.INFORMATION_MESSAGE);

            // Set up a Teacher account
            if (e.getSource() == userAccount1) {
                frameStart.dispose();
                isTeacher = true;
                setupAccount();
            }

            // Set up a Student account
            if (e.getSource() == userAccount2) {
                frameStart.dispose();
                isTeacher = false;
                setupAccount();
            }

            // getting info for students and teachers
            if (e.getSource() == enterU) {

                // make a create account method
                current = createAccount(isTeacher, textName.getText(), textUsername.getText(), textPassword.getText());
                users.add(current);

                if (isTeacher) {
                    t = new Teacher(current.getName(), current.getUsername(), current.getPassword(), current.isTeacherPermission());
                } else {
                    s = new Student(current.getName(), current.getUsername(), current.getPassword(), current.isTeacherPermission());
                }

                if (current == null) {
                    JOptionPane.showMessageDialog(null, "Enter your name, username, and password!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    frameSetupA.dispose();
                    m.addUser(current);

                    current = null;
                    startupScreen();
                }
            }

            // Log-In
            if (e.getSource() == logIn) {
                frameStart.dispose();
                signIn();
            }

            // sign in
            if (e.getSource() == enterS) {

                if (comboBoxOne.getSelectedIndex() == 0) {
                    isTeacher = true;
                } else {
                    isTeacher = false;
                }

                // Ensure that user exists
                if (m.isUser(textUsername.getText(), textPassword.getText(), isTeacher)) {
                    current = m.getUser(textUsername.getText(), textPassword.getText());
                    // System.out.println("Printing current");
                    // System.out.println(current);
                }

                if (current == null) {
                    JOptionPane.showMessageDialog(null, "No user with that username and password!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (comboBoxOne.getSelectedIndex() == 0 && current.isTeacherPermission()) {

                        // for teachers
                        frameSignIn.dispose();
                        courseOptions();

                    } else if (!current.isTeacherPermission()) {

                        //for students
                        frameSignIn.dispose();
                        joinCourse();

                    } else {
                        JOptionPane.showMessageDialog(null, "Login failed!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }

            // Delete account
            if (e.getSource() == buttonOne) {
                frameStart.dispose();
                deleteAccount();
            }

            if (e.getSource() == enterDA) {

                if (comboBoxOne.getSelectedIndex() == 0) {
                    isTeacher = true;
                } else {
                    isTeacher = false;
                }

                // deleteAccountInfo(comboBoxOne.getSelectedIndex(), textUsername.getText(), textPassword.getText());

                if (m.removeUser(isTeacher, textUsername.getText(), textPassword.getText())) {
                    frameDeleteA.dispose();

                    current = null;
                    startupScreen();
                } else {
                    JOptionPane.showMessageDialog(null, "User does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Exit
            if (e.getSource() == exitPanel) {
                //JOptionPane.showMessageDialog(null, m.toString(), "Manager", JOptionPane.INFORMATION_MESSAGE);
                frameStart.dispose();
                exitOrSignout();
            }

            if (e.getSource() == exit) {

                m.writeToFile();

                frameExit.dispose();
                lastSave();
                // close the whole thing
                //System.out.println("closed");
                System.exit(0);
                return;
            }

            if (e.getSource() == signOut) {
                frameExit.dispose();

                current = null;
                startupScreen();
            }

            // Course options
            if (e.getSource() == enterCO) {
                int num = comboBoxOne.getSelectedIndex();
                frameCourseO.dispose();

                // add a course
                if (num == 0) {
                    addCourse();

                    // join a course
                } else if (num == 1) {
                    joinCourse();

                    // edit a course
                } else if (num == 2) {
                    editCourseOptions();

                    // remove a course
                } else {
                    deleteCourse();
                }
            }

            // add course
            if (e.getSource() == enterAC) {
                if (textName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a course name!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (!m.addCourse(new Course(textName.getText()))) {
                    JOptionPane.showMessageDialog(null, "Course already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    frameAddCourse.dispose();

                    courseOptions();
                }
            }
            // join course
            if (e.getSource() == enterJC) {

                joinCourseInfo(textName.getText());

                c = m.getCourse(textName.getText());

                // No course with that name exists
                if (c == null) {
                    error("Course does not exist!");
                } else {

                    // make this variable actually have a name
                    courseName = c.getName();

                    frameJoinC.dispose();

                    // find out what account is doing stuff current user

                    // Throws null pointer exception

                    if (isTeacher) {
                        teacherOptions();
                    } else {
                        studentOptions();
                    }
                }
            }

            // remove course
            if (e.getSource() == enterRC) {

                c = m.getCourse(textName.getText());

                // No course with that name exists
                if (c == null) {
                    JOptionPane.showMessageDialog(null, "Course does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    m.removeCourse(m.getCourses().indexOf(c));
                    frameDeleteC.dispose();

                    current = null;
                    startupScreen();
                }
            }

            // edit course
            if (e.getSource() == enter) {

                isValidInput = true;

                // Ensure the user fills in both text boxes
                if (textName.getText().isBlank() || textOne.getText().isBlank()) {
                    error("Pleaser enter a course name and a new course name!");
                    isValidInput = false;
                }

                // If this course exists
                if (!m.containsCourse(new Course(textName.getText())) && isValidInput) {
                    error("No such course exists!");
                    isValidInput = false;
                }

                // If the new course name is already taken by another course
                if (m.containsCourse(new Course(textOne.getText())) && isValidInput) {
                    error("Course name is already taken!");
                    isValidInput = false;
                }

                // If everything works, change the name
                if (isValidInput) {
                    m.getCourse(textName.getText()).setName(textOne.getText());
                    frameEditCO.dispose();
                    courseOptions();
                }
            }

            // teachers options and all teacher juice
            if (e.getSource() == enterTO) {

                int num = comboBoxOne.getSelectedIndex();
                frameTO.dispose();

                //add quiz
                if (num == 0) {
                    createQuizOption();

                    //edit
                } else if (num == 1) {
                    editQuizOptions();

                    //remove
                } else if (num == 2) {
                    deleteQuiz();

                    //view student grade
                } else {
                    checkStudentQuiz();

                }
            }

            // add quiz
            if (e.getSource() == enterCQ) {
                int num = comboBoxOne.getSelectedIndex();
                frameCQ.dispose();
                if (num == 0) {
                    createQuizFile();
                } else {
                    createQuiz();
                }
            }
            // make quiz with file
            if (e.getSource() == enterQC) {
                q = t.addFileQuiz(courseName, textName.getName());
                if (q == null) {
                    JOptionPane.showMessageDialog(null, "Making Quiz With File Did Not Work",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (q != null) {
                    q.setGraded(false);
                    m.getCourse(courseName).addQuiz(q);
                    frameCQF.dispose();
                    teacherOptions();
                }

            }

// make quiz without file
            if (e.getSource() == enterQC1) {

                isValidInput = true;
                count = 0;

                // Number of questions (verify it too)
                amount = textOne.getText();
                try {
                    amountQuestions = Integer.parseInt(amount);
                    if (amountQuestions < 1 || amountQuestions > 80) {
                        error("Invalid Input! Please enter a number from 1-80.");
                        isValidInput = false;
                    }
                } catch (Exception ex) {
                    error("Invalid Input! Please enter a number.");
                    isValidInput = false;
                }

                if (textName.getText().isBlank() && isValidInput) {
                    error("Please enter a quiz name!");
                    isValidInput = false;
                }

                // Get the quiz name
                quizName = textName.getText();

                if (isValidInput) {

                    // System.out.println("Valid input");

                    // Create a new quiz object and add it to the current course
                    q = new Quiz();
                    // c.addQuiz(q);

                    if (m.containsCourse(c)) {
                        // System.out.println("We got that course bro");
                        // System.out.println(c.getName());
                    }

                    // Load the new frame
                    frameCQ.dispose();
                    createQuiz2();
                }
            }

            // deleting quiz, they must enter the name of the quiz
            if (e.getSource() == enterDelQuiz) {

                // textName is name of quiz
                if (c.containsQuiz(textName.getText())) {
                    c.removeQuiz(textName.getText());
                    JOptionPane.showMessageDialog(null, "Quiz removed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frameDeleteQ.dispose();
                    teacherOptions();
                } else {
                    error("No such quiz exists!");
                }
            }

            /* We should use the Quiz class here */
            // Issues: does not add quiz name, adds questions that have been rejected, does not add last question
            // Make quiz without file
            if (e.getSource() == enterQC2) {

                isValidInput = true;

                // When they add a question (prompt, choices, and answer together) but have yet to reach the amount of questions on the quiz
                if (count != amountQuestions) {

                    ques = new Question();

                    //System.out.println("Adding question");

                    if (textName.getText().isBlank()) {
                        error("Please enter a question!");
                        isValidInput = false;
                    }

                    // Add the prompt to the list of prompts
                    questionsS.add(textName.getText());
                    ques.setPrompt(textName.getText());

                    // Add the list of options
                    options.add(textOne.getText() + "," + textTwo.getText() + "," + textThree.getText() + "," + textFour.getText());
                    ques.addChoice(textOne.getText());
                    ques.addChoice(textTwo.getText());
                    ques.addChoice(textThree.getText());
                    ques.addChoice(textFour.getText());

                    if (textOne.getText().isBlank()) {
                        error("Please enter at least one option!");
                        isValidInput = false;
                    }

                    // Add the answer choice
                    try {

                        int choice = Integer.parseInt(textPassword.getText()) - 1;
                        answers.add(choice);

                        // System.out.println(choice);
                        // System.out.println(ques.getChoices().size());

                        // They did not enter a valid choice option (i.e. if their option is not an answer choice)
                        if (choice < 0 || choice >= ques.getChoices().size()) {

                            // Throw an error
                            error("Please enter a valid answer choice!");
                            isValidInput = false;

                        } else {

                            //System.out.println("Question has been added fully");
                            ques.setAnswer(ques.getChoice(choice));
                        }
                    } catch (Exception ex) {
                        error("Invalid Input! Please enter a number.");
                        isValidInput = false;
                    }

                    if (isValidInput) {

                        // System.out.println("Valid input");
                        // System.out.println(ques);
                        q.addQuestion(ques);
                        count++;

                        //System.out.printf("count: %d\n", count + 1);

                        // Loop back
                        frameCQ2.dispose();
                        createQuiz2();
                    }

                }
                if (count == amountQuestions) {


                    /* Somehow implement Manager here */
                    frameCQ2.dispose();

                    // System.out.println(q);
                    // System.out.println(courseName);

                    t.setCourses(m.getCourses());

                    q = t.addQuiz(courseName, quizName, questionsS, options, answers);

                    if (q != null) {
                        q.setGraded(false);
                        m.getCourse(courseName).addQuiz(q);
                    }

                    // System.out.println(q);

                    // System.out.println(m);

                    questionsS.clear();
                    options.clear();
                    answers.clear();

                    teacherOptions();
                }
            }

            //edit quiz
            if (e.getSource() == enterEQO) {
                int num = comboBoxOne.getSelectedIndex();
                frameEditQO.dispose();
                // Edit Quiz Name
                if (num == 0) {
                    editQuiz1();
                    // edit quiz question
                } else if (num == 1) {
                    editQuiz2();
                    // edit quiz new choices
                } else {
                    editQuiz3A();
                }
            }

            // edit quiz name
            if (e.getSource() == enterEQ1) {

                if (textName.getText().isBlank() || textOne.getText().isBlank()) {
                    error("Please enter a quiz name and a new quiz name!");
                } else {

                    // System.out.println(courseName);
                    String s = t.modifyQuizName(courseName, textName.getText(), textOne.getText(), m);
                    // System.out.println(s);
                    frameEditQ1.dispose();
                    teacherOptions();
                }
            }

            // edit quiz question
            if (e.getSource() == enterEQ2) {

                if (textName.getText().isBlank() || textOne.getText().isBlank() || textTwo.getText().isBlank()) {
                    error("Please enter a quiz name, question, and new question!");
                } else {
                    String s = t.modifyQuizPrompt(courseName, textName.getText(), textOne.getText(), textTwo.getText(), m);
                    // System.out.println(s);
                    frameEditQ2.dispose();
                    teacherOptions();
                }
            }

            // edit quiz new choices
            /* This is currently broken */
            // This is the one that gets the quiz and the question
            if (e.getSource() == enterEQ3) {

                if (textName.getText().isBlank() || textOne.getText().isBlank()) {
                    error("Please answer each prompt!");
                } else {

                    // Get quiz name and question name
                    String quizNamE = textName.getText();
                    String questionName = textOne.getText();
                    // If there is no quiz with this name
                    if (!c.containsQuiz(quizNamE)) {
                        error("No quiz with that name!");
                        // Or if there is no question with this prompt in the quiz
                    } else if (!c.getQuiz(quizNamE).containsQuestion(questionName)) {
                        error("No question with this prompt!");
                    } else {

                        q = c.getQuiz(quizNamE);
                        ques = q.getQuestion(questionName);

                        frameEQ3A.dispose();
                        editQuiz3B();

                        // String options1 = textOne.getText() + " " + textTwo.getText() + " " + textThree.getText() + " " + textFour.getText();
                        // int answerNum = Integer.parseInt(textPassword.getText()) - 1;
                        // String s = t.modifyQuizChoices(courseName, quizNamE, questionName, options1, answerNum, m);
                        // System.out.println(s);
                        // frameEQ3B.dispose();
                        // teacherOptions();
                    }
                }
            }

            // Entering the options for the new quiz choices
            if (e.getSource() == enterEQ3B) {

                isValidInput = true;

                if (textOne.getText().isBlank()) {
                    error("Please enter at least one option!");
                    isValidInput = false;
                }

                if (textPassword.getText().isBlank() && isValidInput) {
                    error("Please enter an answer choice!");
                }

                // Add the list of options, but only adds them if there is something in them
                // options.add(textOne.getText() + "," + textTwo.getText() + "," + textThree.getText() + "," + textFour.getText());
                ques.removeChoices();
                ques.addChoice(textOne.getText());
                ques.addChoice(textTwo.getText());
                ques.addChoice(textThree.getText());
                ques.addChoice(textFour.getText());

                // Add the answer choice
                try {

                    int choice = Integer.parseInt(textPassword.getText()) - 1;

                    // System.out.println(choice);
                    // System.out.println(ques.getChoices().size());

                    // They did not enter a valid choice option (i.e. if their option is not an answer choice)
                    if ((choice < 0 || choice >= ques.getChoices().size()) && isValidInput) {

                        // Throw an error
                        error("Please enter a valid answer choice!");
                        isValidInput = false;
                    } else {

                        //System.out.println("Question has been added fully");
                        ques.setAnswer(ques.getChoice(choice));
                    }
                } catch (Exception ex) {
                    if (isValidInput) {
                        error("Invalid Input! Please enter a number.");
                    }
                    isValidInput = false;
                }

                if (isValidInput) {

                    // q.addQuestion(ques);

                    // Loop back
                    frameEQ3B.dispose();
                    //frameEQ3B.setVisible(false);
                    teacherOptions();
                }
            }

            // remove quiz
            /* if (e.getSource() == enterDQ) {
                t.removeQuiz(courseName, textName.getText(), m);
                frameDeleteQ.dispose();
                startupScreen();
            }*/


            // view student grade
            if (e.getSource() == enterVSG) {

                isValidInput = true;

                if (textOne.getText().isBlank() || textTwo.getText().isBlank()) {
                    error("Please enter a username and quiz name!");
                    isValidInput = false;
                }

                if (!c.containsQuiz(textTwo.getText()) && isValidInput) {
                    error("No quiz with that name in this course!");
                    isValidInput = false;
                }

                User temp = new User();
                temp.setUsername(textOne.getText());
                if (!m.containsUser(temp) && isValidInput) {
                    error("No user with that username!");
                    isValidInput = false;
                }

                // find quiz
                if (isValidInput) {

                    frameVSG.dispose();
                    checkStudentQuiz2();
                }
            }

            if (e.getSource() == enterVSG1) {

                //panel will get students question and count++ to get different question
                try {
                    Integer.parseInt(textOne.getText());
                    grades.add(textOne.getText());
                    gradeRepresentation += grades.get(grades.indexOf(textOne.getText())) + ",";
                    if (count == q.getQuestions().size()) {
                        frameVSG1.dispose();
                        checkStudentQuiz3();
                    } else {
                        count++;
                        frameVSG1.dispose();
                        checkStudentQuiz2();
                    }
                } catch (NumberFormatException ex) {
                    error("Enter a numerical score!");
                    frameVSG1.dispose();
                    checkStudentQuiz2();
                }
            }
            if (e.getSource() == enterVSG3) {
                try {
                    Integer.parseInt(textTwo.getText());
                    Integer.parseInt(textOne.getText());
                    gradeRepresentation += textTwo.getText() + "," + textOne.getText();
                    quizGrades.put(q, gradeRepresentation);
                    s.setGrades(quizGrades);
                    q.setGraded(true);
                    frameVSG3.dispose();
                    teacherOptions();
                } catch (NumberFormatException ex) {
                    error("Enter a numerical score!");
                    frameVSG3.dispose();
                    checkStudentQuiz3();
                }
            }


            // Students options and all their juice

            // take quiz options
            if (e.getSource() == enterSO) {
                int num = comboBoxOne.getSelectedIndex();
                frameSO.dispose();
                if (num == 0) {
                    takeQuizA();
                } else {
                    checkGrade();
                }
            }

            if (e.getSource() == enterQO) {
                frameTQA.dispose();
                if (m.getCourse(courseName).containsQuiz(textOne.getText())) {
                    q = m.getCourse(courseName).getQuiz(textOne.getText());
                } else {
                    error("Quiz does not exist!");
                    frameTQA.dispose();
                    takeQuizA();
                }
                count = 1;
                takeQuizB();
            }

            if (e.getSource() == enterCG){
                frameCheckGrade.dispose();
                if (m.getCourse(courseName).containsQuiz(textName.getText())) {
                    q = m.getCourse(courseName).getQuiz(textName.getText());
                } else {
                    error("Quiz does not exist!");
                    frameCheckGrade.dispose();
                    checkGrade();
                }
                if (q.getGraded()) {
                    gradeRepresentation = s.getQuizGrade(q);
                    String[] scores = gradeRepresentation.split(",");
                    displayGrades(scores);
                } else {
                    error("Quiz has not been graded!");
                    frameCheckGrade.dispose();
                    checkGrade();
                }
            }

            if (e.getSource() == enterDG) {
                frameDisplayGrade.dispose();

                studentOptions();
            }

            // take quiz options
            if (e.getSource() == enterQSO) {
                int num = comboBoxOne.getSelectedIndex();
                frameTQB.dispose();
                if (num == 0) {
                    //file answer
                    takeQuiz1();
                } else {
                    //manual
                    takeQuiz2();
                }
            }

            if (e.getSource() == enterUA) {
                try {
                    BufferedReader bfr = new BufferedReader(new FileReader(textName.getText()));
                    int num = Integer.parseInt(bfr.readLine());
                    responses.add(q.getQuestion(count - 1).getChoice(num - 1));
                } catch (IOException ex) {
                    error("Could not read from file");
                }

                if (count == q.getQuestions().size()) {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    JOptionPane.showMessageDialog(null, "Submitted!\n" + dateFormat.format(timestamp), "Quiz Complete", JOptionPane.INFORMATION_MESSAGE);
                    submissions.put(q, responses);
                    s.setSubmissions(submissions);
                    frameTQ1.dispose();
                    studentOptions();
                } else {
                    count++;
                    frameTQ1.dispose();
                    takeQuiz1();
                }
            }

            //F answers
            if (e.getSource() == enterFA) {
                int num = comboBoxOne.getSelectedIndex();
                responses.add(q.getQuestion(count - 1).getChoice(num));

                if (count == q.getQuestions().size()) {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    JOptionPane.showMessageDialog(null, "Submitted!\n" + dateFormat.format(timestamp), "Quiz Complete", JOptionPane.INFORMATION_MESSAGE);
                    
                    submissions.put(q, responses);
                    s = (Student) current;
                    s.setSubmissions(submissions); // error here
                    frameTQ2.dispose();
                    studentOptions();
                } else {
                    count++;
                    frameTQ2.dispose();
                    takeQuiz2();
                }
            }


        }
    };

    public User createAccount(boolean isTeacher, String name, String username, String password) {

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return null;
        }

        if (m.containsUser(new User(name, username, password, isTeacher))) {
            return null;
        }

        if (isTeacher) {
            t = new Teacher(name, username, password, isTeacher);
            return t;
        } else {
            Student s = new Student(name, username, password, isTeacher);
            return s;
        }
    }

    public void deleteAccountInfo(int isTeacher, String username, String password) {
        if (isTeacher == 1) {
            User u = null;
            for (int i = 0; i < m.getUsers().size(); i++) {
                if (m.getUser(i) instanceof Student && m.getUser(i).getUsername().equals(username) && m.getUser(i).getPassword().equals(password)) {
                    u = m.getUser(i);
                    m.removeUser(i);
                    //usernames.remove(i);
                    break;
                }
            }

            if (u == null) {
                JOptionPane.showMessageDialog(null, "User does not exist",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            User u = null;
            for (int i = 0; i < m.getUsers().size(); i++) {
                if (m.getUser(i) instanceof Teacher && m.getUser(i).getUsername().equals(username) && m.getUser(i).getPassword().equals(password)) {
                    u = m.getUser(i);
                    m.removeUser(i);
                    //usernames.remove(i);
                    break;
                }
            }

            if (u == null) {
                JOptionPane.showMessageDialog(null, "User does not exist",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void joinCourseInfo(String courseName) {

    }

    public void lastSave() {
        //System.out.println("saved");
    }

    public void startupScreen() {

        // Options for Startup Screen
        // 1. Set up Teacher Account
        // 2. Set up Student Account
        // 3. Log In
        // 4. Delete Account
        // 5. Exit

        frameStart = new JFrame();
        Container content = frameStart.getContentPane();
        content.setLayout(new GridLayout(6, 1));
        frameStart.setTitle("Start Menu");

        //jPanel we need
        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        //each panel
        labelOne = new JLabel("Welcome To The Start Menu");
        panelOne.add(labelOne);

        userAccount1 = new JButton("Set up a Teacher account");
        userAccount1.addActionListener(actionListener);
        userAccount1.setPreferredSize(new Dimension(500, 100));
        panelTwo.add(userAccount1);

        userAccount2 = new JButton("Set up a Student account");
        userAccount2.setPreferredSize(new Dimension(500, 100));
        userAccount2.addActionListener(actionListener);
        panelThree.add(userAccount2);

        logIn = new JButton("Log-In");
        logIn.setPreferredSize(new Dimension(500, 100));
        logIn.addActionListener(actionListener);
        panelFour.add(logIn);

        buttonOne = new JButton("Delete Account");
        buttonOne.setPreferredSize(new Dimension(500, 100));
        buttonOne.addActionListener(actionListener);
        panelFive.add(buttonOne);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(500, 100));
        exitPanel.addActionListener(actionListener);
        panelSix.add(exitPanel);

        // add them to cont
        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);
        content.add(panelFour);
        content.add(panelFive);
        content.add(panelSix);

        frameStart.setSize(600, 400);
        frameStart.setLocationRelativeTo(null);
        frameStart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameStart.setVisible(true);
    }

    public void setupAccount() {
        frameSetupA = new JFrame();
        Container content = frameSetupA.getContentPane();
        content.setLayout(new GridLayout(5, 2));
        frameSetupA.setTitle("Creating Account");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To Creating Your Account");
        panelOne.add(labelOne);

        name = new JLabel("Full name: ");
        textName = new JTextField(15);
        textName.addActionListener(actionListener);
        panelTwo.add(name);
        panelTwo.add(textName);

        username = new JLabel("Username: ");
        textUsername = new JTextField(15);
        textUsername.addActionListener(actionListener);
        panelThree.add(username);
        panelThree.add(textUsername);

        password = new JLabel("Password: ");
        textPassword = new JTextField(15);
        textPassword.addActionListener(actionListener);
        panelFour.add(password);
        panelFour.add(textPassword);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterU = new JButton("Enter");
        enterU.setPreferredSize(new Dimension(250, 100));
        enterU.addActionListener(actionListener);
        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        panelFive.add(enterU);
        panelFive.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);
        content.add(panelFour);
        content.add(panelFive);

        frameSetupA.setSize(600, 400);
        frameSetupA.setLocationRelativeTo(null);
        frameSetupA.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameSetupA.setVisible(true);
    }

    public void signIn() {
        frameSignIn = new JFrame();
        Container content = frameSignIn.getContentPane();
        content.setLayout(new GridLayout(5, 2));
        frameSignIn.setTitle("Sign In");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To Sign-In");
        panelOne.add(labelOne);

        labelTwo = new JLabel("Who Are You?");
        String[] options = {"I am a teacher", "I am a student"};
        comboBoxOne = new JComboBox(options);
        comboBoxOne.addActionListener(actionListener);
        panelTwo.add(comboBoxOne);

        username = new JLabel("Username: ");
        textUsername = new JTextField(15);
        textUsername.addActionListener(actionListener);
        panelThree.add(username);
        panelThree.add(textUsername);

        password = new JLabel("Password: ");
        textPassword = new JTextField(15);
        textPassword.addActionListener(actionListener);
        panelFour.add(password);
        panelFour.add(textPassword);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterS = new JButton("Enter");
        enterS.setPreferredSize(new Dimension(250, 100));
        enterS.addActionListener(actionListener);
        panelFive.add(enterS);
        panelFive.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);
        content.add(panelFour);
        content.add(panelFive);

        frameSignIn.setSize(700, 500);
        frameSignIn.setLocationRelativeTo(null);
        frameSignIn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameSignIn.setVisible(true);
    }

    public void deleteAccount() {
        frameDeleteA = new JFrame();
        Container content = frameDeleteA.getContentPane();
        content.setLayout(new GridLayout(5, 2));
        frameDeleteA.setTitle("Delete Account");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To Deleting Accounts");
        panelOne.add(labelOne);

        labelTwo = new JLabel("Who Are You?");
        String[] options = {"I am a teacher", "I am a student"};
        comboBoxOne = new JComboBox(options);
        comboBoxOne.addActionListener(actionListener);
        panelTwo.add(comboBoxOne);

        username = new JLabel("Username: ");
        textUsername = new JTextField(15);
        textUsername.addActionListener(actionListener);
        panelThree.add(username);
        panelThree.add(textUsername);

        password = new JLabel("Password: ");
        textPassword = new JTextField(15);
        textPassword.addActionListener(actionListener);
        panelFour.add(password);
        panelFour.add(textPassword);

        enterDA = new JButton("Enter");
        enterDA.setPreferredSize(new Dimension(250, 100));
        enterDA.addActionListener(actionListener);
        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        panelFive.add(enterDA);
        panelFive.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);
        content.add(panelFour);
        content.add(panelFive);

        frameDeleteA.setSize(600, 400);
        frameDeleteA.setLocationRelativeTo(null);
        frameDeleteA.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameDeleteA.setVisible(true);
    }

    public void editAccountOptions() {
        frameEditAO = new JFrame();
        Container content = frameEditAO.getContentPane();
        content.setLayout(new GridLayout(3, 2));
        frameEditAO.setTitle("Edit Account Options");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To The Account Options");
        panelOne.add(labelOne);

        labelTwo = new JLabel("What Would You Like To Do?");
        String[] options = {"Choose an option", "Change Name", "Change Username", "Change Password"};
        comboBoxOne = new JComboBox(options);
        comboBoxOne.setSelectedIndex(0);
        comboBoxOne.addActionListener(actionListener);
        panelTwo.add(labelTwo);
        panelTwo.add(comboBoxOne);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enter = new JButton("Enter");
        enter.setPreferredSize(new Dimension(250, 100));
        enter.addActionListener(actionListener);
        panelThree.add(enter);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameEditAO.setSize(600, 400);
        frameEditAO.setLocationRelativeTo(null);
        frameEditAO.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameEditAO.setVisible(true);
    }

    public void courseOptions() {
        frameCourseO = new JFrame();
        Container content = frameCourseO.getContentPane();
        content.setLayout(new GridLayout(3, 2));
        frameCourseO.setTitle("Course Options");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To The Course Options");
        panelOne.add(labelOne);

        labelTwo = new JLabel("What Would You Like To Do?");
        String[] options = {"Add Course", "Join Course", "Edit Course", "Remove Course"};
        comboBoxOne = new JComboBox(options);
        comboBoxOne.addActionListener(actionListener);
        panelTwo.add(labelTwo);
        panelTwo.add(comboBoxOne);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterCO = new JButton("Enter");
        enterCO.setPreferredSize(new Dimension(250, 100));
        enterCO.addActionListener(actionListener);
        panelThree.add(enterCO);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameCourseO.setSize(600, 400);
        frameCourseO.setLocationRelativeTo(null);
        frameCourseO.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameCourseO.setVisible(true);
    }

    public void addCourse() {
        frameAddCourse = new JFrame();
        Container content = frameAddCourse.getContentPane();
        content.setLayout(new GridLayout(3, 2));
        frameAddCourse.setTitle("Add Course");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To Add Course");
        panelOne.add(labelOne);

        name = new JLabel("Course Name: ");
        textName = new JTextField(15);
        textName.addActionListener(actionListener);
        panelTwo.add(name);
        panelTwo.add(textName);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterAC = new JButton("Enter");
        enterAC.setPreferredSize(new Dimension(250, 100));
        enterAC.addActionListener(actionListener);
        panelThree.add(enterAC);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameAddCourse.setSize(600, 400);
        frameAddCourse.setLocationRelativeTo(null);
        frameAddCourse.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAddCourse.setVisible(true);
    }

    public void joinCourse() {
        frameJoinC = new JFrame();
        Container content = frameJoinC.getContentPane();
        content.setLayout(new GridLayout(3, 2));
        frameJoinC.setTitle("Join Course");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To Join Course");
        panelOne.add(labelOne);

        name = new JLabel("What Course Would You Like To Join: ");
        textName = new JTextField(15);
        textName.addActionListener(actionListener);
        panelTwo.add(name);
        panelTwo.add(textName);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterJC = new JButton("Enter");
        enterJC.setPreferredSize(new Dimension(250, 100));
        enterJC.addActionListener(actionListener);
        panelThree.add(enterJC);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameJoinC.setSize(600, 400);
        frameJoinC.setLocationRelativeTo(null);
        frameJoinC.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameJoinC.setVisible(true);
    }

    public void deleteCourse() {
        frameDeleteC = new JFrame();
        Container content = frameDeleteC.getContentPane();
        content.setLayout(new GridLayout(3, 2));
        frameDeleteC.setTitle("Delete Course");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To Delete Course");
        panelOne.add(labelOne);

        name = new JLabel("What Is The Course Name: ");
        textName = new JTextField(15);
        textName.addActionListener(actionListener);
        panelTwo.add(name);
        panelTwo.add(textName);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterRC = new JButton("Enter");
        enterRC.setPreferredSize(new Dimension(250, 100));
        enterRC.addActionListener(actionListener);
        panelThree.add(enterRC);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameDeleteC.setSize(600, 400);
        frameDeleteC.setLocationRelativeTo(null);
        frameDeleteC.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameDeleteC.setVisible(true);
    }

    public void editCourseOptions() {
        frameEditCO = new JFrame();
        Container content = frameEditCO.getContentPane();
        content.setLayout(new GridLayout(4, 2));
        frameEditCO.setTitle("Edit Course Options");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To The Edit Options");
        panelOne.add(labelOne);

        name = new JLabel("What Is The Course Name: ");
        textName = new JTextField(15);
        textName.addActionListener(actionListener);
        panelTwo.add(name);
        panelTwo.add(textName);

        name = new JLabel("What Is The New Course Name: ");
        textOne = new JTextField(15);
        textOne.addActionListener(actionListener);
        panelThree.add(name);
        panelThree.add(textOne);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enter = new JButton("Enter");
        enter.setPreferredSize(new Dimension(250, 100));
        enter.addActionListener(actionListener);
        panelFour.add(enter);
        panelFour.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);
        content.add(panelFour);

        frameEditCO.setSize(600, 400);
        frameEditCO.setLocationRelativeTo(null);
        frameEditCO.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameEditCO.setVisible(true);
    }

    public void teacherOptions() {
        frameTO = new JFrame();
        Container content = frameTO.getContentPane();
        content.setLayout(new GridLayout(4, 2));
        frameTO.setTitle("Teacher Options");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To The Teachers Options");
        panelOne.add(labelOne);

        labelTwo = new JLabel("What Would You Like To Do?");
        String[] options = {"Add Quiz", "Edit Quiz", "Remove Quiz", "View and Grade Student Submissions"};
        comboBoxOne = new JComboBox(options);
        comboBoxOne.setSelectedIndex(0);
        comboBoxOne.addActionListener(actionListener);
        panelTwo.add(labelTwo);
        panelTwo.add(comboBoxOne);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterTO = new JButton("Enter");
        enterTO.setPreferredSize(new Dimension(250, 100));
        enterTO.addActionListener(actionListener);
        panelThree.add(enterTO);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameTO.setSize(600, 400);
        frameTO.setLocationRelativeTo(null);
        frameTO.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameTO.setVisible(true);
    }

    // we could have three. one that asks how many questions, one that asks how many options per question and one that goes through each question
    public void createQuizOption() {
        frameCQ = new JFrame();
        Container content = frameCQ.getContentPane();
        content.setLayout(new GridLayout(4, 2));
        frameCQ.setTitle("Quiz Options");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To Create Quiz Options");
        panelOne.add(labelOne);

        labelTwo = new JLabel("What Would You Like To Do?");
        String[] options = {"Add Quiz With A File", "Add Quiz without A File"};
        comboBoxOne = new JComboBox(options);
        comboBoxOne.setSelectedIndex(0);
        comboBoxOne.addActionListener(actionListener);
        panelTwo.add(labelTwo);
        panelTwo.add(comboBoxOne);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterCQ = new JButton("Enter");
        enterCQ.setPreferredSize(new Dimension(250, 100));
        enterCQ.addActionListener(actionListener);
        panelThree.add(enterCQ);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameCQ.setSize(600, 400);
        frameCQ.setLocationRelativeTo(null);
        frameCQ.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameCQ.setVisible(true);
    }

    public void createQuizFile() {
        frameCQF = new JFrame();
        Container content = frameCQF.getContentPane();
        content.setLayout(new GridLayout(4, 2));
        frameCQF.setTitle("Quiz Options");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To The Create Quiz With A File");
        panelOne.add(labelOne);

        labelTwo = new JLabel("What is the File Name?");
        textName = new JTextField(15);
        textName.addActionListener(actionListener);
        panelTwo.add(labelTwo);
        panelTwo.add(textName);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterQC = new JButton("Enter");
        enterQC.setPreferredSize(new Dimension(250, 100));
        enterQC.addActionListener(actionListener);
        panelThree.add(enterQC);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameCQF.setSize(600, 400);
        frameCQF.setLocationRelativeTo(null);
        frameCQF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameCQF.setVisible(true);
    }

    public void createQuiz() {
        frameCQ = new JFrame();
        Container content = frameCQ.getContentPane();
        content.setLayout(new GridLayout(4, 2));
        frameCQ.setTitle("Question Creation");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome to Quiz Creation Page!");
        panelOne.add(labelOne);

        labelTwo = new JLabel("What is the Quiz Name?");
        textName = new JTextField(15);
        panelTwo.add(labelTwo);
        panelTwo.add(textName);

        labelThree = new JLabel("How many Questions Do You Want?");
        textOne = new JTextField(15);
        panelThree.add(labelThree);
        panelThree.add(textOne);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterQC1 = new JButton("Enter");
        enterQC1.setPreferredSize(new Dimension(250, 100));
        enterQC1.addActionListener(actionListener);
        panelFour.add(enterQC1);
        panelFour.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);
        content.add(panelFour);

        frameCQ.setSize(600, 400);
        frameCQ.setLocationRelativeTo(null);
        frameCQ.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameCQ.setVisible(true);
    }

    public void createQuiz2() {
        //Options for questions
        frameCQ2 = new JFrame();
        Container content = frameCQ2.getContentPane();
        content.setLayout(new GridLayout(8, 2));
        frameCQ2.setTitle("Question Creation");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();
        panelSeven = new JPanel();
        panelEight = new JPanel();


        labelOne = new JLabel("Welcome to Quiz Creation Page!\n if you do not want four options keep them blank.");
        panelOne.add(labelOne);

        labelTwo = new JLabel("What is the question");
        textName = new JTextField(15);
        panelTwo.add(labelTwo);
        panelTwo.add(textName);

        labelThree = new JLabel("What is your first option");
        textOne = new JTextField(15);
        panelThree.add(labelThree);
        panelThree.add(textOne);

        labelThree = new JLabel("What is your second option");
        textTwo = new JTextField(15);
        panelFour.add(labelThree);
        panelFour.add(textTwo);

        labelThree = new JLabel("What is your third option");
        textThree = new JTextField(15);
        panelFive.add(labelThree);
        panelFive.add(textThree);

        labelThree = new JLabel("What is your fourth option");
        textFour = new JTextField(15);
        panelSix.add(labelThree);
        panelSix.add(textFour);

        labelThree = new JLabel("which option number is the answer?");
        textPassword = new JTextField(15);
        panelEight.add(labelThree);
        panelEight.add(textPassword);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterQC2 = new JButton("Enter");
        enterQC2.setPreferredSize(new Dimension(250, 100));
        enterQC2.addActionListener(actionListener);
        panelSeven.add(enterQC2);
        panelSeven.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);
        content.add(panelFour);
        content.add(panelFive);
        content.add(panelSix);
        content.add(panelEight);
        content.add(panelSeven);

        frameCQ2.setSize(900, 700);
        frameCQ2.setLocationRelativeTo(null);
        frameCQ2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameCQ2.setVisible(true);
    }


    public void editQuizOptions() {
        frameEditQO = new JFrame();
        Container content = frameEditQO.getContentPane();
        content.setLayout(new GridLayout(4, 2));
        frameEditQO.setTitle("Edit Quiz Options");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To The Edit Options");
        panelOne.add(labelOne);

        labelTwo = new JLabel("What Would You Like To Do?");
        String[] options = {"Quiz Name", "Quiz Question", "Questions New Choices"};
        comboBoxOne = new JComboBox(options);
        comboBoxOne.addActionListener(actionListener);
        panelTwo.add(labelTwo);
        panelTwo.add(comboBoxOne);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterEQO = new JButton("Enter");
        enterEQO.setPreferredSize(new Dimension(250, 100));
        enterEQO.addActionListener(actionListener);
        panelThree.add(enterEQO);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameEditQO.setSize(600, 400);
        frameEditQO.setLocationRelativeTo(null);
        frameEditQO.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameEditQO.setVisible(true);
    }

    public void editQuiz1() {
        frameEditQ1 = new JFrame();
        Container content = frameEditQ1.getContentPane();
        content.setLayout(new GridLayout(4, 2));
        frameEditQ1.setTitle("Change Name");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To Change Quiz Name");
        panelOne.add(labelOne);

        name = new JLabel("What Is The Quiz Name: ");
        textName = new JTextField(15);
        textName.addActionListener(actionListener);
        panelTwo.add(name);
        panelTwo.add(textName);

        name = new JLabel("What Is The New Quiz Name: ");
        textOne = new JTextField(15);
        textOne.addActionListener(actionListener);
        panelThree.add(name);
        panelThree.add(textOne);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterEQ1 = new JButton("Enter");
        enterEQ1.setPreferredSize(new Dimension(250, 100));
        enterEQ1.addActionListener(actionListener);
        panelFour.add(enterEQ1);
        panelFour.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);
        content.add(panelFour);

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        frameEditQ1.setSize(600, 400);
        frameEditQ1.setLocationRelativeTo(null);
        frameEditQ1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameEditQ1.setVisible(true);
    }

    public void editQuiz2() {
        frameEditQ2 = new JFrame();
        Container content = frameEditQ2.getContentPane();
        content.setLayout(new GridLayout(5, 2));
        frameEditQ2.setTitle("Change Question Name");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To Change Question Name");
        panelOne.add(labelOne);

        name = new JLabel("What Is The Quiz Name: ");
        textName = new JTextField(15);
        textName.addActionListener(actionListener);
        panelTwo.add(name);
        panelTwo.add(textName);

        name = new JLabel("What Is The Question Name: ");
        textOne = new JTextField(15);
        textOne.addActionListener(actionListener);
        panelThree.add(name);
        panelThree.add(textOne);

        name = new JLabel("What Is The New Question Name: ");
        textTwo = new JTextField(15);
        textTwo.addActionListener(actionListener);
        panelFour.add(name);
        panelFour.add(textTwo);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterEQ2 = new JButton("Enter");
        enterEQ2.setPreferredSize(new Dimension(250, 100));
        enterEQ2.addActionListener(actionListener);
        panelFive.add(enterEQ2);
        panelFive.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);
        content.add(panelFour);
        content.add(panelFive);

        frameEditQ2.setSize(600, 400);
        frameEditQ2.setLocationRelativeTo(null);
        frameEditQ2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameEditQ2.setVisible(true);
    }

    // have two one that asks how many options there will be and one that finds the questions
    public void editQuiz3A() {
        frameEQ3A = new JFrame();
        Container content = frameEQ3A.getContentPane();
        content.setLayout(new GridLayout(5, 2));
        frameEQ3A.setTitle("Change Question Options");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To Change Question Options");
        panelOne.add(labelOne);

        labelTwo = new JLabel("What is the name of the quiz?");
        textName = new JTextField(15);
        textName.addActionListener(actionListener);
        panelTwo.add(labelTwo);
        panelTwo.add(textName);

        name = new JLabel("What is the name of the question?");
        textOne = new JTextField(15);
        textOne.addActionListener(actionListener);
        panelThree.add(name);
        panelThree.add(textOne);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterEQ3 = new JButton("Enter");
        enterEQ3.setPreferredSize(new Dimension(250, 100));
        enterEQ3.addActionListener(actionListener);
        panelFive.add(enterEQ3);
        panelFive.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);
        content.add(panelFour);
        content.add(panelFive);

        frameEQ3A.setSize(700, 500);
        frameEQ3A.setLocationRelativeTo(null);
        frameEQ3A.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameEQ3A.setVisible(true);
    }

    public void editQuiz3B() {
        //Options for questions
        frameEQ3B = new JFrame();
        Container content = frameEQ3B.getContentPane();
        content.setLayout(new GridLayout(8, 2));
        frameEQ3B.setTitle("Question Creation");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();
        panelSeven = new JPanel();
        panelEight = new JPanel();


        labelOne = new JLabel("Welcome to Quiz Creation Page!\n if you do not want four options keep them blank.");
        panelOne.add(labelOne);

        labelThree = new JLabel("What is your first option");
        textOne = new JTextField(15);
        panelThree.add(labelThree);
        panelThree.add(textOne);

        labelThree = new JLabel("What is your second option");
        textTwo = new JTextField(15);
        panelFour.add(labelThree);
        panelFour.add(textTwo);

        labelThree = new JLabel("What is your third option");
        textThree = new JTextField(15);
        panelFive.add(labelThree);
        panelFive.add(textThree);

        labelThree = new JLabel("What is your fourth option");
        textFour = new JTextField(15);
        panelSix.add(labelThree);
        panelSix.add(textFour);

        labelThree = new JLabel("which option number is the answer?");
        textPassword = new JTextField(15);
        panelEight.add(labelThree);
        panelEight.add(textPassword);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterEQ3B = new JButton("Enter");
        enterEQ3B.setPreferredSize(new Dimension(250, 100));
        enterEQ3B.addActionListener(actionListener);
        panelSeven.add(enterEQ3B);
        panelSeven.add(exitPanel);

        content.add(panelOne);
        content.add(panelThree);
        content.add(panelFour);
        content.add(panelFive);
        content.add(panelSix);
        content.add(panelEight);
        content.add(panelSeven);

        frameEQ3B.setSize(900, 700);
        frameEQ3B.setLocationRelativeTo(null);
        frameEQ3B.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameEQ3B.setVisible(true);
    }

    public void deleteQuiz() {
        frameDeleteQ = new JFrame();
        Container content = frameDeleteQ.getContentPane();
        content.setLayout(new GridLayout(3, 2));
        frameDeleteQ.setTitle("Edit Course");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To Delete Quiz");
        panelOne.add(labelOne);

        name = new JLabel("What Is The Name Of The Quiz: ");
        textName = new JTextField(15);
        textName.addActionListener(actionListener);
        panelTwo.add(name);
        panelTwo.add(textName);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterDelQuiz = new JButton("Enter");
        enterDelQuiz.setPreferredSize(new Dimension(250, 100));
        enterDelQuiz.addActionListener(actionListener);
        panelThree.add(enterDelQuiz);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameDeleteQ.setSize(600, 400);
        frameDeleteQ.setLocationRelativeTo(null);
        frameDeleteQ.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameDeleteQ.setVisible(true);
    }

    public void checkStudentQuiz() {
// asking for which quiz they would like to check and asking for the username (only for teachers)
        frameVSG = new JFrame();
        Container content = frameVSG.getContentPane();
        content.setLayout(new GridLayout(5, 2));
        frameVSG.setTitle("Check Student Quiz");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Check Students Quiz");
        panelOne.add(labelOne);


        name = new JLabel("What is the username of the student?");
        textOne = new JTextField(15);
        textOne.addActionListener(actionListener);
        panelTwo.add(name);
        panelTwo.add(textOne);

        name = new JLabel("What is the name of the quiz");
        textTwo = new JTextField(15);
        textTwo.addActionListener(actionListener);
        panelThree.add(name);
        panelThree.add(textTwo);


        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(100, 60));
        exitPanel.addActionListener(actionListener);
        enterVSG = new JButton("Enter");
        enterVSG.setPreferredSize(new Dimension(100, 60));
        enterVSG.addActionListener(actionListener);
        panelThree.add(enterVSG);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameVSG.setSize(700, 500);
        frameVSG.setLocationRelativeTo(null);
        frameVSG.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameVSG.setVisible(true);
    }

    public void checkStudentQuiz2() {

        //have to show the quiz
        ArrayList<String> responses = s.getQuizSubmission(q);

        ArrayList<Question> questions = q.getQuestions();

        frameVSG1 = new JFrame();
        Container content = frameVSG1.getContentPane();
        content.setLayout(new GridLayout(5, 2));
        frameVSG1.setTitle("Check Student Quiz");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Check Students Quiz");
        panelOne.add(labelOne);

        // prints them the prompt of question
        name = new JLabel("Question prompt: " + questions.get(count - 1).getPrompt());
        panelTwo.add(name);

        // prints them the answer the user put
        name = new JLabel("Student response: " + responses.get(count - 1));
        panelThree.add(name);

        name = new JLabel("Enter a numerical score for the question: ");
        textOne = new JTextField(15);
        panelFour.add(name);
        panelFour.add(textOne);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(100, 60));
        exitPanel.addActionListener(actionListener);
        enterVSG1 = new JButton("Next Question");
        enterVSG1.setPreferredSize(new Dimension(100, 60));
        enterVSG1.addActionListener(actionListener);
        panelFive.add(enterVSG1);
        panelFive.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);
        content.add(panelFour);
        content.add(panelFive);

        frameVSG1.setSize(700, 500);
        frameVSG1.setLocationRelativeTo(null);
        frameVSG1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameVSG1.setVisible(true);
    }


    public void checkStudentQuiz3() {
        frameVSG3 = new JFrame();
        Container content = frameVSG3.getContentPane();
        content.setLayout(new GridLayout(5, 2));
        frameVSG3.setTitle("Check Student Quiz");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Check Students Quiz");
        panelOne.add(labelOne);

        int totalGrade = 0;
        for (int i = 0; i < grades.size(); i++) {
            totalGrade += Integer.parseInt(grades.get(i));
        }

        // prints them the prompt of question
        name = new JLabel("Student's score for this quiz: " + totalGrade);
        textTwo = new JTextField(String.valueOf(totalGrade), 15);
        panelTwo.add(name);
        panelTwo.add(textTwo);

        // prints them the answer the user put
        name = new JLabel("Enter the maximum possible score for this quiz.");
        textOne = new JTextField(15);
        panelThree.add(name);
        panelThree.add(textOne);


        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(100, 60));
        exitPanel.addActionListener(actionListener);
        enterVSG3 = new JButton("Next Question");
        enterVSG3.setPreferredSize(new Dimension(100, 60));
        enterVSG3.addActionListener(actionListener);
        panelFour.add(enterVSG3);
        panelFour.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);
        content.add(panelFour);

        frameVSG3.setSize(700, 500);
        frameVSG3.setLocationRelativeTo(null);
        frameVSG3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameVSG3.setVisible(true);
    }

    public void studentOptions() {
        frameSO = new JFrame();
        Container content = frameSO.getContentPane();
        content.setLayout(new GridLayout(3, 2));
        frameSO.setTitle("Edit Quiz Options");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To The Student Options");
        panelOne.add(labelOne);

        labelTwo = new JLabel("What Would You Like To Do?");
        String[] options = {"Take Quiz", "Check Grade"};
        comboBoxOne = new JComboBox(options);
        comboBoxOne.addActionListener(actionListener);
        panelTwo.add(labelTwo);
        panelTwo.add(comboBoxOne);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterSO = new JButton("Enter");
        enterSO.setPreferredSize(new Dimension(250, 100));
        enterSO.addActionListener(actionListener);
        panelThree.add(enterSO);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameSO.setSize(600, 400);
        frameSO.setLocationRelativeTo(null);
        frameSO.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameSO.setVisible(true);
    }

    // giving them options for which quiz they are taking separately
    public void takeQuizA() {
        frameTQA = new JFrame();
        Container content = frameTQA.getContentPane();
        content.setLayout(new GridLayout(5, 2));
        frameTQA.setTitle("Choose Your Quiz");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Choose Your Quiz Here!");
        panelOne.add(labelOne);

        name = new JLabel("Name Of The Quiz: ");
        textOne = new JTextField(15);
        textOne.addActionListener(actionListener);
        panelTwo.add(name);
        panelTwo.add(textOne);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(100, 60));
        exitPanel.addActionListener(actionListener);
        enterQO = new JButton("Enter");
        enterQO.setPreferredSize(new Dimension(100, 60));
        enterQO.addActionListener(actionListener);
        panelThree.add(enterQO);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameTQA.setSize(600, 400);
        frameTQA.setLocationRelativeTo(null);
        frameTQA.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameTQA.setVisible(true);
    }

    public void takeQuizB() {
        frameTQB = new JFrame();
        Container content = frameTQB.getContentPane();
        content.setLayout(new GridLayout(5, 2));
        frameTQB.setTitle("Choose Your Option for Answering Quiz");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();

        labelTwo = new JLabel("Submit by File or Answer Manually");
        String[] options = {"1. File", "2. Manual"};
        comboBoxOne = new JComboBox(options);
        comboBoxOne.addActionListener(actionListener);
        panelOne.add(labelTwo);
        panelTwo.add(comboBoxOne);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(100, 60));
        exitPanel.addActionListener(actionListener);
        enterQSO = new JButton("Enter");
        enterQSO.setPreferredSize(new Dimension(100, 60));
        enterQSO.addActionListener(actionListener);
        panelThree.add(enterQSO);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameTQB.setSize(600, 400);
        frameTQB.setLocationRelativeTo(null);
        frameTQB.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameTQB.setVisible(true);
    }

    public void takeQuiz1() {
        // Sub by file
        frameTQ1 = new JFrame();
        Container content = frameTQ1.getContentPane();
        content.setLayout(new GridLayout(3, 2));
        frameTQ1.setTitle("Quiz (File)");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Question " + count + ": " + q.getQuestion(count - 1).getPrompt());
        panelOne.add(labelOne);

        labelTwo = new JLabel("Options: " + "1. " + q.getQuestion(count - 1).getChoice(0) + " 2. " + q.getQuestion(count - 1).getChoice(1)
                + " 3. " + q.getQuestion(count - 1).getChoice(2) + " 4. " + q.getQuestion(count - 1).getChoice(3));
        panelTwo.add(labelTwo);

        name = new JLabel("Upload the File with your Response: ");
        textName = new JTextField(15);
        textName.addActionListener(actionListener);
        panelThree.add(name);
        panelThree.add(textName);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterUA = new JButton("Enter");
        enterUA.setPreferredSize(new Dimension(250, 100));
        enterUA.addActionListener(actionListener);
        panelThree.add(enterUA);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameTQ1.setSize(600, 400);
        frameTQ1.setLocationRelativeTo(null);
        frameTQ1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameTQ1.setVisible(true);


    }

    public void takeQuiz2() {
        // Sub by Write
        frameTQ2 = new JFrame();
        Container content = frameTQ2.getContentPane();
        content.setLayout(new GridLayout(5, 3));
        frameTQ2.setTitle("Quiz (Manual)");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Question " + count + ": " + q.getQuestion(count - 1).getPrompt());
        panelOne.add(labelOne);

        labelTwo = new JLabel("Options: " + "1. " + q.getQuestion(count - 1).getChoice(0) + " 2. " + q.getQuestion(count - 1).getChoice(1)
        + " 3. " + q.getQuestion(count - 1).getChoice(2) + " 4. " + q.getQuestion(count - 1).getChoice(3));
        panelTwo.add(labelTwo);

        name = new JLabel("Select Your Answer: ");
        panelThree.add(name);
        String[] options = {"1.   ", "2.   ", "3.   ", "4.   "};
        comboBoxOne = new JComboBox(options);
        comboBoxOne.addActionListener(actionListener);
        panelThree.add(comboBoxOne);


        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(150, 100));
        exitPanel.addActionListener(actionListener);
        enterFA = new JButton("Enter");
        enterFA.setPreferredSize(new Dimension(150, 100));
        enterFA.addActionListener(actionListener);
        panelFour.add(enterFA);
        panelFour.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);
        content.add(panelFour);

        frameTQ2.setSize(750, 500);
        frameTQ2.setLocationRelativeTo(null);
        frameTQ2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameTQ2.setVisible(true);

    }

    public void checkGrade() {
        frameCheckGrade = new JFrame();
        Container content = frameCheckGrade.getContentPane();
        content.setLayout(new GridLayout(3, 2));
        frameCheckGrade.setTitle("Check Grade");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To The Check Grade");
        panelOne.add(labelOne);

        labelTwo = new JLabel("What is the Quiz Name");
        textName = new JTextField(15);
        panelTwo.add(labelTwo);
        panelTwo.add(textName);

        exitPanel = new JButton("Exit");
        exitPanel.setPreferredSize(new Dimension(250, 100));
        exitPanel.addActionListener(actionListener);
        enterCG = new JButton("Enter");
        enterCG.setPreferredSize(new Dimension(250, 100));
        enterCG.addActionListener(actionListener);
        panelThree.add(enterCG);
        panelThree.add(exitPanel);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameCheckGrade.setSize(600, 400);
        frameCheckGrade.setLocationRelativeTo(null);
        frameCheckGrade.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameCheckGrade.setVisible(true);
    }

    public void displayGrades(String[] scores) {
        frameDisplayGrade = new JFrame();
        Container content = frameDisplayGrade.getContentPane();
        content.setLayout(new GridLayout(4, 1));
        frameCheckGrade.setTitle("Quiz Grade");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To The Grade for the Quiz");
        panelOne.add(labelOne);

        String feedback = "";
        int sum = 0;
        for (int i = 0; i < scores.length - 2; i++) {
            feedback += "Grade for Question " + (i + 1) + ": " + scores[i] + " ";
            sum += Integer.parseInt(scores[i]);
        }

        labelTwo = new JLabel(feedback);
        panelTwo.add(labelTwo);

        labelThree = new JLabel("Final Quiz Score: " + sum + "/" + scores[scores.length - 1]);
        panelThree.add(labelThree);

        enterDG = new JButton("OK");
        enterDG.setPreferredSize(new Dimension(250, 100));
        enterDG.addActionListener(actionListener);
        panelFour.add(enterDG);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);
        content.add(panelFour);

        frameDisplayGrade.setSize(600, 400);
        frameDisplayGrade.setLocationRelativeTo(null);
        frameDisplayGrade.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameDisplayGrade.setVisible(true);
    }

    public void exitOrSignout() {
        frameExit = new JFrame();
        Container content = frameExit.getContentPane();
        content.setLayout(new GridLayout(4, 2));
        frameExit.setTitle("Edit Quiz Options");

        panelOne = new JPanel();
        panelTwo = new JPanel();
        panelThree = new JPanel();
        panelFour = new JPanel();
        panelFive = new JPanel();
        panelSix = new JPanel();

        labelOne = new JLabel("Welcome To The Exit Options");
        panelOne.add(labelOne);

        labelTwo = new JLabel("Do You Want To Sign-Out Or Exit");
        panelTwo.add(labelTwo);

        signOut = new JButton("SignOut");
        signOut.setPreferredSize(new Dimension(250, 100));
        signOut.addActionListener(actionListener);
        exit = new JButton("Exit");
        exit.setPreferredSize(new Dimension(250, 100));
        exit.addActionListener(actionListener);
        panelThree.add(exit);
        panelThree.add(signOut);

        content.add(panelOne);
        content.add(panelTwo);
        content.add(panelThree);

        frameExit.setSize(600, 400);
        frameExit.setLocationRelativeTo(null);
        frameExit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameExit.setVisible(true);
    }

    public void error(String message) {

        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
