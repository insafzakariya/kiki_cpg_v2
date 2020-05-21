package org.kiki_cpg_v2.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "viewers")
public class ViewerEntity implements Serializable {

	private static final long serialVersionUID = 5113755443586979871L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ViewerID")
	private Integer id;

	@Column(name = "Username")
	private String username;

	@Column(name = "RegistrationType")
	private Integer registrationType;

	@Column(name = "MobileNumber")
	private String mobileNumber;

	@Column(name = "Password")
	private String password;

	@Column(name = "Name")
	private String name;

	@Column(name = "DateOfBirth")
	private Date dateOfBirth;

	@Column(name = "Gender")
	private String gender;

	@Column(name = "Language")
	private String language;

	@Column(name = "SocialType")
	private String socialType;

	@Column(name = "SocialToken")
	private String socialToken;

	@Column(name = "Location")
	private String location;

	@Column(name = "SocialUserID")
	private String socialUserID;

	@Column(name = "LastUpdatedTime")
	private Date lastUpdatedTime;

	@Column(name = "CreatedTime")
	private Date createdTime;

	@Column(name = "Email")
	private String email;

	@Column(name = "RandomNumber")
	private String randomNumber;

	@Column(name = "Verified")
	private String verified;

	@Column(name = "ViewerTypeID")
	private Integer viewerTypeID;

	@Column(name = "Country")
	private String country;

	@Column(name = "DeviceID")
	private String deviceId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(Integer registrationType) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
