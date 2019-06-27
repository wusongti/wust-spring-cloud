package com.wust.springcloud.common.entity.qrtz.jobandtrigger;


import com.wust.springcloud.common.entity.qrtz.crontriggers.QrtzCronTriggers;
import com.wust.springcloud.common.entity.qrtz.jobdetails.QrtzJobDetails;
import com.wust.springcloud.common.entity.qrtz.triggers.QrtzTriggers;

public class QrtzJobAndTrigger implements java.io.Serializable{
	private static final long serialVersionUID = 3539074515841998274L;

	private QrtzJobDetails qrtzJobDetails;

	private QrtzTriggers qrtzTriggers;

	private QrtzCronTriggers qrtzCronTriggers;

	public QrtzJobDetails getQrtzJobDetails() {
		return qrtzJobDetails;
	}

	public void setQrtzJobDetails(QrtzJobDetails qrtzJobDetails) {
		this.qrtzJobDetails = qrtzJobDetails;
	}

	public QrtzTriggers getQrtzTriggers() {
		return qrtzTriggers;
	}

	public void setQrtzTriggers(QrtzTriggers qrtzTriggers) {
		this.qrtzTriggers = qrtzTriggers;
	}

	public QrtzCronTriggers getQrtzCronTriggers() {
		return qrtzCronTriggers;
	}

	public void setQrtzCronTriggers(QrtzCronTriggers qrtzCronTriggers) {
		this.qrtzCronTriggers = qrtzCronTriggers;
	}
}
