import java.util.List;

public interface IVote {

	/**
	  * Accept a submission from a student. Validate the submission by
	  * making sure the answers submitted are in the domain of the choices
	  * and there are no repeat answers.
	  */
	boolean submit(String uuid, List<String> submission);

	/** Print out statistics for this iClicker instance. */
	String showStats();

	/** Return the total number of submissions */
	Integer totalSubmissions();

	/** Stop accepting submissions */
	void endSubmissions();

}