import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

    /**
     * Test adding a non-empty and an empty integer rating
     */
    @Test
    void addRatingTest() {

        // inputs
        Response response = new Response();
        String ratingString0 = "1";
        String ratingString1 = "";      // Empty input

        // expected output
        int rating0 = 1;
        int rating1 = Response.EMPTY_RATING;
        int numberOfRatings = 2;
        int numberOfSelects = 0;

        // "output"
        response.addRating(ratingString0);
        response.addRating(ratingString1);

        // testing
        assertEquals(rating0, (int) response.getRatings().get(0));
        assertEquals(rating1, (int) response.getRatings().get(1));
        assertEquals(numberOfRatings, response.getRatings().size());
        assertEquals(numberOfSelects, response.getSingleSelects().size());
    }
}