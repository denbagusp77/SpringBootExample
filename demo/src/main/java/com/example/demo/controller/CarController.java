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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.mapper.CarMapper;
import com.example.demo.datatransferobject.CarDto;
import com.example.demo.domainobject.CarDO;
import com.example.demo.exception.ConstraintsViolationException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.service.car.CarService;
import com.google.common.collect.Lists;


@RestController
@RequestMapping("t")
public class CarController { 
	
	private final CarService carService;

@Autowired
public CarController(final CarService carService) {
    this.carService = carService;
}

@GetMapping("/{licensePlate}")
public CarDto getCar(@Valid @PathVariable String licensePlate) throws EntityNotFoundException {
    return CarMapper.makeCarDTO(carService.find(licensePlate));
}

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public CarDto createCar(@Valid @RequestBody CarDto carDTO) throws ConstraintsViolationException {
    CarDO carDO = CarMapper.makeCarDO(carDTO);
    return CarMapper.makeCarDTO(carService.create(carDO));
}


@DeleteMapping("/{licensePlate}")
public void deleteCar(@Valid @PathVariable String licensePlate) throws EntityNotFoundException, ConstraintsViolationException {
    carService.delete(licensePlate);
}

@GetMapping
public List<CarDto> findCars()
        throws ConstraintsViolationException, EntityNotFoundException {
    List<CarDO> cars = Lists.newArrayList(carService.findAll()).stream().
            collect(Collectors.toList());
    return CarMapper.makeCarDTOList(cars);
}
}
