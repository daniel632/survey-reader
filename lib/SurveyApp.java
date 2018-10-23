import java.util.ArrayList;

/**
 * Main class, coordinates app execution.
 */
public class SurveyApp {

    private static final int NUMBER_FILES = 2;
	
	public static void main(String[] args) {
	    if (!validInput(args)) {
	        System.out.println("Two space separated file names required.");
	        return;
        }

        System.out.println("Running files: " + args[0] + " and " + args[1]);

		// Load data into array of objects
		ArrayList<Question> surveyQuestions = Loader.loadQuestions(args[0]);
		ArrayList<Response> surveyResponses = Loader.loadResponses(args[1],
                surveyQuestions);

		// Perform operation on this data
        ParticipationResult participationResult =
                calculateParticipation(surveyResponses);
        double[] ratingAverages = calculateAverages(surveyResponses,
                surveyQuestions);

        System.out.println("Participation percentage: "
                + (100 * participationResult.getParticipationPercentage())
                + "%");
        System.out.println("Participation count: "
                + participationResult.getParticipationCount());
        System.out.println("Average Ratings: ");
        printArray(ratingAverages);
	}

    /**
     * Check that inputs are valid
     * @param args  array of input string filenames
     * @return  true if correct number of files are passed in
     */
	private static boolean validInput(String[] args) {
	    return args.length == NUMBER_FILES;
    }

    /**
     * Calculates the participation percentage (#submitted responses / number
     * of responses)
     * @param surveyResponses   ArrayList of responses to the survey
     * @return  A result, containing a participation percentage and a
     * participation count
     */
	protected static ParticipationResult calculateParticipation(
	        ArrayList<Response> surveyResponses) {
        int submittedCount = 0;
        int count = 0;              // count of submitted / unsubmitted surveys

        for (Response response : surveyResponses) {
            if (response.getIsSubmitted()) {
                submittedCount++;
            }
            count++;
        }

        if (count == 0) {
            return null;
        }

        return new ParticipationResult((1.0 * submittedCount) / count,
                submittedCount);
    }

    /**
     * Calculates the average value for each rating question
     * @param surveyResponses   an ArrayList of survey Response objects
     * @return                  an array of containing each rating average
     */
    protected static double[] calculateAverages(
            ArrayList<Response> surveyResponses,
            ArrayList<Question> surveyQuestions) {
        if (surveyQuestions == null) {
            // then there are no rating questions to calculate an average from
            return null;
        }

        int numberOfRatingQuestions = numberOfQuestionsOfType(surveyQuestions,
                QuestionType.RATING);

        // Note: Arrays are zeroed by default
        // Average and count for each rating
        double[] ratingAverages = new double[numberOfRatingQuestions];
        int[] ratingCount = new int[numberOfRatingQuestions];

        ArrayList<Integer> ratings;

        for (Response response : surveyResponses) {

            // If this response is unsubmitted, don't consider it
            if (!response.getIsSubmitted()) {
                continue;
            }
            ratings = response.getRatings();

            for (int i = 0; i < numberOfRatingQuestions; i++) {
                if (ratings.get(i) == Response.EMPTY_RATING) {
                    continue;
                }

                ratingAverages[i] += ratings.get(i);
                ratingCount[i]++;
            }
        }

        // Converting rating sums to averages
        for (int i = 0; i < numberOfRatingQuestions; i++) {
            if (ratingCount[i] == 0) {
                continue;
            }
            ratingAverages[i] = ratingAverages[i] / ratingCount[i];
        }

        return ratingAverages;
    }

    /**
     * Calculates the number of questions of a particular type
     * @param surveyQuestions   Arraylist of survey questions
     * @param type              the type of question being counted
     * @return                  number of questions of type
     */
    protected static int numberOfQuestionsOfType(
            ArrayList<Question> surveyQuestions, QuestionType type) {
        int number = 0;
        for (Question question : surveyQuestions) {
            if (question.getType().equals(type)) {
                number++;
            }
        }
        return number;
    }

    /**
     * Print an array of double values
     * @param values    array of doubles
     */
    private static void printArray(double[] values) {
        if (values == null) {
            System.out.println("Array is empty.");
            return;
        }
        int ratingNumber = 1;
        for (double value : values) {
            System.out.println(ratingNumber + ": " + value);
            ratingNumber++;
        }
    }

}