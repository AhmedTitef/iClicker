import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
  * I designed this application while keeping in mind that I can switch out the
  *	driver with something more console driven, where you can enter all of these
  *	variables in manually, then each student can submit their answer. I didn't
  *	have the time to make this alternate Driver but if you think about this while
  *	looking over my code you'll have a better understanding of why I did some of
  *	this differently.
 */

public class SimulationDriver {

    public static void main(String[] args) {

        List<String> singleChoices = new ArrayList<String>();
        List<String> multiChoices = new ArrayList<String>();
        List<String> answer = new ArrayList<String>();
        List<String> answers = new ArrayList<String>();

        singleChoices.add("True");
        singleChoices.add("False");
        answer.add(singleChoices.get(0));

        multiChoices.add("Yes");
        multiChoices.add("No");
        multiChoices.add("Maybe");
        answers.add(multiChoices.get(0));
        answers.add(multiChoices.get(2));

        SimulationRun("SingleChoice", "Is OOP fun?", singleChoices, answer, 25);

        // Throws an exception
        //SimulationRun("SingleChoice", "Is OOP fun?", singleChoices, answers, 25);
        SimulationRun("MultipleChoice", "Is this an awesome program?", multiChoices, answers, 45);
    }

    /** Start a simulation, this is a wrapper so you can have multiple runs. */
    private static void SimulationRun(String qType,
            String q, List<String> choices, List<String> answers, Integer numStudents) {
        final Question question;
        final IVote iClicker;
        final Student[] students = new Student[numStudents];

        // 1)
        // create a question type and configure the answers
        switch(qType) {
            case "MultipleChoice":
                question = new MultipleChoiceQuestion(q, choices, answers);
                break;
            default:
                question = new SingleChoiceQuestion(q, choices, answers);
                break;
        }

        // 2)
        // configure IClickerService
        iClicker = new IClickerService(question);

        // 3 and 4)
        // create students, generate an answer, then submit the answer
        for (Integer i = 0; i < students.length; i++) {
            students[i] = new Student();
            students[i].enterAnswers(randGenAnswers(choices, qType));
            // submit
            iClicker.submit(students[i].getUuid(), students[i].getAnswers());
        }
        if (iClicker.totalSubmissions() != numStudents) {
            System.err.println("Number of submissions is " + iClicker.totalSubmissions().toString());
        }
        System.out.println("Before submissions are over.");
        System.out.println(iClicker.showStats());

        // for some students submit a 2nd answer
        for (Integer i = 0; i < students.length; i += 5) {
            students[i].enterAnswers(randGenAnswers(choices, qType));
            // submit
            iClicker.submit(students[i].getUuid(), students[i].getAnswers());
        }
        if (iClicker.totalSubmissions() != numStudents) {
            System.err.println("Number of submissions is " + iClicker.totalSubmissions().toString());
        }

        // end submissions
        iClicker.endSubmissions();

        // 5)
        // show statistics
        System.out.println("After answers have been checked.");
        System.out.println(iClicker.showStats());
    }

    /**
      * Randomly generate answers, duplicate answers can be in the returning array.
      * Does not allow answers outside of the available choices.
      */
    private static List<String> randGenAnswers(List<String> choices, String type) {
        Integer numAnswers = 1;
        final Random rand = new Random();
        ArrayList<String> answers = new ArrayList<String>();
        if (type == "MultipleChoice") {
            numAnswers = rand.nextInt(choices.size() - 1) + 1;
        }
        for (Integer i = 0; i < numAnswers; i++) {
            answers.add(choices.get(rand.nextInt(choices.size())));
        }
        return answers;
    }
};
