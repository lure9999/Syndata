package com.gas.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult implements Serializable{
	private static final long serialVersionUID = 6117065027783179235L;
	private Integer state=1;//响应状�??1 ok,0 error
	private String message="ok";//响应信息
	private Object data;//响应对象数据
	
	
	public JsonResult() {
		super();
	}
	public JsonResult(String message) {
		super();
		this.message = message;
	}
	public JsonResult(Object data) {
		super();
		this.data = data;
	}
	
	public JsonResult(Throwable e) {
		super();
		this.state=0;
		this.message=e.getMessage();
	}

	
}
