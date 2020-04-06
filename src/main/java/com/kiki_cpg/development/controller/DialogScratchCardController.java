package com.kiki_cpg.development.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kiki_cpg.development.entity.SubscriptionPayments;
import com.kiki_cpg.development.entity.TblScratchCardCodes;
import com.kiki_cpg.development.entity.TblScratchCards;
import com.kiki_cpg.development.service.ScratchCardCodesService;
import com.kiki_cpg.development.service.SubscriptionPaymentService;
import com.kiki_cpg.development.service.ViewerService;


@CrossOrigin("")
@RestController("kiki-cpg/api/v1/scratchCard")
public class DialogScratchCardController {
	
	@Autowired
	ViewerService viewerService;
	
	@Autowired
	SubscriptionPaymentService subscriptionPayService;
	
	@Autowired
	ScratchCardCodesService scratchCardCodeService;
	
	private static final Logger logger = LoggerFactory.getLogger(DialogScratchCardController.class);
	
	@RequestMapping(value = { "/payment" }, method = RequestMethod.POST)
	public String validateAndUseCode(@RequestParam("card_code") String cardCode, ModelMap model,
			HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		
		Object viewerId = session.getAttribute("viewerID");
		int viewerID = 0;
		if (viewerId == null) {
			String trasactionToken = session.getAttribute("token").toString();
			SubscriptionPayments subscriptionPayment = subscriptionPayService.validatePaymentToken(trasactionToken);
			viewerID = subscriptionPayment.getViewerID();
		} else {
			viewerID = (int) session.getAttribute("viewerID");
		}

		if (logger.isInfoEnabled()) {
            logger.info("Validate and Use Promo" + viewerID);
        }

        TblScratchCards tblScratchCard = null;
        TblScratchCardCodes tblScratchCardCode = null;
		
        try {
            tblScratchCard = scratchCardCodeService.validateCode(cardCode);

        } catch (NumberFormatException nf) {
            if (logger.isInfoEnabled()) {
                logger.info("Could not parse card code");
            }
        }
        
		return null;
	}
}
