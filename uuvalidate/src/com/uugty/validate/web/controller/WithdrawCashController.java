package com.uugty.validate.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uugty.validate.domain.WithdrawCash;
import com.uugty.validate.service.IWithdrawCashService;
import com.uugty.validate.utils.Page;
import com.uugty.validate.web.util.EasemobUtil;

@Controller("withDrawController")
@RequestMapping("/withdraw")
public class WithdrawCashController {

	@Resource(name = IWithdrawCashService.SERVER_NAME)
	private IWithdrawCashService withdrawCashService;

	@RequestMapping("/getAllWithdraw")
	public ModelAndView getAllWithdraw(WithdrawCash withdraw,
			Map<String, Object> model) {
		int totalSize = withdrawCashService.findAllWithdrawCount(withdraw);
		Page page = new Page(totalSize, withdraw.getCurrentPage());
		List<WithdrawCash> WithdrawCashlist = withdrawCashService
				.findAllWithdraw(withdraw, page);
		model.put("WithdrawCashlist", WithdrawCashlist);
		model.put("withdrawStatus", withdraw.getWithdrawStatus());
		model.put("page", page);
		return new ModelAndView("withdraw/getAllWithdraw", model);
	}

	@RequestMapping("/adoptWithdraw")
	public ModelAndView adoptWithdraw(WithdrawCash withdraw,
			Map<String, Object> model) {
		withdraw.setStatus("3");
		withdrawCashService.updateWithDrawStatus(withdraw);
		EasemobUtil.sendMessage(withdraw.getUserId(), "你的提现请求已经成功提现,请等待银行提款！！");
		return getAllWithdraw(withdraw, model);
	}

	@RequestMapping("/rejectWithdraw")
	public ModelAndView rejectWithdraw(WithdrawCash withdraw,
			Map<String, Object> model) {
		withdraw.setStatus("4");
		withdrawCashService.updateWithDrawStatus(withdraw);
		EasemobUtil.sendMessage(withdraw.getUserId(), "你的提现请求已经被拒绝,请查看提现信息！");
		return getAllWithdraw(withdraw, model);
	}
}
