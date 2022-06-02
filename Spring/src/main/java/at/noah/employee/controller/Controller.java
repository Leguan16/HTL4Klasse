package at.noah.employee.controller;

import at.noah.employee.domain.Employee;
import at.noah.employee.domain.Task;
import at.noah.employee.exceptions.EmployeeNotFoundException;
import at.noah.employee.persistence.EmployeeRepository;
import at.noah.employee.persistence.TaskRepository;
import at.noah.employee.requests.EmployeeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class Controller {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    public Controller(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping()
    public List<Employee> getAll(@RequestParam(name = "name", required = false) String partialName) throws EmployeeNotFoundException {
        if (partialName == null) {
            return employeeRepository.findAll();
        }

        var names = employeeRepository.findAllByNameContains(partialName);

        if (names.isEmpty()) {
            throw new EmployeeNotFoundException();
        }

        return names;
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable String id) throws EmployeeNotFoundException {

        var employee = employeeRepository.findById(id);

        return employee.orElseThrow(EmployeeNotFoundException::new);
    }

    @PostMapping
    public ResponseEntity<?> postEmployee(@RequestBody EmployeeRequest request) {
        System.out.println(request);

        if (request == null || request.notValid()) {
            return ResponseEntity.status(400).body("Invalid Request Body");
        }

        var saved = employeeRepository.save(new Employee(request.getId(), request.getFirstName(), request.getLastName()));

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}/hoursWorked")
    public Long getHoursWorked(@PathVariable String id) throws EmployeeNotFoundException {
        ;

        return taskRepository.getTotalHoursWorkedByEmployee(getById(id));
    }

    @GetMapping("/{id}/tasks")
    public List<Task> getAllTasksBetween(@PathVariable String id, @RequestParam String from, @RequestParam String to) throws EmployeeNotFoundException {
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        if (toDate.isBefore(fromDate)) {
            throw new DateTimeException("to can not be before from");
        }

        return taskRepository.getTaskByAssignedEmployeeBetween(getById(id), fromDate , toDate);
    }

}
