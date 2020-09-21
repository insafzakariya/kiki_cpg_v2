package org.kiki_cpg_v2.controller.factory;

import org.kiki_cpg_v2.dto.InvoiceDetailsForNotificationEmailDto;
import org.kiki_cpg_v2.entity.CardInvoiceEntity;
import org.kiki_cpg_v2.entity.InvoiceEntity;
import org.kiki_cpg_v2.entity.MerchantAccountEntity;
import org.kiki_cpg_v2.entity.SubscriptionInvoiceEntity;
import org.kiki_cpg_v2.service.CardInvoiceService;
import org.kiki_cpg_v2.service.InvoiceService;
import org.kiki_cpg_v2.service.MerchantAccountService;
import org.kiki_cpg_v2.service.SubscriptionInvoiceService;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class InvoiceFactoryController {

	@Autowired
	InvoiceService invoiceService;

	@Autowired
	MerchantAccountService merchantAccountService;

	@Autowired
	SubscriptionInvoiceService subscriptionInvoiceService;

	@Autowired
	CardInvoiceService cardInvoiceService;

	public InvoiceDetailsForNotificationEmailDto getInvoiceDetails(Integer invoiceId, Integer PaymentMethodId) {
		InvoiceDetailsForNotificationEmailDto invoiceDetailsForNotificationEmailDto = new InvoiceDetailsForNotificationEmailDto();
		try {
			if (PaymentMethodId == 4) {// Dialog
				InvoiceEntity invoiceEntity = invoiceService.getInvoiceByIdAndSuccess(invoiceId, AppConstant.ACTIVE);
				invoiceDetailsForNotificationEmailDto.setAmount(invoiceEntity.getAmount());
				invoiceDetailsForNotificationEmailDto.setInvoiceId(invoiceEntity.getId());
				invoiceDetailsForNotificationEmailDto.setCreateDate(invoiceEntity.getCreatedDate());
			} else if (PaymentMethodId == 5) {// Mobitel
				MerchantAccountEntity merchantAccountEntity = merchantAccountService.getMerchantAccountById(invoiceId);
				invoiceDetailsForNotificationEmailDto.setAmount(merchantAccountEntity.getAmount());
				invoiceDetailsForNotificationEmailDto.setInvoiceId(merchantAccountEntity.getId());
				invoiceDetailsForNotificationEmailDto.setCreateDate(merchantAccountEntity.getDate());
			} else if (PaymentMethodId == 9) {// KEELLS
				SubscriptionInvoiceEntity subscriptionInvoiceEntity = subscriptionInvoiceService
						.getSubscriptionInvoiceById(invoiceId, AppConstant.ACCEPT, AppConstant.KEELLS);
				invoiceDetailsForNotificationEmailDto.setAmount(subscriptionInvoiceEntity.getAmount());
				invoiceDetailsForNotificationEmailDto.setInvoiceId(subscriptionInvoiceEntity.getId());
				invoiceDetailsForNotificationEmailDto.setCreateDate(subscriptionInvoiceEntity.getCreatedDate());
			} else if (PaymentMethodId == 10) {// HNB
				CardInvoiceEntity cardInvoiceEntity = cardInvoiceService.getInvoiceByIdAndDicision(invoiceId,
						AppConstant.ACCEPT);
				invoiceDetailsForNotificationEmailDto.setAmount(cardInvoiceEntity.getAmount());
				invoiceDetailsForNotificationEmailDto.setInvoiceId(cardInvoiceEntity.getId());
				invoiceDetailsForNotificationEmailDto.setCreateDate(cardInvoiceEntity.getCreatedDate());
			} else if (PaymentMethodId == 11) {// Hutch
				SubscriptionInvoiceEntity subscriptionInvoiceEntity = subscriptionInvoiceService
						.getSubscriptionInvoiceById(invoiceId, AppConstant.ACCEPT, AppConstant.KEELLS);
				invoiceDetailsForNotificationEmailDto.setAmount(subscriptionInvoiceEntity.getAmount());
				invoiceDetailsForNotificationEmailDto.setInvoiceId(subscriptionInvoiceEntity.getId());
				invoiceDetailsForNotificationEmailDto.setCreateDate(subscriptionInvoiceEntity.getCreatedDate());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invoiceDetailsForNotificationEmailDto;
	}
}
