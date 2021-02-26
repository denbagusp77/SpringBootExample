package com.example.demo.dataaccesobject;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.domainobject.DriverDO;
import com.example.demo.domainvalue.OnlineStatus;




public interface DriverRepository extends CrudRepository<DriverDO, Long>
{

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);

	

	
}
