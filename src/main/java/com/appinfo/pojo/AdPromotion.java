package com.appinfo.pojo;

import java.util.Date;

public class AdPromotion {
	private int id;
	private int appId;// appId����Դ�ڣ�app_info�������id��
	private String adPicPath;// ���ͼƬ�洢·��
	private int adPV;// �������
	private int carouselPosition;// �ֲ�λ��1-n��
	private Date startTime;// ��Чʱ��
	private Date endTime;// ʧЧʱ��
	private int createdBy;// �����ߣ���Դ��backend_user�û�����û�id��
	private Date creationDate;// ����ʱ��
	private int modifyBy;// �����ߣ���Դ��backend_user�û�����û�id��
	private Date modifyDate;// ���¸���ʱ��

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAdPicPath() {
		return adPicPath;
	}

	public void setAdPicPath(String adPicPath) {
		this.adPicPath = adPicPath;
	}

	public int getAdPV() {
		return adPV;
	}

	public void setAdPV(int adPV) {
		this.adPV = adPV;
	}

	public int getCarouselPosition() {
		return carouselPosition;
	}

	public void setCarouselPosition(int carouselPosition) {
		this.carouselPosition = carouselPosition;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

}
