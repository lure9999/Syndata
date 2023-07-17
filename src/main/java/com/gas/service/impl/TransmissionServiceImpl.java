package com.gas.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.gas.dao.TransmissionDao;
import com.gas.entity.TransLog;
import com.gas.service.TransmissionService;

@Service
public class TransmissionServiceImpl implements TransmissionService {
	@Value("${db.tables}")
	private String tables;

	@Autowired
	private TransmissionDao transmissionDao;

	@Override
	@DSTransactional
	public void dataTransfer() {
		// 拿到yml中配置的表名
		List<String> tableNameList = Arrays.asList(tables.split(","));

		for (String tableName : tableNameList) {
			// 获取从库表主键
			String primaryKey = transmissionDao.selectPrimaryKey(tableName);

			// 获取从库表字段
			List<String> columns = transmissionDao.selectColumns(tableName);

			// 获取主库表日志
			List<TransLog> primaryLogs = transmissionDao.selectPrimaryTransLog(primaryKey, tableName);
			//System.out.println(primaryLogs);
			if (primaryLogs == null || primaryLogs.size() == 0) {
				continue;
			}

			// 长度要一致 不然复制为null
			List<TransLog> primaryLogsCache = new ArrayList<>(Arrays.asList(new TransLog[primaryLogs.size()]));
			// 集合类复制,留一份全部数据
			Collections.copy(primaryLogsCache, primaryLogs);

			// 获取本地日志
			List<TransLog> secondaryLogs = transmissionDao.selectSecondaryTransLog(tableName);
			//System.out.println(secondaryLogs);

			// 求差集
			primaryLogs.removeAll(secondaryLogs);
			//System.out.println("差集：" + primaryLogs);
			if (primaryLogs == null || primaryLogs.size() == 0) {
				continue;
			}

			// 筛选出U和I操作
			List<TransLog> result = primaryLogs.stream().filter(item -> "U".equals(item.getSYS_CHANGE_OPERATION()) || "I".equals(item.getSYS_CHANGE_OPERATION())).collect(Collectors.toList());
			//System.out.println(result);
			if (result != null && result.size() != 0) {

				// 主库批量查出数据
				List<Map> newData = transmissionDao.selectDataBatch(tableName, primaryKey, result);

				// 组装新增数据
				List<List<Object>> buildSaveData = buildSaveData(newData, columns);
				//System.out.println(buildSaveData);

				// 从库批量删除
				transmissionDao.deleteDataBatch(tableName, primaryKey, result);
				transmissionDao.deleteDataBatchMysql("tb_"+tableName, primaryKey, result);

				// 从库批量新增
				//System.out.println(columns);
				transmissionDao.insertDataBatch(tableName, columns, buildSaveData);

				transmissionDao.insertDataBatchMysql("tb_"+tableName, columns, buildSaveData);
			}

			// 筛选出D操作
			List<TransLog> resultDelete = primaryLogs.stream().filter(item -> "D".equals(item.getSYS_CHANGE_OPERATION())).collect(Collectors.toList());
			//System.out.println("删除操作：" + resultDelete);
			if (resultDelete != null && resultDelete.size() != 0) {

				// 从库批量删除数据
				transmissionDao.deleteDataBatch2(tableName, primaryKey, resultDelete);
				transmissionDao.deleteDataBatch2Mysql("tb_"+tableName, primaryKey, resultDelete);
			}

			// 删除此表本地日志数据
			transmissionDao.deleteTransLog(tableName);

			// 新增此表本地日志数据
			for (TransLog transLog : primaryLogsCache) {
				transLog.setTABLE_NAME(tableName);
			}
			transmissionDao.insertTransLogBatch(primaryLogsCache);
		}

	}

	public List<List<Object>> buildSaveData(List<Map> maps, List<String> camelList) {
		List<List<Object>> dataList = maps.stream().map(t -> {
			List<Object> list = new ArrayList<>();
			camelList.stream().forEach(field -> {
				if (t.get(field) != null && t.get(field).toString().indexOf("-") > 0 && t.get(field).toString().indexOf(" ") > 0 && t.get(field).toString().indexOf(".") > 0 && t.get(field).toString().indexOf(":") > 0) {
					//如果是日期，toString一下
					list.add(t.get(field).toString());
				} else {
					list.add(t.get(field));
				}
			});
			return list;
		}).collect(Collectors.toList());
		return dataList;
	}

}
