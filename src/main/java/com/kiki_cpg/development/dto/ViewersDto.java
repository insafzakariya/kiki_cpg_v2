package com.kiki_cpg.development.dto;

import java.util.Date;

import javax.persistence.Column;

public class ViewersDto {

	private Integer viewerId;

	private String username;

	private int registrationType;

	private String mobileNumber;

	private String password;

	private String name;

	private Date dateOfBirth;

	private String gender;

	private String language;

	private String socialType;

	private String socialToken;

	private String location;

	private String socialUserID;

	private Date lastUpdatedTime;

	private Date createdTime;

	private String email;

	private String randomNumber;

	private String verified;

	private Integer viewerTypeID;

	private String country;

	private String deviceId;

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(int registrationType) {
		this.registrationType = registrationType;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSocialType() {
		return socialType;
	}

	public void setSocialType(String socialType) {
		this.socialType = socialType;
	}

	public String getSocialToken() {
		return socialToken;
	}

	public void setSocialToken(String socialToken) {
		this.socialToken = socialToken;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSocialUserID() {
		return socialUserID;
	}

	public void setSocialUserID(String socialUserID) {
		this.socialUserID = socialUserID;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRandomNumber() {
		return randomNumber;
	}

	public void setRandomNumber(String randomNumber) {
		this.randomNumber = randomNumber;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	public Integer getViewerTypeID() {
		return viewerTypeID;
	}

	public void setViewerTypeID(Integer viewerTypeID) {
		this.viewerTypeID = viewerTypeID;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	
}
