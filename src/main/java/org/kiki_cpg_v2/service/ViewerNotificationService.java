/**
 * @author Anjana Thrishakya
 * Nov 12, 2020
 * 6:58:02 AM
 */
package org.kiki_cpg_v2.service;

/**
 *
 */
public interface ViewerNotificationService {
	
	boolean save(String message, Integer viewerId) throws Exception;

}
