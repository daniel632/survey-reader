import java.util.ArrayList;

public class Response {

    static final int EMPTY_RATING = -1;

    // Could also store email, employee number, submission date
    private ArrayList<Integer> ratingResponses;
    private ArrayList<String> singleSelectResponses; // TODO - define enum for singleselect response types

    public Response() {
        this.ratingResponses = new ArrayList<>();
        this.singleSelectResponses = new ArrayList<>();
    }

    // TODO - check that rating is within 1,5?
    public void addRating(String ratingString) {

        if (ratingString.isEmpty()) {
            ratingResponses.add(EMPTY_RATING);
            return;
        }
        ratingResponses.add(Integer.parseInt(ratingString));
    }

    // Note, if singleSelect string is empty, don't need to do anything
    public void addSingleSelect(String singleSelect) {
        singleSelectResponses.add(singleSelect);
    }

    public ArrayList<Integer> getRatingResponses() {
        return ratingResponses;
    }

    public ArrayList<String> getSingleSelectResponses() {
        return singleSelectResponses;
    }

}