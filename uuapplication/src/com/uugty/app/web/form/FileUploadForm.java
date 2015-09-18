package com.uugty.app.web.form;

/**
 * @ClassName: FileUploadForm
 * @Description: 保存文件上传的时候字段
 * @author ganliang
 * @date 2015年6月11日 下午6:08:02
 */
public class FileUploadForm {
	/**
	 * 改变用户的头像
	 */
	public static final String CHANGE_USER_AVATAR = "0";

	/**
	 * 改变用户的身份证
	 */
	public static final String VALIDATE_USER_IDENTITY = "1";

	/**
	 * 改变用户的学历证
	 */
	public static final String VALIDATE_USER_CERTIFICATE = "2";

	/**
	 * 改变用户的导游证
	 */
	public static final String VALIDATE_USER_TOUR_CARD = "3";

	/**
	 * 车辆的照片
	 */
	public static final String FILEUPLOAD_CAR_IMAGE = "4";

	/**
	 * 生活照
	 */
	public static final String FILEUPLOAD_LIFE_DEST = "5";

	/** 路线描述照片 */
	public static final String FILEUPLOAD_DESCRIBE_ROADLINE_DEST = "6";
	/** 订单评价图片 */
	public static final String FILEUPLOAD_ORDER_EVALUATION_DEST = "7";
	/** 驾驶证 */
	public static final String FILEUPLOAD_CAR_DRIVER_IMAGE_DEST = "8";
	/** 行驶证 */
	public static final String FILEUPLOAD_CAR_DRIVING_IMAGE_DEST = "9";
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
