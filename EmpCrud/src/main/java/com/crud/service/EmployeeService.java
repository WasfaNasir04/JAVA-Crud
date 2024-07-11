package com.crud.service;

import com.crud.dto.EmployeeDto;
import com.crud.entity.employee;
import com.crud.exception.ResourceNotFoundException;
import com.crud.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDto getEmployeeById(Long id) {
        employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        return convertToDTO(employee);
    }

    public EmployeeDto saveEmployee(EmployeeDto employeeDTO) {
        employee employee = convertToEntity(employeeDTO);
        employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDTO) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setName(employeeDTO.getName());
            employee.setSalary(employeeDTO.getSalary());
            employee.setDate(employeeDTO.getDate());
            employeeRepository.save(employee);
            return convertToDTO(employee);
        }).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    public void deleteEmployee(Long id) {
        employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        employeeRepository.delete(employee);
    }

    private EmployeeDto convertToDTO(employee employee) {
        return new EmployeeDto(employee.getId(), employee.getName(), employee.getSalary(), employee.getDate());
    }

    private employee convertToEntity(EmployeeDto employeeDTO) {
        return new employee(employeeDTO.getId(), employeeDTO.getName(), employeeDTO.getSalary(), employeeDTO.getDate(), null);
    }
}
