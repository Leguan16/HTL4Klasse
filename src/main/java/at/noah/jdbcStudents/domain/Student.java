package at.noah.jdbcStudents.domain;

import java.util.Comparator;
import java.util.Locale;

public record Student (
        String lastName,
        String firstName,
        Gender gender,
        int number,
        String schoolClass) implements Comparable<Student>{


    public static Student of(String csv) throws NumberFormatException {
        var splitted = csv.split(",");
        var lastName = splitted[1];
        var firstName = splitted[0];
        Gender gender = null;

        switch (splitted[2]) {
            case "m" -> gender = Gender.MALE;
            case "w" -> gender = Gender.FEMALE;
            case "d" -> gender = Gender.DIVERSE;
        }

        int number = Integer.parseInt(splitted[3]);
        var schoolClass = splitted[4];
        return new Student(lastName, firstName, gender, number, schoolClass);
    }

    public Student {
        if (lastName.isBlank())
            throw new IllegalArgumentException("Last name cannot be blank");
        if (firstName.isBlank())
            throw new IllegalArgumentException("First name cannot be blank");
        if (number < 1 || number > 36)
            throw new IllegalArgumentException("Number out of range, must be in [1,36]");
        if (schoolClass.isBlank())
            throw new IllegalArgumentException("Student must belong to a class");
        /*if (!schoolClass.matches("/[1-5][A-C](HIF)$/g"))
            throw new IllegalArgumentException("Student class must be in correct format!");*/
    }

    @Override
    public String toString() {
        return String.format("%s/%02d %s %s %s", schoolClass, number, lastName, firstName, gender);
    }

    @Override
    public int compareTo(Student o) {
        return Comparator.comparing(Student::schoolClass).thenComparing(Student::number).compare(this, o);
    }
}
