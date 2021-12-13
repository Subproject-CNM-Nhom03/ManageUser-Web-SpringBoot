package com.example.chat.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chat.entity.Address;
import com.example.chat.entity.User;
import com.example.chat.repository.AddressRepository;
import com.example.chat.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Address saveAddress(Address address) {

		return addressRepository.saveAndFlush(address);
	}

	@Override
	public Address updateAddress(Address address) {

		return addressRepository.save(address);
	}

	@Override
	public List<Address> getListAddressByUser(User user) {
		return addressRepository.getListAddressByUser(user);
	}

	/// LÂm thêm vào
	@Override
	public void deleteAddress(int id) {
		addressRepository.deleteById(id);
	}

	//12/11
	@Override
	public Boolean updateAddressProfile(Address address) {
		try {
			if(address.getAddressId()==0)
				addressRepository.save(address);
			else
				addressRepository.updateAddressProfile(address.getAddressId(), address.getNumberaddress(), address.getWard(), address.getProvinece(), address.getDistrict(), address.getCountry());
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
}
