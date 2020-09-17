/**
 * @DaJun 25, 2020 @NotificationDto.java
 */
package org.kiki_cpg_v2.dto;

import java.util.ArrayList;

/**
 * @author Anjana Thrishakya
 */
public class NotificationDto {

	private ArrayList<String> deviceid;
	private String title;
	private String body;
	private String image_url;
	private String type;
	private String content_type;
	private String content_id;
	private String date_time;

	public ArrayList<String> getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(ArrayList<String> deviceid) {
		this.deviceid = deviceid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public String getContent_id() {
		return content_id;
	}

	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

	@Override
	public String toString() {
		return "NotificationDto [deviceid=" + deviceid + ", title=" + title + ", body=" + body + ", image_url="
				+ image_url + ", type=" + type + ", content_type=" + content_type + ", content_id=" + content_id
				+ ", date_time=" + date_time + "]";
	}

}
