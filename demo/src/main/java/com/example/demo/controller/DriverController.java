package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.mapper.DriverMapper;
import com.example.demo.datatransferobject.DriverDTO;
import com.example.demo.domainobject.DriverDO;
import com.example.demo.domainvalue.OnlineStatus;
import com.example.demo.exception.CarAlreadyInUseException;
import com.example.demo.exception.ConstraintsViolationException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.service.car.CarService;
import com.example.demo.service.driver.DriverService;
import com.google.common.collect.Lists;


@RestController
@RequestMapping("v1/drivers")
public class DriverController {

    private final DriverService driverService;
    private final CarService carService;


    @Autowired
    public DriverController(final DriverService driverService, final CarService carService) {
        this.carService = carService;
        this.driverService = driverService;
    }


    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(@Valid @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
            throws ConstraintsViolationException, EntityNotFoundException {
        driverService.updateLocation(driverId, longitude, latitude);
    }

    @PutMapping("/{driverId}/car/{licensePlate}")
    public void selectCar(@Valid @PathVariable long driverId, @PathVariable String licensePlate)
            throws ConstraintsViolationException, EntityNotFoundException, CarAlreadyInUseException {
        carService.addDriver(driverId, licensePlate);
    }


    @DeleteMapping("/{driverId}/car/{licensePlate}")
    public void deselectCar(@Valid @PathVariable long driverId, @PathVariable String licensePlate)
            throws ConstraintsViolationException, EntityNotFoundException, CarAlreadyInUseException {
        carService.deleteDriver(driverId, licensePlate);
    }


    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam(required = false) OnlineStatus onlineStatus,
                                       @RequestParam(required = false) String username,
                                       @RequestParam(required = false) Boolean deleted)
            throws ConstraintsViolationException, EntityNotFoundException {

        List<DriverDO> drivers = Lists.newArrayList(driverService.findAll());
        if (onlineStatus != null) {
            drivers = drivers.stream().filter(driver -> driver.getOnlineStatus() == onlineStatus).collect(Collectors.toList());
        }
        if (username != null) {
            drivers = drivers.stream().filter(driver -> driver.getUsername().equals(username)).collect(Collectors.toList());
        }
        if (deleted != null) {
            drivers = drivers.stream().filter(driver -> driver.getDeleted().equals(deleted)).collect(Collectors.toList());
        }
        return DriverMapper.makeDriverDTOList(drivers);
    }
}
