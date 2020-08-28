/**
 * @DaJul 29, 2020 @HNBService.java
 */
package org.kiki_cpg_v2.service;

import java.util.Date;

import org.kiki_cpg_v2.dto.PaymentRefDto;
import org.kiki_cpg_v2.dto.request.HNBBeginDto;
import org.kiki_cpg_v2.dto.request.HNBVerifyDto;
import org.kiki_cpg_v2.entity.CardDataEntity;
import org.kiki_cpg_v2.entity.CardInvoiceEntity;
import org.springframework.util.MultiValueMap;

/**
 * @author Anjana Thrishakya
 */
public interface HNBService {

	/**
	 * @param hnbBeginDto
	 * @param isNew
	 * @param days
	 * @param value
	 * @return
	 * @throws Exception
	 */
	PaymentRefDto beginTransaction(HNBBeginDto hnbBeginDto, boolean isNew, Integer days, Double value) throws Exception;

	/**
	 * @param hnbBeginDto
	 * @return
	 * @throws Exception
	 */
	PaymentRefDto getPaymentRefDto(HNBBeginDto hnbBeginDto, Integer days, Double value) throws Exception;

	/**
	 * @param hnbBeginDto
	 * @param paymentRefDto
	 * @return
	 * @throws Exception
	 */
	CardInvoiceEntity getCardInvoiceEntityBegining(HNBBeginDto hnbBeginDto, PaymentRefDto paymentRefDto)
			throws Exception;

	/**
	 * @param formData
	 * @throws Exception 
	 */
	void saveTransaction(MultiValueMap<String, String> formData) throws Exception;


	/**
	 * @param cardInvoiceEntity
	 * @param formData
	 * @return
	 */
	CardInvoiceEntity getUpdatedCardInvoiceEntity(CardInvoiceEntity cardInvoiceEntity,
			MultiValueMap<String, String> formData) throws Exception;

	/**
	 * @param hnbBeginDto
	 * @param paymentRefDto
	 * @return
	 */
	CardDataEntity getCardDataEntityBegining(HNBBeginDto hnbBeginDto, PaymentRefDto paymentRefDto) throws Exception;

	/**
	 * @param cardDataEntity
	 * @param formData
	 * @return
	 */
	CardDataEntity getUpdatedCardDataEntity(CardDataEntity cardDataEntity, MultiValueMap<String, String> formData) throws Exception;

	/**
	 * @param viewerid
	 * @param mobile
	 * @return
	 */
	boolean processUnsubscription(Integer viewerid, String mobile) throws Exception;

	/**
	 * @param viewerid
	 * @param i
	 * @param date
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	boolean updateViewerSubscription(Integer viewerid, int i, Date date, String mobile) throws Exception;

	/**
	 * @param hnbVerifyDto
	 * @param b
	 * @return
	 */
	PaymentRefDto verifyTransaction(HNBVerifyDto hnbVerifyDto) throws Exception;


	/**
	 * @param cardDataEntity
	 * @param cronId 
	 * @return
	 * @throws Exception 
	 */
	String processSimpleOrderPayment(CardDataEntity cardDataEntity, Integer cronId) throws Exception;

	/**
	 * @param cardDataEntity
	 * @return
	 * @throws Exception 
	 */
	CardDataEntity updateCardDataEntityExpireDate(CardDataEntity cardDataEntity) throws Exception;

	

}
