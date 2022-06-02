package at.noah.employee.persistence;

import at.noah.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query("select e from Employee e where e.firstName like concat('%', ?1, '%') or e.lastName like concat('%', ?1, '%')")
    List<Employee> findAllByNameContains(String partialName);


}
