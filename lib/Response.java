import java.util.ArrayList;

public class Response {

    static final int EMPTY_RATING = -1;

    // Could also store email, employee number, submission date
    private ArrayList<Integer> ratings;
    private ArrayList<String> singleSelects; // TODO - define enum for singleselect response types

    public Response() {
        this.ratings = new ArrayList<>();
        this.singleSelects = new ArrayList<>();
    }

    // TODO - check that rating is within 1,5?
    public void addRating(String ratingString) {

        if (ratingString.isEmpty()) {
            ratings.add(EMPTY_RATING);
            return;
        }
        ratings.add(Integer.parseInt(ratingString));
    }

    // Note, if singleSelect string is empty, don't need to do anything
    public void addSingleSelect(String singleSelect) {
        singleSelects.add(singleSelect);
    }

    public ArrayList<Integer> getRatings() {
        return ratings;
    }

    public ArrayList<String> getSingleSelects() {
        return singleSelects;
    }

}