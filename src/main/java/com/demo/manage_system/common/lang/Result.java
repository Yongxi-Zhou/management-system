package com.demo.manage_system.common.lang;

import java.io.Serializable;
import lombok.Data;

@Data
public class Result implements Serializable {

	private int code;
	private String msg;
	private Object data;

	public static Result succ(Object data) {
		return succ(200, "操作成功", data);
	}

	public static Result succ(int code, String msg, Object data) {
		Result r = new Result();
		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}

	public static Result fail(String msg) {
		return fail(400, msg, null);
	}

	public static Result fail(int code, String msg, Object data) {
		Result r = new Result();
		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}

}
