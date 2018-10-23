import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Loader {

    // Used to find type of a question
    private static final int STRING_NOT_FOUND = -1;

    // For use in survey question reading
    private static final String TYPE_FIELD = "type";

    // Index of first response per line
    private static final int FIRST_RESPONSE_INDEX = 3;

    /**
     * Store each survey question.
     * @param surveyQuestionsFile identifier for survey question csv file
     * @return arraylist of survey question objects
     */
    public static ArrayList<Question> loadQuestions(
            String surveyQuestionsFile) {
        ArrayList<Question> questions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(
                surveyQuestionsFile))) {
            String line;
            String[] values;
            String[] head;

            // Parsing the header line, this indicates the ordering of the
            line = br.readLine();
            if (line == null) {
                // empty file
                return null;
            }
            head = line.split(",");

            // TODO - can store 'theme' and 'text' of each question as well
            int typeIndex = indexOf(TYPE_FIELD, head);

            // Reading order is dynamic, e.g. it may be theme-type-text or
            // type-theme-text
            // For now, only storing the type of each question
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                questions.add(new Question(values[typeIndex]));
            }

        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return questions;
    }

    /**
     * Store all (sets of) responses for a given survey.
     * @param responsesFile identifier for csv file of survey responses
     * @return arraylist of response objects
     */
    public static ArrayList<Response> loadResponses(String responsesFile,
                                                    ArrayList<Question> questions) {
        ArrayList<Response> responses = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(responsesFile))) {
            String line;
            String[] values;
            Response response;

            // Reading each line from response file
            while ((line = br.readLine()) != null) {
                values = line.split(",");

                // If the response is valid, add it
                response = parseCSVResponseLine(values, questions);
                if (response != null) {
                    responses.add(response);
                }
            }

        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return responses;
    }

    /**
     * Take an array of csv values (of known structure) and return a Response
     * @param values    a single line of values from the responses file
     * @param questions the list of survey question objects
     * @return          a Response object
     */
    public static Response parseCSVResponseLine(String[] values,
                                                ArrayList<Question> questions){
        // Length less than this means no date, implies no survey
        if (values == null) {
            return null;
        }

        Response response = new Response();

        // (Two checks) If no submission dat included, response if invalid
        if (values.length < FIRST_RESPONSE_INDEX) {
            response.setIsSubmitted(false);
            return response;
        }

        // Currently not storing: email, employee ID, submitted at timestamp

        String submittedAtDate = values[2];
        if (submittedAtDate.isEmpty()) {
            response.setIsSubmitted(false);
            return response;
        }

        // Reading the rest of the line (the survey responses)
        // 3 is the index of first question in the response csv line
        for (int i = FIRST_RESPONSE_INDEX; i < values.length; i++) {

            // Type is either rating or singleselect
            if (questions.get(i - FIRST_RESPONSE_INDEX).getType() ==
                    QuestionType.RATING) {
                response.addRating(values[i]);
            } else if (questions.get(i - FIRST_RESPONSE_INDEX).getType() ==
                    QuestionType.SINGLE_SELECT) {
                response.addSingleSelect(values[i]);
            }
        }

        return response;
    }

    /**
     * Find the (first) index of a string in an array of strings
     * @param string    string to be located
     * @param strings   array of strings
     * @return          the array index of the string to be found
     */
    private static int indexOf(String string, String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            if (string.equals(strings[i])) {
                return i;
            }
        }
        return STRING_NOT_FOUND;
    }
}