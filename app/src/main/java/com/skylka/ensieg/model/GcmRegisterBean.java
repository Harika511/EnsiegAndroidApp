package com.skylka.ensieg.model;

public class GcmRegisterBean {
	private String gcmId;
	static GcmRegisterBean instance;

	public String getGcmId() {
		return gcmId;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	private GcmRegisterBean() {

	}

	public static GcmRegisterBean getGcmBeanInstance() {
		if (instance == null) {
			instance = new GcmRegisterBean();

		}
		return instance;
	}
}
