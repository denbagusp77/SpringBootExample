package com.example.demo.service.car;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dataaccesobject.CarRepository;
import com.example.demo.domainobject.CarDO;
import com.example.demo.domainobject.DriverDO;
import com.example.demo.exception.CarAlreadyInUseException;
import com.example.demo.exception.ConstraintsViolationException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.service.driver.DriverService;


import static com.example.demo.domainvalue.OnlineStatus.ONLINE;




@Service
public class DefaultCarService implements CarService {


    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

    private final CarRepository carRepository;
    private DriverService driverService;

    public DefaultCarService(final CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @Override
    @Transactional
    public CarDO find(String licensePlate) throws EntityNotFoundException {
        return findCarChecked(licensePlate);
    }

    @Override
    @Transactional
    public CarDO create(CarDO carDO) throws ConstraintsViolationException {
        CarDO driver;
        try {
            driver = carRepository.save(carDO);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }

    @Override
    @Transactional
    public void delete(String licensePlate) throws EntityNotFoundException, ConstraintsViolationException {
        CarDO carDO = findCarChecked(licensePlate);
        carRepository.delete(carDO);
    }

    @Override
    @Transactional
    public void addDriver(Long driverId, String licensePlate) throws ConstraintsViolationException, EntityNotFoundException, CarAlreadyInUseException {
        CarDO carDO = find(licensePlate);
        DriverDO driver = driverService.find(driverId);
        if (carDO.getDriver() != null) {
            throw new CarAlreadyInUseException("This car is already in use by another driver");
        }
        if (driver.getOnlineStatus() != ONLINE) {
            throw new ConstraintsViolationException("Driver status is not ONLINE");
        }
        carDO.setDriver(driver);
        create(carDO);
        driverService.updateCar(driverId, carDO);
    }

    @Override
    @Transactional
    public void deleteDriver(Long driverId, String licensePlate) throws ConstraintsViolationException, EntityNotFoundException, CarAlreadyInUseException {
        CarDO carDO = find(licensePlate);
        DriverDO driver = driverService.find(driverId);
        if (carDO.getDriver() == null) {
            throw new ConstraintsViolationException("No driver for this car");
        }
        if (carDO.getDriver().getId() != driver.getId()) {
            throw new ConstraintsViolationException("This driver is not driving this car");
        }
        carDO.setDriver(null);
        create(carDO);
        driverService.updateCar(driverId, null);
    }

    @Override
    public Iterable<CarDO> findAll() {
        return carRepository.findAll();
    }

    private CarDO findCarChecked(String licensePlate) throws EntityNotFoundException {
        CarDO carDO = carRepository.findByLicensePlate(licensePlate);
        if (carDO == null) {
            throw new EntityNotFoundException("Could not find car with this licensePlate");
        }
        return carDO;
    }
}
