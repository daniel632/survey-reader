import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Loader {

    /**
     * Store each survey question.
     * @param surveyFile
     * @return
     */
    public static ArrayList<SurveyQuestion> loadSurvey(String surveyFile) {
        ArrayList<SurveyQuestion> questions = new ArrayList<>();


        return questions;
    }


    /**
     * Store all (sets of) responses for a given survey.
     * @param responsesFile
     * @return
     */
    public static ArrayList<Response> loadResponses(String responsesFile) {
        ArrayList<Response> responses = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(responsesFile))) {
            String line;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return responses;
    }
}