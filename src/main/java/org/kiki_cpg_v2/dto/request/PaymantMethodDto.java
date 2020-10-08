/**
 * @DaOct 8, 2020 @PaymantMethodDto.java
 */
package org.kiki_cpg_v2.dto.request;

/**
 * @author Anjana Thrishakya
 */
public class PaymantMethodDto {

	private Integer id;
	private String image;
	private String name;
	private String description;
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
