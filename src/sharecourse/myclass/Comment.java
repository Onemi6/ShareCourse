package sharecourse.myclass;

public class Comment {
	
	private String bydynamicnum; //相关动态
	public void setBydynamicnum(String bydynamicnum) {
		this.bydynamicnum = bydynamicnum;
	}

	public String getBydynamicnum() {
		return bydynamicnum;
	}
	
	private String questioner;// 回复对象

	public void setQuestioner(String questioner) {
		this.questioner = questioner;
	}

	public String getQuestioner() {
		return questioner;
	}

	private String respondent; // 回复者

	public void setRespondent(String respondent) {
		this.respondent = respondent;
	}

	public String getRespondent() {
		return respondent;
	}

	private String replytext;// 回复内容

	public void setReplytext(String replytext) {
		this.replytext = replytext;
	}
	
	public String getReplytext() {
		return replytext;
	}
	
	private String replytime;// 回复时间

	public void setReplytime(String replytime) {
		this.replytime = replytime;
	}
	
	public String getReplytime() {
		return replytime;
	}
}