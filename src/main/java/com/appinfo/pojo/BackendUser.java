package com.appinfo.pojo;

import java.util.Date;

public class BackendUser {
	private int id;             
	private String userCode;//�û�����
	private String userName;//�û�����
	private int userType;//�û���ɫ���ͣ���Դ�������ֵ����Ϊ�����ܡ������г�����Ӫ�����ۣ�
	private int createdBy;//�����ߣ���Դ��backend_user�û�����û�id��
	private Date creationDate;//����ʱ��
	private int modifyBy;//�����ߣ���Դ��backend_user�û�����û�id��
	private Date modifyDate;//���¸���ʱ��
	private String userPassword;//�û�����
	private String userTypeName;
	
	public String getUserTypeName() {
		return userTypeName;
	}
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
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
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	

}
