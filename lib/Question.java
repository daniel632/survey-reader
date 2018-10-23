/**
 * POJO for Survey's. Stores survey questions.
 */
public class Question {

    static final String RATING_QUESTION = "ratingquestion";
    static final String SINGLE_QUESTION = "singleselect";

    // Could also store theme, text
    private QuestionType type;

    public Question(String surveyType) {
        setSurveyType(surveyType);
    }

    private void setSurveyType(String typeString) {
        if (typeString.equals(RATING_QUESTION)) {
            this.type = QuestionType.RATING;
        } else if (typeString.equals(SINGLE_QUESTION)) {
            this.type = QuestionType.SINGLE_SELECT;
        } else {
            this.type = QuestionType.UNDEFINED;
        }
    }

    public QuestionType getType() {
        return type;
    }
}