package at.noah.airport;

public record Plane(Runway runway) implements Runnable{

    @Override
    public void run() {
    }
}
