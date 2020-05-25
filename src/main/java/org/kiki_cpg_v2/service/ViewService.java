package org.kiki_cpg_v2.service;

import org.springframework.web.servlet.ModelAndView;

public interface ViewService {

	ModelAndView navigateHome(String paymentToken, String type) throws Exception;

}
