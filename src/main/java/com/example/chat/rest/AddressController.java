package com.example.chat.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.chat.entity.DTO.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chat.entity.Address;
import com.example.chat.service.AddressService;


@RestController
@RequestMapping("/api")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@PostMapping("/addresses")
	public Address saveAddress(@RequestBody Address address) {
		return addressService.saveAddress(address);
	}

	// update
	@PutMapping("/addresses")
	public ResponseEntity<Object> updateAddress(@RequestBody Address address) {
		ApiError eror;
		String status;
		Map<String, String> o = new HashMap<String, String>();

		boolean rs=	addressService.updateAddressProfile(address);
		if(rs) {
			status = "True";
			o.put("status", status);
			return new ResponseEntity<Object>(o, HttpStatus.OK);
		}else {
			eror = new ApiError("404", "Update Fail");
			return new ResponseEntity<Object>(eror,HttpStatus.OK);
		}
	}
}
