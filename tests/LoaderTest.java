import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoaderTest {

    @Test
    void loadQuestionsTest() {

        // inputs
        String fileName = "example-data/survey-2.csv";

        // expected output
        Question[] expectedQuestions = {
                new Question(Question.RATING_QUESTION),
                new Question(Question.RATING_QUESTION),
                new Question(Question.RATING_QUESTION),
                new Question(Question.RATING_QUESTION),
                new Question(Question.SINGLE_QUESTION)};
        int numberQuestions = 5;

        // output
        ArrayList<Question> actualQuestions = Loader.loadQuestions(fileName);

        // testing (could use a for loop)
        assertEquals(numberQuestions, actualQuestions.size());
        assertEquals(expectedQuestions[0].getType(),
                actualQuestions.get(0).getType());
        assertEquals(expectedQuestions[1].getType(),
                actualQuestions.get(1).getType());
        assertEquals(expectedQuestions[2].getType(),
                actualQuestions.get(2).getType());
        assertEquals(expectedQuestions[3].getType(),
                actualQuestions.get(3).getType());
        assertEquals(expectedQuestions[4].getType(),
                actualQuestions.get(4).getType());
    }

    @Test
    void loadResponsesTest() {

        // inputs
        String questionFileName = "example-data/survey-2.csv";
        String responsesFileName = "example-data/survey-2-responses.csv";
        ArrayList<Question> questions = Loader.loadQuestions(questionFileName);

        // expected output - for the first two responses only
        Response response0 = new Response();
        response0.addRating("5");
        response0.addRating("5");
        response0.addRating("5");
        response0.addRating("4");
        response0.addSingleSelect("Sally");

        Response response1 = new Response();
        response1.addRating("4");
        response1.addRating("5");
        response1.addRating("5");
        response1.addRating("3");
        response1.addSingleSelect("Jane");

        int numberOfResponses = 5;

        // output
        ArrayList<Response> actualResponses = Loader.loadResponses(
                responsesFileName, questions);

        // testing
        assertEquals(numberOfResponses, actualResponses.size());
        assertEquals((int)response0.getRatings().get(0),
                    (int)actualResponses.get(0).getRatings().get(0),
                    "First reponse's first rating equals 5");
        assertEquals((int)response1.getRatings().get(3),
                    (int)actualResponses.get(1).getRatings().get(3),
                    "Second response's fourth rating equals 3");
        assertEquals(response1.getSingleSelects().get(0),
                    actualResponses.get(1).getSingleSelects().get(0),
                    "Second response's first single select equals 'Jane'");
    }

    @Test
    void parseCSVResponseLineTest() {

        // inputs
        String[] values = {"employee1@abc.xyz", "1",
                "2014-07-28T20:35:41+00:00", "5", "4", "questionString"};
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question(Question.RATING_QUESTION));
        questions.add(new Question(Question.RATING_QUESTION));
        questions.add(new Question(Question.SINGLE_QUESTION));

        // expected output
        int numberOfRatings = 2;
        int numberOfSingleSelects = 1;

        // output
        Response response = Loader.parseCSVResponseLine(values, questions);
        ArrayList<Integer> ratingResponses = response.getRatings();
        ArrayList<String> singleResponses = response.getSingleSelects();

        // testing
        assertTrue(response.getIsSubmitted());
        assertEquals(numberOfRatings, ratingResponses.size());
        assertEquals(numberOfSingleSelects, singleResponses.size());
        assertEquals(5, (int) ratingResponses.get(0));
        assertEquals(4, (int) ratingResponses.get(1));
        assertEquals("questionString", singleResponses.get(0));
    }

    /**
     * Case with no submission date, should be tagged as unsubmitted
     */
    @Test
    void parseCSVResponseLineTestNoDate() {
        // inputs
        String[] values = {"employee1@abc.xyz", "1", "", "5", "4",
                "questionString"};
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question(Question.RATING_QUESTION));
        questions.add(new Question(Question.RATING_QUESTION));
        questions.add(new Question(Question.SINGLE_QUESTION));

        // output
        Response response = Loader.parseCSVResponseLine(values, questions);

        // testing
        assertFalse(response.getIsSubmitted());
    }

    /**
     * Alternate case with no date, should be tagged as unsubmitted
     */
    @Test
    void parseCSVResponseLineTestNoDate2() {
        // inputs
        String[] values = {"", "1"};
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question(Question.RATING_QUESTION));
        questions.add(new Question(Question.SINGLE_QUESTION));

        // output
        Response response = Loader.parseCSVResponseLine(values, questions);

        // testing
        assertFalse(response.getIsSubmitted());
    }

    @Test
    void parseCSVResponseLineTestFile() {

        // input - testing only a few responses
        String questionFileName = "example-data/survey-test-0.csv";
        String responsesFileName = "example-data/survey-test-0-responses.csv";
        ArrayList<Question> questions = Loader.loadQuestions(questionFileName);

        // expected
        int[] ratings0 = {1,5,5,2,4,5,5,5,4,4,1};
        int numberOfRatings = 11;

        // actual
        ArrayList<Response> responses = Loader.loadResponses(responsesFileName,
                questions);

        // testing
        ArrayList<Integer> ratings = responses.get(0).getRatings();
        assertEquals(numberOfRatings, ratings.size());
        for (int i = 0; i < numberOfRatings; i++) {
            assertEquals(ratings0[i], (int)ratings.get(i));
        }
    }
}