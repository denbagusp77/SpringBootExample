package com.example.demo.dataaccesobject;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.domainobject.CarDO;

public interface CarRepository extends CrudRepository<CarDO, String>{
	
	CarDO findByLicensePlate(String licensePlate);

}
