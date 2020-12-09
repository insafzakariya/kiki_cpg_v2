/**
 * @author Anjana Thrishakya
 * Nov 3, 2020
 * 12:29:45 PM
 */
package org.kiki_cpg_v2.client;

import java.util.HashMap;

/**
 *
 */
public interface AppleClient {
	
	public HashMap<String, Object> verify(String receipt) throws Exception;

}
