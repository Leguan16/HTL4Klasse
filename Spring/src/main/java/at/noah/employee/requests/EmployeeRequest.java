package at.noah.employee.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class EmployeeRequest {

    String id;
    String firstName;
    String lastName;

    public boolean notValid() {
        return id == null || firstName == null || lastName == null;
    }
}
