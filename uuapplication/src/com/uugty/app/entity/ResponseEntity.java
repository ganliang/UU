package com.uugty.app.entity;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.uugty.app.constant.StringConstant;

/**
 * @ClassName: ResponseEntity
 * @Description: 返回到客户实体，并且将实体转换为json字符串
 * @author ganliang
 * @date 2015年6月12日 下午2:11:11
 */
public class ResponseEntity {

	private static Logger LOG = Logger.getLogger(ResponseEntity.class);

	private String STATUS;

	private String MSG;

	private Object OBJECT;

	private List<Object> LIST;

	public static final String SUCCESS_STATUS = "0";// 服务器ok
	public static final String WARN_STATUS = "1";// 服务器响应正常,但不是客户端要的,警告warn
	public static final String ERROR_STATUS = "2";// 服务器出现error
	public static final String NO_LOGIN = "3";// 用户未登录

	public ResponseEntity() {
		super();
	}

	public ResponseEntity(Object obj) {
		super();
		this.OBJECT = obj;
	}

	public ResponseEntity(List<Object> list) {
		super();
		this.LIST = list;
	}

	public ResponseEntity(String sTATUS, String mSG) {
		super();
		STATUS = sTATUS;
		MSG = mSG;
	}

	public ResponseEntity(String sTATUS, String mSG, Object obj) {
		super();
		STATUS = sTATUS;
		MSG = mSG;
		this.OBJECT = obj;
	}

	public ResponseEntity(String sTATUS, String mSG, List<Object> list) {
		super();
		STATUS = sTATUS;
		MSG = mSG;
		this.LIST = list;
	}

	public ResponseEntity(String sTATUS, String mSG, Object oBJECT,
			List<Object> lIST) {
		super();
		STATUS = sTATUS;
		MSG = mSG;
		OBJECT = oBJECT;
		LIST = lIST;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getMSG() {
		return MSG;
	}

	public void setMSG(String mSG) {
		MSG = mSG;
	}

	public Object getOBJECT() {
		return OBJECT;
	}

	public void setOBJECT(Object oBJECT) {
		OBJECT = oBJECT;
	}

	public List<Object> getLIST() {
		return LIST;
	}

	public void setLIST(List<Object> lIST) {
		LIST = lIST;
	}

	@Override
	public String toString() {
		return "ResponseEntity [STATUS=" + STATUS + ", MSG=" + MSG + "]";
	}

	/**
	 * 
	 * @Title: println
	 * @Description: 向客户端打印出服务端的状态
	 * @param @param request
	 * @param @param STATUS
	 * @param @param MSG
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static void println(HttpServletResponse response, String STATUS,
			String MSG) {
		try {
			PrintWriter out = response.getWriter();
			String json = JSONObject
					.fromObject(new ResponseEntity(STATUS, MSG)).toString();
			LOG.info(json);
			out.println(json);
			out.flush();
		} catch (IOException e) {
			LOG.error("从HttpServletResponse获取输出流出现异常");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Title: println
	 * @Description:向客户端返回SUCCESS状态
	 * @param @param response
	 * @return void 返回类型
	 * @throws
	 */
	public static void println(HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			String json = JSONObject.fromObject(
					new ResponseEntity(SUCCESS_STATUS, "SUCCESS")).toString();
			LOG.info(json);
			out.println(json);
			out.flush();
		} catch (IOException e) {
			LOG.error("从HttpServletResponse获取输出流出现异常");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Title: println
	 * @Description: 将对象封装成json,并返回到客户端
	 * @param @param response
	 * @param @param obj
	 * @return void 返回类型
	 * @throws
	 */
	@Deprecated
	public static void println2(HttpServletResponse response, Object obj) {
		try {
			PrintWriter out = response.getWriter();
			String jsonObject = JSONObject.fromObject(obj).toString();
			String statusJSON = JSONObject.fromObject(
					new ResponseEntity(SUCCESS_STATUS, "SUCCESS")).toString();
			jsonObject = jsonObject.substring(0,
					jsonObject.lastIndexOf(StringConstant.RIGHT_SPACE));
			statusJSON = statusJSON.substring(statusJSON
					.indexOf(StringConstant.LEFT_SPACE));
			String json = jsonObject + StringConstant.QUOTA + statusJSON;
			LOG.info(json);
			out.println(json);
			out.flush();
		} catch (IOException e) {
			LOG.error("从HttpServletResponse获取输出流出现异常");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Title: println
	 * @Description: 将一个对象和状态码一起封装成json
	 * @param @param response
	 * @param @param obj
	 * @return void 返回类型
	 * @throws
	 */
	public static void println(HttpServletResponse response, Object obj) {
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = JSONObject.fromObject(new ResponseEntity(
					SUCCESS_STATUS, "SUCCESS", obj));
			LOG.info(json.toString());
			out.print(json.toString());
			out.flush();
		} catch (IOException e) {
			LOG.error("从HttpServletResponse获取输出流出现异常");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Title: println
	 * @Description: 将对象集合封装成json
	 * @param @param response
	 * @param @param list
	 * @return void 返回类型
	 * @throws
	 */
	public static void printlns(HttpServletResponse response, List<Object> list) {
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = JSONObject.fromObject(new ResponseEntity(
					SUCCESS_STATUS, "SUCCESS", list));
			LOG.info(json.toString());
			out.println(json.toString());
			out.flush();
		} catch (IOException e) {
			LOG.error("从HttpServletResponse获取输出流出现异常");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Title: println
	 * @Description: 将对象封装到json中去
	 * @param @param response
	 * @param @param obj
	 * @param @param list
	 * @return void 返回类型
	 * @throws
	 */
	public static void println(HttpServletResponse response, Object obj,
			List<Object> list) {
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = JSONObject.fromObject(new ResponseEntity(
					SUCCESS_STATUS, "SUCCESS", obj, list));
			LOG.info(json.toString());
			out.println(json.toString());
			out.flush();
		} catch (IOException e) {
			LOG.error("从HttpServletResponse获取输出流出现异常");
			throw new RuntimeException(e);
		}
	}

	public static void println(HttpServletResponse response, String mSG2,
			Object obj, List<Object> list) {
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = null;
			if ("".equals(mSG2)) {
				json = JSONObject.fromObject(new ResponseEntity(SUCCESS_STATUS,
						mSG2, obj, list));
			} else {
				json = JSONObject.fromObject(new ResponseEntity(WARN_STATUS,
						mSG2, obj, list));
			}
			LOG.info(json.toString());
			out.println(json.toString());
			out.flush();
		} catch (IOException e) {
			LOG.error("从HttpServletResponse获取输出流出现异常");
			throw new RuntimeException(e);
		}
	}
}
