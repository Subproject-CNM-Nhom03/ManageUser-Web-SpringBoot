package com.example.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.chat.entity.Address;
import com.example.chat.entity.User;

import javax.transaction.Transactional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	List<Address> getListAddressByUser(User user);
	//12/11
	@Modifying
	@Transactional
	@Query(value = "update address set numberaddress = :numberaddress, ward = :ward, provinece = :provinece, district = :district, country = :country where addressId = :addressId", nativeQuery = true)
	void updateAddressProfile(@Param("addressId") int addressId, @Param("numberaddress") String numberaddress, @Param("ward") String ward, @Param("provinece") String provinece, @Param("district") String district, @Param("country") String country);
}
