package com.cab.allocation.admin.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cab.allocation.admin.TeamMemberDTO;

@Component
public class RestUtils {

	private RestTemplate restTemplate;

	@Autowired
	public RestUtils(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public List<TeamMemberDTO> getAllTeamMembers() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		try {
			ResponseEntity<List<TeamMemberDTO>> teamMembers = restTemplate.exchange("/teamMembers", HttpMethod.GET,
					null, new ParameterizedTypeReference<List<TeamMemberDTO>>() {
					});

			if (teamMembers.getStatusCode() == HttpStatus.OK) {
				return teamMembers.getBody();
			}

			return null;

		} catch (RestClientException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

}
