package sharecourse.myclass;

public class Comment {
	
	private String bydynamicnum; //��ض�̬
	public void setBydynamicnum(String bydynamicnum) {
		this.bydynamicnum = bydynamicnum;
	}

	public String getBydynamicnum() {
		return bydynamicnum;
	}
	
	private String questioner;// �ظ�����

	public void setQuestioner(String questioner) {
		this.questioner = questioner;
	}

	public String getQuestioner() {
		return questioner;
	}

	private String respondent; // �ظ���

	public void setRespondent(String respondent) {
		this.respondent = respondent;
	}

	public String getRespondent() {
		return respondent;
	}

	private String replytext;// �ظ�����

	public void setReplytext(String replytext) {
		this.replytext = replytext;
	}
	
	public String getReplytext() {
		return replytext;
	}
	
	private String replytime;// �ظ�ʱ��

	public void setReplytime(String replytime) {
		this.replytime = replytime;
	}
	
	public String getReplytime() {
		return replytime;
	}
}