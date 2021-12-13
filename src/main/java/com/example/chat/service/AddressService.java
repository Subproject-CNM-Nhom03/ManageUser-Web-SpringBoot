package com.example.chat.service;

import java.util.List;

import com.example.chat.entity.Address;
import com.example.chat.entity.User;

public interface AddressService {
	Address saveAddress(Address address);
	Address updateAddress(Address address);
	List<Address> getListAddressByUser(User user);
	//Lâm thêm
	void deleteAddress(int id);

	//12/11
	Boolean updateAddressProfile(Address address);
}
