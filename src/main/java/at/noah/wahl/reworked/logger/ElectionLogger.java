package at.noah.wahl.reworked.logger;

import java.util.List;

public interface ElectionLogger<T> extends Logger {

    void printCandidates(List<T> candidates);

}
