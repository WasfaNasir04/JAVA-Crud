package com.crud.controller;

import com.crud.dto.EmployeeAddressDto;
import com.crud.service.EmployeeAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class EmployeeAddressController {

    @Autowired
    private EmployeeAddressService addressService;

    @GetMapping
    public List<EmployeeAddressDto> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeAddressDto> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeAddressDto> createAddress(@RequestBody EmployeeAddressDto addressDTO) {
        return ResponseEntity.ok(addressService.saveAddress(addressDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeAddressDto> updateAddress(@PathVariable Long id, @RequestBody EmployeeAddressDto addressDTO) {
        return ResponseEntity.ok(addressService.updateAddress(id, addressDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
