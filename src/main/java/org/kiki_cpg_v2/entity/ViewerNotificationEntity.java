/**
 * @author Anjana Thrishakya
 * Nov 12, 2020
 * 6:48:14 AM
 */
package org.kiki_cpg_v2.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "viewer_notifications")
public class ViewerNotificationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rec_id")
	private Integer id;

	@Column(name = "viewer_id")
	private Integer viewerId;

	@Column(name = "message_content")
	private String messageContent;

	@Column(name = "content_type")
	private Integer contentType;

	@Column(name = "message_time")
	private Date messageTime;

	@Column(name = "status")
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public Date getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
