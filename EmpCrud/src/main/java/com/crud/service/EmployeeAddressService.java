package com.crud.service;

import com.crud.dto.EmployeeAddressDto;
import com.crud.entity.EmployeeAddress;
import com.crud.exception.ResourceNotFoundException;
import com.crud.repository.EmployeeAddressRepo;
import com.crud.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeAddressService {

    @Autowired
    private EmployeeAddressRepo addressRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeAddressDto> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeAddressDto getAddressById(Long id) {
        EmployeeAddress address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + id));
        return convertToDTO(address);
    }

    public EmployeeAddressDto saveAddress(EmployeeAddressDto addressDTO) {
        EmployeeAddress address = convertToEntity(addressDTO);
        EmployeeAddress savedAddress = addressRepository.save(address);
        return convertToDTO(savedAddress);
    }

    public EmployeeAddressDto updateAddress(Long id, EmployeeAddressDto addressDTO) {
        return addressRepository.findById(id).map(address -> {
            address.setAddress(addressDTO.getAddress());
            address.setPhone(addressDTO.getPhone());
            addressRepository.save(address);
            return convertToDTO(address);
        }).orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + id));
    }

    public void deleteAddress(Long id) {
        EmployeeAddress address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id " + id));
        addressRepository.delete(address);
    }

    private EmployeeAddressDto convertToDTO(EmployeeAddress address) {
        return new EmployeeAddressDto(address.getId(), address.getEmployee().getId(), address.getAddress(), address.getPhone());
    }

    private EmployeeAddress convertToEntity(EmployeeAddressDto addressDTO) {
        EmployeeAddress address = new EmployeeAddress();
        address.setId(addressDTO.getId());
        address.setAddress(addressDTO.getAddress());
        address.setPhone(addressDTO.getPhone());
        address.setEmployee(employeeRepository.findById(addressDTO.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + addressDTO.getEmployeeId())));
        return address;
    }
}
