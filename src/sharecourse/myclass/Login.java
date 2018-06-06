package sharecourse.myclass;


public class Login {
	private String userId;
	private String contactWay;
	private String userPswd;
	private String userType;
	public Login(String userId,String contactWay,String userPswd,String userType)
	{
		this.userId = userId;
		this.userPswd = userPswd;
		this.userType = userType;
		this.contactWay = contactWay;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPswd() {
		return userPswd;
	}
	public void setUserPswd(String userPswd) {
		this.userPswd = userPswd;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getContactWay() {
		return contactWay;
	}
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}
	
}
