package com.cab.allocation.admin.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cab.allocation.admin.model.Cab;
import com.cab.allocation.admin.model.Cabs;
import com.cab.allocation.admin.repository.CabService;

import io.swagger.annotations.ApiOperation;

@RestController
public class CabController {

	private CabService cabService;

	@Autowired
	public CabController(CabService cabService) {
		this.cabService = cabService;
	}

	@GetMapping("/cabs")
	@ApiOperation(value = "Get All Cabs", response = Cabs.class)
	public Cabs getAllCabs() {
		return cabService.findAll();
	}

	@PostMapping("/cabs")
	@ApiOperation(value = "Add cabs in bulk", response = Cabs.class)
	public ResponseEntity<Cabs> addCabs(@Valid @RequestBody Cabs cabs) {
		Cabs cabsPersisted = cabService.saveAllCabs(cabs);
		return new ResponseEntity<Cabs>(cabsPersisted, HttpStatus.CREATED);
	}

	@GetMapping("/cab/{id}")
	@ApiOperation(value = "Get Cab by cabId ", response = Cab.class)
	public Cab getCabById(@PathVariable(name = "cabId") String cabId) {
		return cabService.findByCabId(cabId);
	}

	@ApiOperation(value = "Add cab ", response = Cab.class)
	@PostMapping("/cab")
	public ResponseEntity<Cab> addCab(@Valid @RequestBody Cab cab) {
		Cab persistedCab = cabService.saveCab(cab);
		return new ResponseEntity<Cab>(persistedCab, HttpStatus.CREATED);
	}

	@PutMapping("/cab")
	@ApiOperation(value = "Update Cab ", response = Cab.class)
	public ResponseEntity<Cab> updateCab(@Valid @RequestBody Cab cab) {

		Cab fetchCab = cabService.findByCabId(cab.getCabId());

		if (fetchCab == null)
			throw new ResourceNotFoundException(String.format("Cab with cabID { %s } was not found ", cab.getCabId()));

		cab.setId(fetchCab.getId());
		Cab persistedCab = cabService.saveCab(cab);
		return new ResponseEntity<Cab>(persistedCab, HttpStatus.CREATED);
	}

	@DeleteMapping("/cab/{cabId}")
	@ApiOperation(value = "Delete Cab ", response = Cab.class)
	public ResponseEntity<Void> deleteCab(@Valid @PathVariable(name = "cabId") String cabId) {
		Cab fetchCab = cabService.findByCabId(cabId);

		if (fetchCab == null)
			throw new ResourceNotFoundException(String.format("Cab with cabID { %s } was not found ", cabId));

		cabService.deleteCabById(fetchCab);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	protected List<URI> getLocationHeader(List<Cab> cabs) {
		List<URI> uris = new ArrayList<>();
		cabs.stream().forEach(cab -> {
			uris.add(getLocationHeader(cab));
		});
		return uris;
	}

	protected URI getLocationHeader(Cab cab) {
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cab.getId())
				.toUri();
		return location;
	}
}
