package com.skylka.ensieg.model;

import java.io.Serializable;

public class MessageModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8136994035787071864L;
	private String sender;
	private String message;
	private String isMine;
	private String commentId;

	public MessageModel(String sender, String message, String isMine) {
		this.sender = sender;
		this.message = message;
		this.isMine = isMine;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String isMine() {
		return isMine;
	}

	public void setMine(String isMine) {
		this.isMine = isMine;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

}
