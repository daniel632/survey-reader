public class ParticipationResult {

    // TODO - privacy
    private float participationPercentage;
    private int participationCount;

    public ParticipationResult(float participationPercentage, int participationCount) {
        this.participationPercentage = participationPercentage;
        this.participationCount = participationCount;
    }

    public float getParticipationPercentage() {
        return participationPercentage;
    }

    public int getParticipationCount() {
        return participationCount;
    }
}