# Project05
CS 180 Project 5
# Project 05

Grayson Nocera: Submitted Vocareum 

Alex Lee: Submitted Report, Presentations

Compilation:
To compile and run the program, open up the terminal. Then, run the following command to compile the program: "javac GUI.java" (which is the main for this project) and “javac Server.java” After the program has compiled, run "Server.java" and “GUI.java”. While this program is being run, a series of menus will come up to guide the user through our program.

Description:
Project 5 is the extension of project 4 (learning management system quiz). In project 5 we incorporated synchronizations, GUI, and complex GUI to make the learning management system organized in panels. The way the program is being run is quite similar to the previous project since we are just putting prompts into panels(using JComponent). However, one major difference is we use servers to give access (where checking happens) to the whole program such as where users have to set up a specific account or log in (with unique passwords and usernames) to the program. After being successfully logged in there will be panels that are “hyperlinked” which contain options for teachers and students where students will be able to take quizzes and view submissions and grades. Teachers will be able to create a course, edit a course, and delete a course. Within a course that has been created, they will be able to create a quiz, edit the quiz and they can delete quizzes.


GUI.java:
GUI. java is the main class of the program. We used Jcomponents to organize prompts (implemented)  for the programs. We had a decent amount of panels combined for most of the panels we tried to have identical “setLayout” Gridlayouts (3,2) to (5,2) which makes the program neat. However, the dimensions “setPreferredSize” (Button Sizes) are different at times, because there are different components that are included in the panels ( for space limits). The “actionPerformed” method is where actual programs get implemented to successfully run. In order for the panels to appear in order and appear “a panel” each time we had to dispose of the previous panels (It is necessary because even after going forward the panel will still be on it). Furthermore, all the try and catch happen under “e.getSource” showing errors with JoptionPane to avoid all invalid input. Lastly, just like previous main class Teachers can create/join courses, create quizzes, edit quizzes, delete quizzes, and view and grade submissions. Students will be able to take quizzes and view the grades of the submissions. We also have a createAccount method to verify whether teachers and students have a unique username (if not it will be denied.). The testing we have done for the main class is by running the program multiple times with every different possible option to make sure that the program does not crash.

MainServer.java:
MainServer.java is where all the “data persistence”  happens. After connecting GUI.java and Server.java using server sockets we store users' information such as account, password, and username in Arraylists. By storing those data ( with Bufferedreader and PrintWriter) the program will be able to Create accounts (Where we write the data) for specific roles (Teacher & Students), Deleting accounts (Reading the data that has been saved) based on the information we saved from created accounts, and logging in with unique roles, usernames, and passwords. Moreover, the “containUser” this variable is to determine if the user is in the list of users. So within the Server. java has to re-verify everything and make sure GUI and server are successfully linked.


Manager.java:
Manager.java as it has been mentioned in the last project is a class that organizes methods so we could call/implement them when we are working on GUIs. “Organized methods?” : Firstly, in a public manager we store courses and users(accounts) in arraylists by using “getters & setters” for users and courses. Secondly, the “User” & “isUser” method where verifies whether the username and password overlap (this will be taken to the Arraylists). Thirdly, we have “addUser”,”removeUser”, “addCourse” and “removeCourse” to basically add and remove the user & course from the lists that have been written. Fourthly, we have “writeToFile” method which prints the data in “manager” to the file(data.txt). Lastly, in the “readFromFile” there are two parts to the reading file when we reach a new course and new users where data major data persistence happens which reads the name of the user/teacher, username, password and gives permissions to get logged in later. There will be an error if the file can’t be read from the file or written.


__________________________________________________________________________________________________________________________________________________________

Course.java:
Course. java helps create courses when the teacher tries to add a course to their account. It gets the course(setter/getter) name, and within the course, we will be able to create (setter/getter) quizzes and remove quizzes for specific courses. 

Manager.java:
Manager.java manages all the data such as a list of courses( getter/setter ), a list of students, as well a list of teachers. It has a function to determine if a course exists (“containsCourse”), so it could add users (“setUsers”, “addUsers”) and the course (“addCourse”) to the management system. This class doesn’t seem to do much, however, once we get to project 5 this class will be used quite a lot to organize the code. 


Question.java
Question.java is used to have all the questions for a quiz object. This will make it easy to randomize the questions and possibly the answers to each quiz so we can have more variety. This will save the answer to the question with the choices in an ArrayList making it so they can have as many questions as they want. We split it up to make it so the options of how we do quizzes and questions can be open-ended and easier. In this, you can add a sing choice or change a single choice as well to make the options even more open-ended. 

Quiz.java:
Quiz.java is built to be able to make all of the quizzes we need. This class works with the question class to build a complete quiz that a teacher can create, edit, remove and the students can take. The quiz will be able to randomize in this class as well making it so the students will have different quizzes. Once a student is taking a quiz it will get the question so they can take each question at a time. The quiz class also has all the quiz questions in an array list so they are easy to call.

Teacher.java:
Teacher.java extends the user class. ( Meaning student class is a subclass of “User.java”). In Teacher.java, since only teachers are authorized to create the class, (getter/setter) of the course is being used. Teachers also should be able to add quizzes to a file. We have made an “addFileQuiz” method to store the quiz, and “addQuiz” method adds(creates) the quiz. Firstly, for editing quiz, we separated 4 parts “modifyQuizName” method (modifies the name of the quiz), “modifyQuizPrompt” (modifies quizzes questions), “modifyQuizChoices”(modifies the lists of quiz options for the answers.), and  “modifyQuizAnswer”(modifies the answer of the quiz). Secondly, we made “removeQuiz” to remove the quiz itself as a package. Thirdly, made the ArrayList method “checkStudentQuiz”, we made the student name equal to a filename, get the file and read it until the quiz is being found, finds when the quiz ends, and adds each line of the quiz to an ArrayList, and which returns an ArrayList that can then be printed to show someone's quiz. Fourthly, we have made a “printQuestionQuiz” method, this will send each question to the main grade and it calls the needed quiz version. In this method the loop helps check to see if the question is starting in the right spot, check if the next questions come up, and add all the lines for the question to ArrayList. Lastly, we have “getQuizResult”, with the grades for each question this will print the graded quiz to a file. It starts with the first question and makes a variable to add for a final grade, it calls for the whole quiz ungraded to make a file with the name given. And it starts writing graded quizzes (uses a loop to look through the whole quiz).

Student.java:
Student.java extends user class, ( Meaning student class is a subclass of “User.java”). This student class has lesser parts to deal with since students are only allowed to take quizzes and view their submission grades. 


User.java:
User.java stores the basic information of the user trying to create an account, such as the full name of the user (setter/getter), the username of the users (setter/getter), and password of the users (setter/getter). These getters and setters are being used to get users logged in and signed up properly for their roles. So, after being verified they would be used to get access to their own management system. User.java is the superclass of  Teacher.java and Students java.
