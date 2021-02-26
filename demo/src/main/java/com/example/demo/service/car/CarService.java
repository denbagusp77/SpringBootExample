package com.example.demo.service.car;



import com.example.demo.domainobject.CarDO;
import com.example.demo.exception.CarAlreadyInUseException;
import com.example.demo.exception.ConstraintsViolationException;
import com.example.demo.exception.EntityNotFoundException;

public interface CarService {
	   CarDO find(String licensePlate) throws EntityNotFoundException;

	    CarDO create(CarDO carDO) throws ConstraintsViolationException;

	    void delete(String licensePlate) throws EntityNotFoundException, ConstraintsViolationException;

	    void addDriver(Long driverId, String licensePlate) throws ConstraintsViolationException, EntityNotFoundException, CarAlreadyInUseException;

	    void deleteDriver(Long driverId, String licensePlate) throws ConstraintsViolationException, EntityNotFoundException, CarAlreadyInUseException;

	    Iterable<CarDO> findAll();
	}

