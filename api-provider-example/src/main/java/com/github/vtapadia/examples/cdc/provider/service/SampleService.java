package com.github.vtapadia.examples.cdc.provider.service;

import com.github.vtapadia.examples.cdc.provider.domain.Employee;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class SampleService {
    private Map<Long, Employee> map = new HashMap<>();

    @PostConstruct
    public void setup() {
        map.put(1L, new Employee(1L, "User1", "dep1"));
        map.put(2L, new Employee(2L, "User2", "dep1"));
        map.put(3L, new Employee(3L, "User3", "dep1"));
    }

    public Employee getEmployee(Long id) {
        return map.get(id);
    }

    public Long addEmployee(Employee employee) {
        map.put(employee.getId(), employee);
        return employee.getId();
    }
}
