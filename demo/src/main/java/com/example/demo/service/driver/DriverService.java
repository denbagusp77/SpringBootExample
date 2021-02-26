package com.example.demo.service.driver;

import java.util.List;



import com.example.demo.domainobject.CarDO;
import com.example.demo.domainobject.DriverDO;
import com.example.demo.domainvalue.OnlineStatus;
import com.example.demo.exception.ConstraintsViolationException;
import com.example.demo.exception.EntityNotFoundException;





public interface DriverService {
	
	  DriverDO find(Long driverId) throws EntityNotFoundException ;

	    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

	    void delete(Long driverId) throws EntityNotFoundException;

	    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException, ConstraintsViolationException;

	    void updateCar(long driverId, CarDO carDO) throws EntityNotFoundException, ConstraintsViolationException;

	    List<DriverDO> find(OnlineStatus onlineStatus);

	    Iterable<DriverDO> findAll();

}
