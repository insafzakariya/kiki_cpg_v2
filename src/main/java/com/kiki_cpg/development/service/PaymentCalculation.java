package com.kiki_cpg.development.service;

import java.util.List;

import com.kiki_cpg.development.entity.Ideabiz;
import com.kiki_cpg.development.entity.Viewers;

public interface PaymentCalculation {

	public void proceedPendingIdiabizPayment(String cronStartTime, List<Ideabiz> getSubList, List<Viewers> getViewers, Integer cronId);
	
	public Double getAmountByDays(Integer day);
}
