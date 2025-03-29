package timer;

public class Timer {
    private long startTime;

    public Timer(long startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        long currentTime = System.nanoTime();
        return ((currentTime - startTime)/1000000000);

    }
}
