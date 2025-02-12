import java.time.LocalTime;

public class Task {
    private static int counter = 0;
    private final int id;
    private LocalTime startTime;
    private LocalTime endTime;
    private String name;

    public Task(LocalTime startTime, int duration, String name) {
        this.id = ++counter;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(duration);
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getName() {
        return name;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }


    public int getDuration() {
        return (int) java.time.Duration.between(startTime, endTime).toMinutes();
    }

    @Override
    public String toString() {
        return id + " - " + startTime + " - " + endTime + " - " + name;
    }
}
