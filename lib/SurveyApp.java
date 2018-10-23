import java.util.ArrayList;

/**
 * Main class, coordinates app execution.
 */
public class SurveyApp {

    private static final int NUMBER_FILES = 2;
	
	public static void main(String[] args) {
	    if (!validInput(args)) {
	        System.out.println("Two file space separated file names required.");
	        return;
        }

		// Load data into array of objects
		ArrayList<Question> surveyQuestions = Loader.loadQuestions(args[0]);
		ArrayList<Response> surveyResponses = Loader.loadResponses(args[1], surveyQuestions);

		// Perform operation on this data
        ParticipationResult participationResult = calculateParticipation();
        AverageResult averageResult = calculateAverages();

        System.out.println("Participation Results: ");
        System.out.println("Averages: ");
	}

    /**
     * Check that inputs are valid
     * @param args  array of input string filenames
     * @return  true if correct number of files are passed in
     */
	private static boolean validInput(String[] args) {
	    if (args.length == NUMBER_FILES) {
	        return true;
        }
        return false;
    }

	private static ParticipationResult calculateParticipation() {
        ParticipationResult result = null;

        return result;
    }

    private static AverageResult calculateAverages() {
        AverageResult result = null;

        return result;
    }

}