package com.uugty.app.web.utils;

import java.util.Timer;
import java.util.TimerTask;

import com.uugty.app.service.IOrderService;
import com.uugty.app.service.impl.OrderServiceImpl;

/**
 * @ClassName: GratuityUnRecivedThread
 * @Description: 微信小费 如果用户未领取 则24小时后将钱返还给小费发送者
 * @author ganliang
 * @date 2015年9月7日 下午2:46:50
 */
public class GratuityUnRecivedThread extends Thread {

	private String outTradeNo;

	public GratuityUnRecivedThread(String outTradeNo) {
		super();
		this.outTradeNo = outTradeNo;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	@Override
	public void run() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				IOrderService orderService = new OrderServiceImpl();
				orderService.scheduleGratuityUnReceived(outTradeNo);
			}
		}, 1000 * 60 * 60 * 24);
	}
}
