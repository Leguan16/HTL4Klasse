package at.noah.student.domain;

public record Student(String schoolClass,
                      int number,
                      String firstName,
                      String secondName,
                      Gender gender) implements Comparable<Student> {

    public static Student of(String csv) {
        var splitted = csv.split(",");
        var firstName = splitted[1];
        var secondName = splitted[0];
        Gender gender = null;

        switch (splitted[2]) {
            case "M" -> gender = Gender.MALE;
            case "W" -> gender = Gender.FEMALE;
            case "D" -> gender = Gender.DIVERSE;
        }

        int number = Integer.parseInt(splitted[3]);
        var schoolClass = splitted[4];
        return new Student(schoolClass, number, firstName, secondName, gender);
    }

    @Override
    public String toString() {
        return String.format("%s/%02d %s %s %s", schoolClass, number, secondName, firstName, gender);
    }

    @Override
    public int compareTo(Student o) {
        return (o.firstName().length() + o.secondName().length()) - (this.firstName().length() + this.secondName().length());
    }
}
