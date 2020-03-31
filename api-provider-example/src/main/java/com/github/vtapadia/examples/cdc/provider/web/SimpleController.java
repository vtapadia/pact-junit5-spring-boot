package com.github.vtapadia.examples.cdc.provider.web;

import com.github.vtapadia.examples.cdc.provider.domain.Employee;
import com.github.vtapadia.examples.cdc.provider.service.SampleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/employee")
public class SimpleController {

    @Autowired
    private SampleService sampleService;

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Gets the employee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee object", response = Employee.class)
    })
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(sampleService.getEmployee(id));
    }

    @PostMapping
    @ApiOperation(value = "Add the employee")
    public ResponseEntity<?> addUser(@RequestBody Employee employee) {
        Long eId = sampleService.addEmployee(employee);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(eId).toUri();

        return ResponseEntity.created(location).build();
    }
}
