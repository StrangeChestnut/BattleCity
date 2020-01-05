package game;

public enum Status {
    RUN, PAUSE, WIN, LOOSE;

    public boolean isRunning() {
        return this == RUN || this == PAUSE;
    }
}
