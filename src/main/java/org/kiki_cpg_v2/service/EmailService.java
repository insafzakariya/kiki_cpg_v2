package org.kiki_cpg_v2.service;

public interface EmailService {

	public boolean sendSimpleMessage(String to, String subject, String text);
}
