package org.kiki_cpg_v2.controller;

import org.kiki_cpg_v2.dto.request.ViewerPolicyUpdateRequestDto;
import org.kiki_cpg_v2.service.ViewerPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/viewerpolicy")
public class ViewerPolicyController {

	@Autowired
	private ViewerPolicyService viewerPolicyService;

	@PostMapping("/update")
	public ResponseEntity<Object> updateViewerPolicy(ViewerPolicyUpdateRequestDto viewerPolicyUpdateRequestDto) {
		ResponseEntity<Object> responseEntity = null;
		try {
			String resp = viewerPolicyService.updateViewerPolicy(viewerPolicyUpdateRequestDto);
			if (resp.equals("success")) {
				responseEntity = new ResponseEntity<Object>("Success", HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<Object>("Fail", HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<Object>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}

}
