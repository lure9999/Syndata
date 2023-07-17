package com.gas.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.api.R;
import com.gas.service.TransmissionService;

@Controller
@RequestMapping("transmission")
@Api(value = "数据同步", tags = {"数据同步"})
public class TransmissionController {
//	@Autowired
//	private TransmissionDao transmissionDao;

	@Autowired
	private TransmissionService transmissionService;

	@Autowired
	DataSourceTransactionManager dataSourceTransactionManager;

	@Autowired
	TransactionDefinition transactionDefinition;
	TransactionStatus transactionStatus = null;

	/*
	 * 首页
	 */
	@RequestMapping("/toStarter")
	public String toStarter() {
		return "transmission-starter";
	}

//	public Map dataTest() {
//		Map dataTest = transmissionDao.dataTest();
//		transmissionDao.insertLocal(dataTest);
//		return dataTest;
//	}

	@GetMapping("/dataTest")
	@ResponseBody
	//每分钟执行一次
	//@Scheduled(cron = "0 */1 * * * ?")
	//每半个小时执行一次
	@Scheduled(cron = "0 0/30 * * * ?")
	@ApiOperation(value = "同步到从库和mysql", notes = "同步到从库和mysql")
	public R dataTransfer() {
		synchronized (TransmissionController.class) {
			transmissionService.dataTransfer();
		}
		return R.ok("成功");
	}

}
