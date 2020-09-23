/**
 * @DaSep 9, 2020 @SubscriptionServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import org.kiki_cpg_v2.entity.CardInvoiceEntity;
import org.kiki_cpg_v2.repository.CardInvoiceRepository;
import org.kiki_cpg_v2.service.CardInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardInvoiceServiceImpl implements CardInvoiceService {

	@Autowired
	CardInvoiceRepository cardInvoiceRepository;

	@Override
	public CardInvoiceEntity getInvoiceByIdAndDicision(Integer invoiceId, String Accept) {
		return cardInvoiceRepository.findByIdAndDecision(invoiceId, Accept);
	}
}
