import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
  * IClickerService stores submissions and uses the question to
  * validate submissions and show statistics for question choices.
  * submissions_end tells the instance when we have stopped collecting
  * submissions and num_correct keeps track of how many submissions
  * were correct, used for statistics.
  */

public class IClickerService implements IVote {

    private Hashtable<String, List<String>> submissions;
    private Question description;
    private boolean submissionsEnd;
    private Integer numCorrect;

    public IClickerService(Question description) {
        this.description = description;
        this.submissions = new Hashtable<String, List<String>>();
        this.submissionsEnd = false;
        this.numCorrect = 0;
    }

    /* (non-Javadoc)
	 * @see edu.csupomona.cs356.iclicker.IVote#submit(java.lang.String, java.util.List)
	 */
    @Override
	public boolean submit(String uuid, List<String> submission) {
        if (this.submissionsEnd) {
            return false;
        }

        // check if the submission is valid
        if (submission == null) {
            return false;
        }

        final List<String> uniqueSubmission = makeUnique(submission);
        final List<String> validSubmission = new ArrayList<String>();
        for (String sub : uniqueSubmission) {
            if (this.description.inChoices(sub)) {
                validSubmission.add(sub);
            }
        }
        this.submissions.put(uuid, validSubmission);
        return true;
    }

    /** Only allow one of the same answer. no I, I, I, I, I submissions. */
    private List<String> makeUnique(List<String> submission) {
        final Set<String> temp = new HashSet<String>(submission);
        final List<String> uq = new ArrayList<String>();
        uq.addAll(temp);
        return uq;
    }

    /* (non-Javadoc)
	 * @see edu.csupomona.cs356.iclicker.IVote#showStats()
	 */
    @Override
	public String showStats() {
        checkAnswers();
        String stats = "Question:\n";
        stats += this.description.getQuestion();
        stats += "\n-----------------";
        stats += "\nAnswer Statistics\n";
        stats += "-----------------\n";

        List<String> choices = this.description.getChoices();
        for (String choice : choices) {
            Integer numChoice = 0;
            for (String uuid : submissions.keySet()) {
                if (this.submissions.get(uuid).contains(choice)) {
                    numChoice++;
                }
            }
            stats += choice + "\t\t\t" + numChoice.toString() + "\n";
        }

        stats += "-----------------\n";
        stats += "Total Submissions: " + this.totalSubmissions().toString();
        stats += "\nTotal Correct: " + this.numCorrect;
        stats += "\n\n";
        return stats;
    }

    /* (non-Javadoc)
	 * @see edu.csupomona.cs356.iclicker.IVote#totalSubmissions()
	 */
    @Override
	public Integer totalSubmissions() {
        return this.submissions.size();
    }

    /* (non-Javadoc)
	 * @see edu.csupomona.cs356.iclicker.IVote#endSubmissions()
	 */
    @Override
	public void endSubmissions() {
        this.submissionsEnd = true;
    }

    /** Allow answers to be checked only if submissions have ended */
    private void checkAnswers() {
        if (!this.submissionsEnd) {
            return;
        }
        // reset
        this.numCorrect = 0;
        for (Entry<String, List<String>> entry : submissions.entrySet()) {
            final String check = description.checkA(entry.getValue());
            if (check.compareTo("Your answer is correct!") == 0) {
                this.numCorrect++;
            }
        }
    }
}
