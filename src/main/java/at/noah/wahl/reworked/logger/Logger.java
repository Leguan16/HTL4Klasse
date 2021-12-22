package at.noah.wahl.reworked.logger;

import java.io.File;

public interface Logger {

    void print(String message);

    void printInputLine();

    File getLogFile();
}
