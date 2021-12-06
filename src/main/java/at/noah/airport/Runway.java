package at.noah.airport;

import java.util.concurrent.locks.Lock;

public record Runway(String Name, Lock lock) {

}
