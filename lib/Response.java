import java.util.ArrayList;

public class Response {

    static final int EMPTY_RATING = -1;

    // Could also store email, employee number, submission date
    // May want to make an enum for SingleSelect?
    private ArrayList<Integer> ratings;
    private ArrayList<String> singleSelects;

    // Indicates if survey has been submitted, true by default
    private boolean isSubmitted = true;

    public Response() {
        this.ratings = new ArrayList<>();
        this.singleSelects = new ArrayList<>();
    }

    // TODO - check that rating is within 1,5? (not sure if required)
    public void addRating(String ratingString) {

        if (ratingString.isEmpty()) {
            ratings.add(EMPTY_RATING);
            return;
        }
        ratings.add(Integer.parseInt(ratingString));
    }

    public void addSingleSelect(String singleSelect) {
        singleSelects.add(singleSelect);
    }

    public ArrayList<Integer> getRatings() {
        return ratings;
    }

    public ArrayList<String> getSingleSelects() {
        return singleSelects;
    }

    public boolean getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }
}