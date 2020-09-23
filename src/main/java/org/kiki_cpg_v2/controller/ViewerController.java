package org.kiki_cpg_v2.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.kiki_cpg_v2.controller.factory.InvoiceFactoryController;
import org.kiki_cpg_v2.dto.InvoiceDetailsForNotificationEmailDto;
import org.kiki_cpg_v2.dto.ResponseMapDto;
import org.kiki_cpg_v2.dto.request.ViewerEmailUpdateDto;
import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.service.EmailService;
import org.kiki_cpg_v2.service.PaymentDetailService;
import org.kiki_cpg_v2.service.ViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/viewer")
public class ViewerController {

	@Autowired
	private ViewerService viewerService;
	
	@Autowired
	private InvoiceFactoryController invoiceFactory;
		
	@Value("${viewer.notification.email.subject}")
	private String subjectEmail;

	@RequestMapping(value = "/updatevieweremail", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> update_viewer_email(@RequestBody ViewerEmailUpdateDto viewerEmailUpdateDto,
			HttpServletRequest request) {
		System.out.println(viewerEmailUpdateDto.toString());
		try {
			ResponseMapDto responseMapDto = null;
			viewerService.updateViewerEmailAddress(viewerEmailUpdateDto.getEmailAddress(), viewerEmailUpdateDto.getViewerId());
			responseMapDto = new ResponseMapDto();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("emailaddress", viewerEmailUpdateDto.getEmailAddress());
			map.put("viewerid", viewerEmailUpdateDto.getViewerId());
			responseMapDto.setDataMap(map);
			responseMapDto.setStatus("true");
			
			return new ResponseEntity<Object>(responseMapDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/getvieweremail", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> get_viewer_email(@RequestBody ViewerEmailUpdateDto viewerEmailUpdateDto,
			HttpServletRequest request) {
		System.out.println("inside get_viewer_email ");
		System.out.println(viewerEmailUpdateDto.toString());
		try {
			ResponseMapDto responseMapDto = null;
			ViewerEntity viewerEntity = viewerService.getViewerEmailAddress(viewerEmailUpdateDto.getViewerId());
			responseMapDto = new ResponseMapDto();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("emailaddress", viewerEntity.getEmail());
			map.put("viewerid", viewerEntity.getId());
			
			System.out.println("emailaddress - " + map.get("emailaddress"));
			System.out.println("viewerid - " + map.get("viewerid"));
			responseMapDto.setDataMap(map);
			responseMapDto.setStatus("TRUE");
			
			return new ResponseEntity<Object>(responseMapDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/send-viewer-notification-email", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> send_viewer_notification_email(@RequestBody ViewerEmailUpdateDto viewerEmailUpdateDto,
			HttpServletRequest request) {
		try {
			ResponseMapDto responseMapDto = new ResponseMapDto();
			InvoiceDetailsForNotificationEmailDto invoiceDetailsForNotificationEmailDto = invoiceFactory.getInvoiceDetails(viewerEmailUpdateDto.getInvoiceId(), viewerEmailUpdateDto.getPaymentMethodId());
			boolean isEmailSuccess = viewerService.sendViewerNotificationEmail(viewerEmailUpdateDto.getEmailAddress(), viewerEmailUpdateDto.getViewerId(), subjectEmail, invoiceDetailsForNotificationEmailDto);
			if (isEmailSuccess) {
				responseMapDto.setStatus("TRUE");
				return new ResponseEntity<Object>(responseMapDto, HttpStatus.OK);
			}
			return new ResponseEntity<Object>("Viewer Notification Email send failed", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
