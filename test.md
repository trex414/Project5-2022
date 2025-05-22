Test 1: User log in
Steps:

1.User launches application.
2.User selects the username textbox.
3.User enters username via the keyboard.
4.User selects the password textbox.
5.User selects the "Log in" button.
Expected result: Application verifies the user's username and password and loads their homepage automatically.

Test Status: Passed. 

Test one:

Steps:
User launches application.
User selects the "Set up a Teacher account" button.
User type in "Jason" in first box, "jason" in second box, and "123" in third box.
User selects the "Log-In" button.
User selects the "I am a teacher" and type in "Jason" in the first box and type in "456" in third box

Expected result: Error message "No user with that username and password!"
Test Status: Passed.

Test Two:

Steps:
User launches application.
User selects the "Set up a Teacher account" button.
User type in "Jason" in first box, "jason" in second box, and "123" in third box.
User selects the "Log-In" button.
User selects the "I am a teacher" and type in "jason" in the first box and type in "123" in the second box
User selects the "Add Course" button and "Enter" button
User type in "CS180" in the box and select "Enter" button
User selects "Log in" button and tpye in "jason" in the first box and type in "123" in the second box and select "Enter" button
User type in "CS180" and select "Enter" button
User type in "MA303" and select "Enter" button
User select "Set up a Student account" button 
User type in "Mason" in first box, "Mason" in second box, and "123" in third box.
User selects the "Log-In" button.
User selects the "I am a teacher" and type in "jason" in the first box and type in "123" in the second box
User selects the "Join Course" button and "Enter" button
User type in "MA303" and select "Enter" button
User select "Add quiz" button and "Enter" button
User select "Add quiz without a File" button and "Enter" button
User type in "quiz1" in the first box and "2" in the second box and select "Enter" button
User type in "1+1", "1", "2", "3", "4", "2" and select "Enter" button
User type in "1*5", "2", "4", "5", "1", "3" nad select "Enter" button
User select "Exit" button
User select "Signout" button
User selects the "Log-In" button.
User selects the "I am a student" and type in "Mason" in the first box and type in "123" in the second box and select 'Enter' button
User type in "MA303" and "Enter" button
User type in "quiz" and "Enter" button
User selects "2.Manual" and "Enter" button
User selects "1" and "Enter" button
User select "Exit" button
User select "Signout" button


Expected result: when the user creates duplicate course name, there is an error message "course already exists" and ask the user use another one.
When the student complete the quiz, there is a message shows when the student finished it.
Test Status: Passed.

Test Three:

User launches application.
User selects the "Set up a Teacher account" button.
User types in "Lisa", "lisa", "111"
User selects the "Log in" button
User selects the "I am a teacher" and types in "lisa" and "111"
User selects "Add course" and "Enter" button
User types in "stats"
User selects "Join course" and "Enter" button
User types in "stats" in textbox
User selects "Add quiz" and "Enter" button
User selects "Add quiz without a file" button
User types in "quiz" and "1" in textbox
User types in "2*6", "12", "10", "0", "2" and "1" in textbox
User selects "Edit quiz" button
User selects "Quiz Name" button
User types in "quiz" and "statsquiz"
User selects "Exit" button
User selects "Sign out"
User selects "Set up a Student account" button.
User types in "Jenny", "Jenny", and "12345" in textbox
User selects "Log in" button
User selects the "I am a student" and types in "Jenny" and "12345"
User types in "stats" in textbox
User selects "Take quiz" button
User types in "statsquiz" in textbox
User selects "2.Manual" button
User selects "1" button
User selects "Exit" button
User selects "Signout" button

Expected result: "Quiz complete" massage, after signout, back to the main panel.
Test Status: Passed.

Testcase Four:

User launches application.
User selects the "Set up a Teacher account" button.
User types in "Lisa", "lisa", "111"
User selects the "Set up a Teacher account" button.
User types in "Lisa", "lisa", "111"

Expected result: Error massage, "Enter your name, username, password!"
Test Status: Passed.

Testcase Five:

User launches application.
User selects "Set up a Student account" button.
User types in "Jenny", "Jenny", and "12345" in textbox
User selects the "Set up a Teacher account" button.
User types in "Lisa", "lisa", "111"
User selects the "Log in" button
User selects the "I am a teacher" and types in "lisa" and "111"
User selects "Add course" and "Enter" button
User types in "math" button
User selects "Join course" and "Enter" button
User types in "math" button
User selects "Add quiz" and "Enter" button
User selects "Add quiz without a file" button
User types in "quizOne" and "1" in textbox
User types in "5*6", "10", "20", "30", "40" and "3" in textbox
User selects "delete quiz" button
User selects "quinOne" button

Expected result: message "Quiz removed!"
Test Status: Passed.


Testcase Six:

User launches application.
User selects the "Set up a Teacher account" button.
User types in "Coco", "Coco", "111"
User selects the "Log in" button
User selects the "I am a teacher" and types in "lisa" and "111"
User selects "Add course" and "Enter" button
User types in "art" button
User selects "Join course" and "Enter" button
User types in "art" button
User selects "Add quiz" and "Enter" button
User selects "Add quiz without a file" button
User types in "quiz" and "1" in textbox
User types in "3+4", "1", "7", "2", "3" and "2" in textbox
User selects "Set up a Student account" button.
User types in "q", "q", and "1" in textbox
User selects "Log in" button
User selects the "I am a student" and types in "q" and "1"
User types in "art" in textbox
User selects "Take quiz" button
User types in "quiz" in textbox
User selects "2.Manual" button
User selects "1" button
User selects "Exit" button
User selects "Signout" button


Expected result: "Quiz complete" massage
Test Status: Passed.

Testcase Seven:

User launches application.
User selects the "Set up a Teacher account" button.
User type in "Jason" in first box, "jason" in second box, and "123" in third box.
User selects the "Delete account" button.
User selects the "I am a teacher" and type in "jason" in the first box and type in "123" in the second box
User selects the "Log in" button and "Enter" button
User type in "jason", "123" in the box and select "Enter" button

Expected result: Error message as we already delete the account, "NO user with that username and password!"
Test Status: Passed.

Testcase Eight:
User launches application.
User selects the "Set up a Teacher account" button.
User types in "Mary", "Mary", "111"
User selects the "Log in" button
User selects the "I am a teacher" and types in "Mary" and "111"
User selects "Add course" and "Enter" button
User selects "Join Course" and "Enter" button
User types in "art" and "Enter" button
User selects "Exit" button
User selects the "Log in" button
User selects the "I am a teacher" and types in "Mary" and "111"
user selects "Remove Course" and "Enter" button
User types in "art"
user selects "Join Course" and "Enter" button

Expected result: When the user join course at the first time, there is a quiz panel. When the user join course at the second time, there is a error message,"Course does not exist!", since the user already delete that course.
Test Status: Passed.
