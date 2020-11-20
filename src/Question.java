import java.util.List;

/**
  * Abstract class for Single/Multi choice questions
  * Stores the question in `q`, all the available answer choices
  * in `choices`, and the correct choices in `correct`
  */

public abstract class Question {

    protected String description;
    protected List<String> choices;
    protected List<String> correct;

    public Question(String description, List<String> choices, List<String> correct) {
        this.description = description;
        this.choices = choices;
        this.correct = correct;
    }

    public String getQuestion() {
        return this.description;
    }

    public List<String> getChoices() {
        return this.choices;
    }

    /** Make sure a choice is valid */
    public boolean inChoices(String submission) {
        return this.choices.contains(submission);
    }

    /** A prototype method for checking answer(s) */
    public abstract String checkA(List<String> submittedAnswers);
}
