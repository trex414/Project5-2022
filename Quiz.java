import java.util.ArrayList;
import java.util.Collections;

public class Quiz {

    private String name;
    private ArrayList<Question> questions;
    private int size; // holds number of questions
    private boolean isFinished;
    private boolean isGraded;

    public Quiz(String name, ArrayList<Question> questions) {
        this.name = name;
        this.questions = questions;
        this.size = questions.size();
        this.isFinished = false;
        this.isGraded = false;
    }

    public Quiz(String name, int size) {
        this.name = name;
        this.size = size;
        this.questions = new ArrayList<Question>(size);
        this.isFinished = false;
        this.isGraded = false;
    }

    public Quiz() {
        this.name = "";
        this.questions = new ArrayList<Question>();
        this.size = 0;
        this.isFinished = false;
        this.isGraded = false;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Question> getQuestions() {
        return this.questions;
    }

    public int getSize() {
        return questions.size();
    }

    public boolean getFinished() {
        return this.isFinished;
    }

    public boolean getGraded() {
        return this.isGraded;
    }

    // Allows user to get a question based on the prompt
    public Question getQuestion(String prompt) {

        Question q = null;

        for (int i = 0; i < questions.size(); i++) {
            if (prompt.equals(questions.get(i).getPrompt())) {
                q = questions.get(i);
                break;
            }
        }

        return q;
    }

    public Question getQuestion(int i) {

        Question ques = null;

        try {

            ques = questions.get(i);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Could not get question\n");
        }

        return ques;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public void setGraded(boolean graded) {
        this.isGraded = graded;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public void removeQuestion(Question q) {
        questions.remove(q);
    }

    public boolean containsQuestion(String prompt) {

        boolean doesContain = false;

        for (int i = 0; i < questions.size(); i++) {

            // If the prompt is a question in the quiz
            if (questions.get(i).getPrompt().equals(prompt)) {
                doesContain = true;
                break;
            }
        }

        return doesContain;
    }

    // Randomize the list of questions
    public void randomizeQuestions() {
        Collections.shuffle(questions);
    }

    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append("Quiz: " + name + "\n");
        for (int i = 0; i < questions.size(); i++) {
            s.append(questions.get(i).toString() + "\n");
        }

        return s.toString();
    }
}
