package com.cab.allocation.admin.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cab.allocation.admin.model.DropPoints;
import com.cab.allocation.admin.model.DropPointsUpdate;
import com.cab.allocation.admin.repository.DropPointsRepository;

@RestController
public class DropPointController {

	private DropPointsRepository dropPointRepository;

	@Autowired
	public DropPointController(DropPointsRepository routeRepository) {
		this.dropPointRepository = routeRepository;
	}

	@GetMapping("/dropPoints/{id}")
	public DropPoints getDropPoints(@PathVariable(name = "id") String id) {
		return dropPointRepository.findOne(id);
	}

	@GetMapping("/dropPoints")
	public List<DropPoints> getAllDropPoints() {
		return dropPointRepository.findAll();
	}

	@RequestMapping(value = "/dropPoints", method = { RequestMethod.HEAD })
	public ResponseEntity<Void> isDropPointPresent(@RequestParam(name = "name") String name) {
		DropPoints dropPoints = dropPointRepository.findAll().get(0);
		if (dropPoints.getRoutes().containsKey(name))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/dropPoints")
	public ResponseEntity<DropPoints> addDropPoints(@RequestBody DropPoints dropPoints) {
		DropPoints persistedDropPoints = dropPointRepository.save(dropPoints);
		return ResponseEntity.created(getLocationHeader(persistedDropPoints)).body(dropPoints);
	}

	@PatchMapping("/dropPoints/routes")
	public ResponseEntity<DropPoints> updateDropPoints(@RequestBody DropPointsUpdate dropPointsUpdate) {

		DropPoints dropPoints = dropPointRepository.findOne(dropPointsUpdate.getId());
		if (dropPoints == null)
			throw new ResourceNotFoundException(
					String.format("Drop Point with id { %s } not found", dropPointsUpdate.getId()));

		updateExistingRoutes(dropPointsUpdate, dropPoints);
		addNewRoutes(dropPointsUpdate, dropPoints);

		DropPoints updatedDropPoints = dropPointRepository.save(dropPoints);

		return ResponseEntity.created(getLocationHeader(updatedDropPoints)).body(dropPoints);
	}

	protected void addNewRoutes(DropPointsUpdate dropPointsUpdate, DropPoints dropPoints) {
		dropPointsUpdate.getRoutes().stream().map(route -> route.split(":"))
				.forEach(arr -> dropPoints.getRoutes().put(arr[0], arr[1]));
	}

	protected void updateExistingRoutes(DropPointsUpdate dropPointsUpdate, DropPoints dropPoints) {
		dropPointsUpdate.getRoutes().stream().map(route -> route.split(":"))
				.filter(arr -> dropPoints.getRoutes().containsKey(arr[0]))
				.forEach(arr -> dropPoints.getRoutes().put(dropPoints.getRoutes().get(arr[0]), arr[1]));
	}

	protected URI getLocationHeader(DropPoints dropPoints) {
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dropPoints.getId())
				.toUri();
		return location;
	}
}
