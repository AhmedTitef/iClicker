import java.util.List;

/**
  * A student has a uuid, and an answer. To make things simple my uuid
  * is just a number which is derived from count which keeps track
  * of the number of instances created. A student also has answers.
  * As a student you can enter answers, submit answers, and check
  * answers.
  */

public class Student {

    private static int count = 0;

    private String uuid;
    private List<String> answers;

    public Student() {
        count += 1;
        this.uuid = "" + count;
    }

    public String getUuid() {
        return uuid;
    }

    /** Store generated answers (can be random or manually input) */
    public boolean enterAnswers(List<String> input) {
        this.answers = input;
        return true;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
