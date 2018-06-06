package sharecourse.myclass;

import android.app.Application;

public class MyApplication extends Application{
	private String phoneNum;
	private String userNum;
	private String userType;
	private String passWord;
	private String userName;
	private String dynamicNum;
	private boolean flagCheck;
	
	public boolean isFlagCheck() {
		return flagCheck;
	}
	public void setFlagCheck(boolean flagCheck) {
		this.flagCheck = flagCheck;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getDynamicNum() {
		return dynamicNum;
	}
	public void setDynamicNum(String dynamicNum) {
		this.dynamicNum = dynamicNum;
	}
}
