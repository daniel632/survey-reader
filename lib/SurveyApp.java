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

        System.out.println("Running files: " + args[0] + " and " + args[1]);

		// Load data into array of objects
		ArrayList<Question> surveyQuestions = Loader.loadQuestions(args[0]);
		ArrayList<Response> surveyResponses = Loader.loadResponses(args[1], surveyQuestions);

		// Perform operation on this data
        ParticipationResult participationResult = calculateParticipation(surveyResponses);
        double[] ratingAverages = calculateAverages(surveyResponses, surveyQuestions);

        System.out.println("Participation percentage: "
                + participationResult.getParticipationPercentage());
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
	    if (args.length == NUMBER_FILES) {
	        return true;
        }
        return false;
    }

    /**
     * Calculates the participation percentage (#submitted responses / number of responses)
     * @param surveyResponses
     * @return
     */
	private static ParticipationResult calculateParticipation(ArrayList<Response> surveyResponses) {
        int submittedCount = 0;
        int count = 0;              // count of submitted / unsubmitted surveys

        for (Response response : surveyResponses) {
            if (response != null) {
                submittedCount++;
            }
            count++;
        }

        if (count == 0) {
            return null;
        }

        return new ParticipationResult((1.0 * submittedCount) / count, submittedCount);
    }

    /**
     * Calculates the average value for each rating question
     * @param surveyResponses
     * @return
     */
    private static double[] calculateAverages(ArrayList<Response> surveyResponses,
                                                   ArrayList<Question> surveyQuestions) {
        if (surveyQuestions == null) {
            // then there are no rating questions to calculate an average from
            return null;
        }

        int numberOfRatingQuestions = numberOfQuestionsOfType(surveyQuestions, SurveyType.RATING);
        double[] ratingAverages = new double[numberOfRatingQuestions];    // zeroed by default

        // Number of ratings for each question
        int[] ratingCount = new int[numberOfRatingQuestions];  // zeroed by default

        ArrayList<Integer> ratings = null;

        for (Response response : surveyResponses) {

            // TODO - find replacement for null unsubmitted check, ie a  boolean
            if (response == null) {
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

        for (int i = 0; i < numberOfRatingQuestions; i++) {
            if (ratingCount[i] == 0) {
                continue;
            }
            ratingAverages[i] = ratingAverages[i] / ratingCount[i];
        }

        return ratingAverages;
    }

    private static int numberOfQuestionsOfType(ArrayList<Question> surveyQuestions, SurveyType type) {
        int number = 0;
        for (Question question : surveyQuestions) {
            if (question.getType().equals(type)) {
                number++;
            }
        }
        return number;
    }

    // TODO - make polymorphic
    private static void printArray(double[] values) {
        if (values == null) {
            System.out.println("Array is empty.");
            return;
        }
        int ratingNumber = 0;
        for (double value : values) {
            System.out.println(ratingNumber + ": " + value);
            ratingNumber++;
        }
    }

}