import java.util.ArrayList;
import java.util.Collections;

public class Question {

    private String prompt;
    private String answer;
    private ArrayList<String> choices;

    public Question(String prompt, String answer, ArrayList<String> choices) {
        this.prompt = prompt;
        this.answer = answer;
        this.choices = choices;
    }

    public Question() {
        this.prompt = "";
        this.answer = "";
        this.choices = new ArrayList<String>();
    }

    public String getPrompt() {
        return this.prompt;
    }
    
    public String getAnswer() {
        return this.answer;
    }

    public ArrayList<String> getChoices() {
        return this.choices;
    }

    public String getChoice(int i) {

        String choice = "";
        try {
            choice = choices.get(i);
        } catch (IndexOutOfBoundsException e) {}
        return choice;
    }

    public void removeChoices() {
        choices = new ArrayList<String>();
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswer(int i) {
        try {
            answer = choices.get(i);
        } catch (IndexOutOfBoundsException e) {}
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    // Takes as input the old choice and the new choice that it should be replaced with
    public void setChoice(String oldChoice, String newChoice) {

        // Iterate through choices
        for (int i = 0; i < choices.size(); i++) {

            if (oldChoice.equals(choices.get(i))) {
                choices.set(i, newChoice);
            }
        }
    }

    // Add a choice to the list of choices
    public void addChoice(String choice) {
        
        if (!choice.isBlank()) {
            choices.add(choice);
        }
    }

    // Randomize the list of choices
    public void randomizeChoices() {
        Collections.shuffle(choices);
    }

    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append("Question: " + prompt + "\n");
        for (int i = 0; i < choices.size(); i++) {
            s.append(choices.get(i) + "\n");
        }
        s.append("Answer: " + answer + "\n");

        return s.toString();
    }
}
