/**
 * POJO for Survey's
 */
public class Question {

    static final String RATING_QUESTION = "ratingquestion";
    static final String SINGLE_QUESTION = "singleselect";

    // Could also store theme, text
    private SurveyType type;

    public Question(String surveyType) {
        setSurveyType(surveyType);
    }

    private void setSurveyType(String typeString) {
        if (typeString.equals(RATING_QUESTION)) {
            this.type = SurveyType.RATING;
        } else if (typeString.equals(SINGLE_QUESTION)) {
            this.type = SurveyType.SINGLE_SELECT;
        } else {
            this.type = SurveyType.UNDEFINED;
        }
    }

    public SurveyType getType() {
        return type;
    }
}