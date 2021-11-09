package at.noah.jdbcStudents.persistence;

import at.noah.jdbcStudents.domain.Gender;
import at.noah.jdbcStudents.domain.Student;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.*;
import java.util.stream.Stream;

public record JdbcStudentRepository(Connection connection) implements StudentRepository {

    @Override
    public void deleteAll() throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate("""
        truncate table STUDENTS
        """);
    }

    @Override
    public Optional<Student> findByNumberAndClass(int number, String schoolClass) throws SQLException {

        String sql = """
                select last_name, first_name, gender, student_number, class
                from STUDENTS
                where STUDENT_NUMBER = ? and CLASS = ?
                """;

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, number);
            statement.setString(2, schoolClass);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return Optional.empty();
            } else {
                return createStudent(resultSet);
            }
        }
    }

    @Override
    public Stream<Student> findAll() throws SQLException {
        String sql = """
                select last_name, first_name, gender, student_number, class
                from STUDENTS
                """;

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return Stream.empty();
            } else {
                Collection<Student> students = new ArrayList<>();

                do {
                    createStudent(resultSet).ifPresent(students::add);
                }while (resultSet.next());

                return students.stream();
            }
        }
    }

    @Override
    public SortedSet<Student> findStudentsByClass(String schoolClass) throws SQLException {

        String sql = """
                select LAST_NAME, FIRST_NAME, GENDER, STUDENT_NUMBER, CLASS
                from STUDENTS
                where CLASS = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, schoolClass);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return new TreeSet<>();
            } else {
                SortedSet<Student> students = new TreeSet<>();

                do {
                    createStudent(resultSet).ifPresent(students::add);
                }while (resultSet.next());

                return students;
            }
        }
    }

    @Override
    public Set<Student> findStudentsByGender(Gender gender) throws SQLException {
        String sql = """
                select last_name, first_name, gender, student_number, class
                from students
                where GENDER = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, String.valueOf(gender.toString().toLowerCase().charAt(0)));
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Set.of();
            } else {
                Set<Student> studentSet = new TreeSet<>();
                do {
                    createStudent(resultSet).ifPresent(studentSet::add);
                }while (resultSet.next());
                return studentSet;
            }
        }
    }

    @Override
    public Map<String, Integer> findClasses() throws SQLException {
        String sql = """
                select CLASS, count(CLASS) as count
                from students
                group by CLASS;
                """;

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Map.of();
            } else {
                Map<String, Integer> classSet = new TreeMap<>();
                do {
                    String schoolClass = resultSet.getString("class");
                    int count = resultSet.getInt("count");

                    classSet.put(schoolClass, count);
                } while (resultSet.next());
                return classSet;
            }
        }
    }

    @Override
    public void save(Student student) throws SQLException {
        String lastName = student.lastName();
        String firstName = student.firstName();
        String schoolClass = student.schoolClass();
        int number = student.number();
        String gender;

        switch (student.gender()) {
            case MALE -> gender = "m";
            case FEMALE -> gender = "f";
            default -> gender = "d";
        }

        String sql = """
        insert into STUDENTS values ( ?, ?, ?, ?, ? )
        """;
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, lastName);
        statement.setString(2, firstName);
        statement.setString(3, gender);
        statement.setInt(4, number);
        statement.setString(5, schoolClass);

        statement.executeUpdate();
    }

    private Optional<Student> createStudent(ResultSet resultSet) throws SQLException {
        String lastName = resultSet.getString("last_name");
        String firstName = resultSet.getString("first_name");
        String schoolClass = resultSet.getString("class");
        int number = resultSet.getInt("student_number");
        String studentGenderAsString = resultSet.getString("gender");
        Gender studentGender = null;
        switch (studentGenderAsString.toLowerCase(Locale.ROOT)) {
            case "m" -> studentGender = Gender.MALE;
            case "f" -> studentGender = Gender.FEMALE;
            case "d" -> studentGender = Gender.DIVERSE;
        }

        if (studentGender == null) {
            return Optional.empty();
        }

       return Optional.of(new Student(lastName, firstName, studentGender, number, schoolClass));
    }
}
