package com.cab.allocation.admin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cab.allocation.admin.model.Cab;
import com.cab.allocation.admin.model.Cabs;

@Service
public class CabService {

	private CabsRepository cabsRepository;

	@Autowired
	public CabService(CabsRepository cabsRepository) {
		this.cabsRepository = cabsRepository;
	}

	public Cab findOne(String id) {
		return cabsRepository.findOne(id);
	}

	public Cabs findAll() {
		Cabs cabs = new Cabs(cabsRepository.findAll());
		return cabs;
	}

	public Cabs saveAllCabs(Cabs cabs) {

		cabs.getCabs().stream().forEach(cab -> {
			cabsRepository.save(cab);
		});
		
		return findAll();

	}

	public Cab saveCab(Cab cab) {
		return cabsRepository.save(cab);
	}

	public Cab findByCabId(String cabId) {
		return cabsRepository.findByCabId(cabId);
	}

	public void deleteCabById(Cab cab) {
		cabsRepository.delete(cab);
	}

}
