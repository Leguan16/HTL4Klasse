package at.noah.airport;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public record Runway(String Name, Lock lock) {

}
