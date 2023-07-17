package com.gas.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gas.entity.TransLog;

public interface TransmissionDao {

	Map dataTest();

	@DS("slave")
	void insertLocal(Map dataTest);

	@DS("slave")
	String selectPrimaryKey(@Param("tableName") String tableName);

	@DS("slave")
	List<String> selectColumns(@Param("tableName") String tableName);

	List<TransLog> selectPrimaryTransLog(@Param("primaryKey") String primaryKey, @Param("tableName") String tableName);

	@DS("slave")
	List<TransLog> selectSecondaryTransLog(@Param("tableName") String tableName);

	List<Map> selectDataBatch(@Param("tableName") String tableName, @Param("primaryKey") String primaryKey, @Param("result") List<TransLog> result);

	@DS("slave")
	void deleteDataBatch(@Param("tableName") String tableName, @Param("primaryKey") String primaryKey, @Param("result") List<TransLog> result);


	@DS("customer")
	void deleteDataBatchMysql(@Param("tableName") String tableName, @Param("primaryKey") String primaryKey, @Param("result") List<TransLog> result);





	@DS("slave")
	void insertDataBatch(@Param("tableName") String tableName, @Param("columns") List<String> columns, @Param("newData") List<List<Object>> newData);

	@DS("customer")
	void insertDataBatchMysql(@Param("tableName") String tableName, @Param("columns") List<String> columns, @Param("newData") List<List<Object>> newData);


	@DS("slave")
	void deleteTransLog(@Param("tableName") String tableName);

	@DS("slave")
	void insertTransLogBatch(List<TransLog> primaryLogs);

	@DS("slave")
	void deleteDataBatch2(@Param("tableName") String tableName, @Param("primaryKey") String primaryKey, @Param("result") List<TransLog> resultDelete);

	@DS("customer")
	void deleteDataBatch2Mysql(@Param("tableName") String tableName, @Param("primaryKey") String primaryKey, @Param("result") List<TransLog> resultDelete);


}
