import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SurveyAppTest {

    private ArrayList<Question> questions;
    private ArrayList<Response> responses;

    @BeforeEach
    void setUp() {

        // input
        String questionFileName = "example-data/survey-test-0.csv";
        String responsesFileName = "example-data/survey-test-0-responses.csv";
        questions = Loader.loadQuestions(questionFileName);
        responses = Loader.loadResponses(responsesFileName, questions);
    }


    @Test
    void calculateParticipationTest() {

        // expected
        ParticipationResult expectedResult = new ParticipationResult(0.75, 6);

        // actual
        ParticipationResult actualResult = SurveyApp.calculateParticipation(responses);

        // testing
        assertEquals(expectedResult.getParticipationCount(),
                actualResult.getParticipationCount());
        assertEquals(expectedResult.getParticipationPercentage(),
                actualResult.getParticipationPercentage());
    }

    @Test
    void calculateAverages() {

        // expected
        double[] expectedResults = {1.0, 5.0, 5.0, 2.0, 3.6, 4.6, 4.33, 5.0,
                3.5, 3.67, 1.0};
        int numRatingResults = 11;

        // actual
        double[] actualResults = SurveyApp.calculateAverages(responses,
                questions);

        // testing - for each rating question
        assertEquals(numRatingResults, actualResults.length);
        for (int i = 0; i < numRatingResults; i++) {
            assertEquals(expectedResults[i], actualResults[i], 0.01);
        }
    }

    @Test
    void numberOfQuestionsOfTypeTest() {

        // expected
        int expectedNumberRatingQuestions = 11;
        int expectedNumberSingleSelectQuestions = 8;

        // actual
        int actualNumberRatingQuestions = SurveyApp.numberOfQuestionsOfType(
                questions, QuestionType.RATING);
        int actualSingleSelectQuestions = SurveyApp.numberOfQuestionsOfType(
                questions, QuestionType.SINGLE_SELECT);

        // testing
        assertEquals(expectedNumberRatingQuestions,
                actualNumberRatingQuestions);
        assertEquals(expectedNumberSingleSelectQuestions,
                actualSingleSelectQuestions);
    }
}