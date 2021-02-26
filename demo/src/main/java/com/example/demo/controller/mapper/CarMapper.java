package com.example.demo.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.datatransferobject.CarDto;
import com.example.demo.domainobject.CarDO;



public class CarMapper {
	  public static CarDto makeCarDTO(CarDO carDO) {

	        CarDto.CarDTOBuilder carDTOBuilder = new CarDto.CarDTOBuilder()
	                .setSeatCount(carDO.getSeatCount())
	                .setRating(carDO.getRating())
	                .setLicensePlate(carDO.getLicensePlate())
	                .setId(carDO.getId())
	                .setConvertible(carDO.getConvertible())
	                .setEngineType(carDO.getEngineType())
	                .setManufacturer(carDO.getManufacturer());

	        if (carDO.getDriver() != null) {
	            carDTOBuilder.setDriver(DriverMapper.makeDriverDTO(carDO.getDriver()));
	        }
	        return carDTOBuilder.build();
	    }

	    public static CarDO makeCarDO(CarDto carDTO) {
	        return new CarDO(carDTO.getLicensePlate(), carDTO.getSeatCount(), carDTO.isConvertible(), carDTO.getRating(), carDTO.getEngineType(), carDTO.getManufacturer());
	    }

	    public static List<CarDto> makeCarDTOList(Collection<CarDO> drivers)
	    {
	        return drivers.stream()
	                .map(CarMapper::makeCarDTO)
	                .collect(Collectors.toList());
	    }
	}
