package my.chat.app.data;

import java.util.Date;

public class MyMessage {
	private String message;
	private Date createdAt;

	public MyMessage() {
		message = "...";
		createdAt = new Date();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	//TODO: Update from client side
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String format(String userName) {
		return userName+": ["+createdAt+" ] "+message;
	}

}
