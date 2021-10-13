package at.noah.student;

import at.noah.student.domain.Gender;
import at.noah.student.domain.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StudentStatistics {

    private final Collection<Student> students;

    /**
     * Reads all students from given csv-File.
     * @param path the file
     * @throws IOException
     */
    public StudentStatistics(Path path) throws IOException {
        students = Files
                .lines(path)
                .skip(1)
                .map(Student::of)
                .collect(Collectors.toList());
    }

    /**
     * Counts all students of given gender
     * @param gender the gender
     * @return number of students of the given gender
     */
    public long countGender(Gender gender) {
        return students
                .stream()
                .filter(student -> student.gender().equals(gender))
                .count();
    }

    /**
     * Returns all students classes sorted alphabetically.
     * @return all students classes sorted alphabetically
     */
    public SortedSet<String> getClasses() {
        return students
                .stream()
                .map(Student::schoolClass)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Returns a map containing the number students of each gender in a given class.
     * @param schoolClass the class
     * @return count of students of each gender
     */
    public Map<Gender, Long> getGenderCountForClass(String schoolClass) {
        return students
                .stream()
                .filter(student -> student.schoolClass().equals(schoolClass))
                .collect(Collectors.groupingBy(Student::gender, TreeMap::new, Collectors.counting()));
    }

    /**
     * Returns all students whose second name contains the given sequence.
     * @param sequence the sequence to search for. Case sensitive
     * @return students named like %sequence%
     */
    public List<Student> getAllWithSecondNameLike(String sequence) {
        return students
                .stream()
                .filter(student -> student.secondName().contains(sequence))
                .collect(Collectors.toList());
    }

    /**
     * Finds a student by number and class
     * @param number students number
     * @param schoolClass students class
     * @return the student or Optional.empty if no student matches criteria
     */
    public Optional<Student> findByNumberAndClass(int number, String schoolClass) {
        return students
                .stream()
                .filter(student -> student.number() == number && student.schoolClass().equals(schoolClass))
                .findFirst();
    }

    /**
     * Returns the student with the longest name.
     * @return any student whose name contains the highest number of letters in all names
     */
    public Student getStudentWithLongestName() {
        return students
                .stream()
                .min((o1, o2) -> (o2.firstName().length() + o2.secondName().length()) - (o1.firstName().length() + o1.secondName().length()))
                .orElseThrow(() -> new NoSuchElementException("No student was found!"));
    }

    /**
     * Returns the most frequently found first names
     * @param count how many should be returned
     * @return the topX most frequent first names
     */
    public Set<String> getMostFrequentFirstNames(int count) {

        if (count <= 0) {
            throw new IllegalArgumentException("count cant be 0 or negative!");
        }

        return students
                .stream()
                .collect(Collectors.groupingBy(Student::firstName, TreeMap::new, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                .map(Map.Entry::getKey)
                .limit(count)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Returns the number of students in each year.
     * @return count of students of each year
     */
    public Map<Integer, Long> countStudentsByYear() {
        return students
                .stream()
                .collect(Collectors.toMap(
                        student -> student.schoolClass().charAt(0) - '0',
                        student -> 1L,
                        Long::sum,
                        HashMap::new
                ));
    }
}
