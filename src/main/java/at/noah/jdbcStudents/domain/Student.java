package at.noah.jdbcStudents.domain;

public record Student(
        String lastName,
        String firstName,
        Gender gender,
        int number,
        String schoolClass) {

    public Student {
        if (lastName.isBlank())
            throw new IllegalArgumentException("Last name cannot be blank");
        if (firstName.isBlank())
            throw new IllegalArgumentException("First name cannot be blank");
        if (number < 1 || number > 36)
            throw new IllegalArgumentException("Number out of range, must be in [1,36]");
        if (schoolClass.isBlank())
            throw new IllegalArgumentException("Student must belong to a class");
        //TODO validate class
    }
}
