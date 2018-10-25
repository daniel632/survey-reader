import java.util.ArrayList;

/**
 * Main class, coordinates app execution.
 */
public class SurveyApp {

    private static final int NUMBER_FILES = 2;
    private static final int NUMBER_RATING_VALUES = 5;
    private static final int MIN_RATING_VALUE = 1;
    private static final int MAX_RATING_VALUE = 5;
	
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
        int[][] ratingDistributionResults =
                calculateDistribution(surveyResponses, surveyQuestions);

        // TODO - verify that participationResult is not null

        System.out.println("Participation percentage: "
                + (100 * participationResult.getParticipationPercentage())
                + "%");

        System.out.println("Participation count: "
                + participationResult.getParticipationCount());

        System.out.println("Average Ratings: ");
        printRatings(ratingAverages);

        System.out.println("Rating Distributions (Per Rating Question):");
        printRatingDistributions(ratingDistributionResults);
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
     * For each rating question, find the distribution of rating values.
     * Note: It may be desirable to store this distribution data somewhere (i.e. in the question object)
     * @param surveyResponses
     * @param surveyQuestions
     * @return 2d array, of dimensions: nukber of dimensions * number of rating values
     */
    private static int[][] calculateDistribution(ArrayList<Response> surveyResponses,
                                                 ArrayList<Question> surveyQuestions) {
        int numberOfRatingQuestions = numberOfQuestionsOfType(surveyQuestions,
                QuestionType.RATING);

        // TODO - make constant
        int[][] distributionResults = new int[numberOfRatingQuestions][NUMBER_RATING_VALUES];

        ArrayList<Integer> ratings;

        int responseNumber = 0;
        for (Response response : surveyResponses) {

            // If this response is unsubmitted, don't consider it
            if (!response.getIsSubmitted()) {
                continue;
            }
            ratings = response.getRatings();

            // For each rating question
            for (int ratingQuestionNumber = 0;
                 ratingQuestionNumber < numberOfRatingQuestions;
                 ratingQuestionNumber++) {

                // If this rating question is unanswered, don't consider it
                if (ratings.get(ratingQuestionNumber) == Response.EMPTY_RATING) {
                    continue;
                }

                int ratingValue =
                        response.getRatings().get(ratingQuestionNumber);

                // if the rating value is invalid, don't consider it
                if (ratingValue < MIN_RATING_VALUE || ratingValue > MAX_RATING_VALUE) {
                    continue;
                }

                // -1 since array's are 0-indexed
                distributionResults[ratingQuestionNumber][ratingValue - 1]++;
            }

            responseNumber++;
        }

	    return distributionResults;
    }

    /**
     * Calculates the average value for each rating question
     * @param surveyResponses   an ArrayList of survey Response objects
     * @param surveyQuestions   an ArrayList of survey Question objects
     * @return                  an array containing each rating average
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
     * Print an array of (double typed) rating values
     * @param values    array of doubles
     */
    private static void printRatings(double[] values) {
        if (values == null) {
            System.out.println("Array is empty.");
            return;
        }
        int ratingNumber = 1;
        for (double value : values) {
            System.out.println("RatingQ #" + ratingNumber + " : " + value);
            ratingNumber++;
        }
    }

    /**
     * For each rating question, print the distribution of ratings
     * @param ratingDistributionResults
     */
    private static void printRatingDistributions(int[][] ratingDistributionResults) {
        String output;
        for (int ratingNumber = 0;
             ratingNumber < ratingDistributionResults.length; ratingNumber++) {
            output = "RatingQ #" + (ratingNumber + 1) + " : ";

            for (int value = 0; value < MAX_RATING_VALUE; value++) {
                output += ratingDistributionResults[ratingNumber][value] + " ";
            }

            System.out.println(output);
        }
    }


}