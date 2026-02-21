import java.util.Random;

public class StageRunner {

    private static final Random random = new Random();

    public static final int TURBO_BONUS = 5;

    public static StageResult run(RallyCar car, String stageName) {
        int power =
            car.getEngine().powerBonus + (car.hasTurbo() ? TURBO_BONUS : 0);
        int grip = car.getTires().gripBonus;
        int risk = car.getEngine().reliabilityRisk;
        int wear = car.getTires().wearPenalty;
        int roll = random.nextInt(10) - 5;
        int score = power + grip - risk - wear + roll;

        return new StageResult(
            stageName,
            car,
            power,
            grip,
            risk,
            wear,
            roll,
            score
        );
    }

    public record StageResult(
        String stageName,
        RallyCar car,
        int power,
        int grip,
        int risk,
        int wear,
        int roll,
        int score
    ) {
        public String outcome() {
            if (score > 20) return "STAGE WIN";
            if (score > 10) return "CLEAN RUN";
            if (score > 0) return "STRUGGLED";
            return "RETIREMENT";
        }

        @Override
        public String toString() {
            return String.format(
                """
                  Stage      : %s
                  Driver     : %s (%s)
                  --------------------------------
                  Power      : +%d  (engine%s)
                  Grip       : +%d  (tires)
                  Risk       : -%d  (engine reliability)
                  Wear       : -%d  (tire wear)
                  Luck roll  : %+d
                  --------------------------------
                  Score      : %d  →  %s
                """,
                stageName,
                car.getDriverName(),
                car.getTeamName(),
                power,
                car.hasTurbo() ? " + turbo" : "",
                grip,
                risk,
                wear,
                roll,
                score,
                outcome()
            );
        }
    }
}
