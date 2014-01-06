package com.surferway.android.firemap;

public class SMSData {
	
	// Number from witch the sms was send
	private String number;
	// SMS text body
	private String body;
	// SMS text date
	private String smsdate;
	// SMS text day
	private String smsday;
	// SMS text dispatch
	private String dispatch;
	// SMS text department
	private String department;	
	// SMS text call type
	private String calltype;	
	// SMS text address
	private String address;
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getDate() {
		return smsdate;
	}
	
	public void setDate(String smsdate) {
		this.smsdate = smsdate;
	}
	
	public String getDay() {
		return smsday;
	}
	
	public void setDay(String smsday) {
		this.smsday = smsday;
	}
	
	public String getDispatch() {
		return dispatch;
	}
	
	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getCallType() {
		return calltype;
	}
	
	public void setCallType(String calltype) {
		this.calltype = calltype;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	

}
