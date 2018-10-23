public class ParticipationResult {

    private double participationPercentage;
    private int participationCount;

    public ParticipationResult(double participationPercentage, int participationCount) {
        this.participationPercentage = participationPercentage;
        this.participationCount = participationCount;
    }

    public double getParticipationPercentage() {
        return participationPercentage;
    }

    public int getParticipationCount() {
        return participationCount;
    }
}