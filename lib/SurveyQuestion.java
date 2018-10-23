/**
 * POJO for Survey's
 */
public class SurveyQuestion {

    // TODO - privacy modifiers
    public String theme;
    public SurveyType type;
    public String text;

    public SurveyQuestion(String theme, SurveyType type, String text) {
        this.theme = theme;
        this.type = type;
        this.text = text;
    }
}