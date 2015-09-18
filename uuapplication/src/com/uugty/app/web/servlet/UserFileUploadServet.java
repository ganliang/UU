package com.uugty.app.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.uugty.app.constant.StringConstant;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.PropertiesUtil;
import com.uugty.app.utils.StringUtil;
import com.uugty.app.utils.WebUtil;
import com.uugty.app.web.form.FileUploadForm;

/**
 * 
 * @ClassName: FileUploadServet
 * @Description: 文件上传的接口
 * @author ganliang
 * @date 2015年6月6日 下午3:48:43
 */
@WebServlet(urlPatterns = { "/userFileupload.do" }, asyncSupported = false, loadOnStartup = 10)
public class UserFileUploadServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger
			.getLogger(UserFileUploadServet.class);

	private static String IMAGEURI = "";// 图片的地址
	private static String FILEUPLOAD_ACATAR_DEST = "";// 头像上传
	private static String FILEUPLOAD_IDENTITY_DEST = ""; // 身份证上传
	private static String FILEUPLOAD_CERTIFICATE_DEST = "";// 学历证上传
	private static String FILEUPLOAD_TOUR_CARD_DEST = "";// 导游证上传
	private static String FILEUPLOAD_CAR_DEST = "";// 车辆
	private static String FILEUPLOAD_LIFE_DEST = "";// 生活照
	private static String FILEUPLOAD_ROADLINE_DESCRIBE_DEST = "";// 路线描述的图片保存的地址

	private static String FILEUPLOAD_ORDER_EVALUATION_DEST = "";// 评论地址

	@Override
	public void init() throws ServletException {
		super.init();
		Properties properties = PropertiesUtil.getProperties("file.properties");
		File dir = null;
		// 头像
		IMAGEURI = properties.getProperty("images_uri");

		FILEUPLOAD_ACATAR_DEST = properties
				.getProperty("fileupload_acatar_dest");
		if (FILEUPLOAD_ACATAR_DEST != null) {
			dir = new File(IMAGEURI + FILEUPLOAD_ACATAR_DEST);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		LOG.info("头像上传路径------>>>>" + dir.getAbsolutePath());
		// 身份证
		FILEUPLOAD_IDENTITY_DEST = properties
				.getProperty("fileupload_identity_dest");
		if (FILEUPLOAD_IDENTITY_DEST != null) {
			dir = new File(IMAGEURI + FILEUPLOAD_IDENTITY_DEST);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		LOG.info("身份证上传路径------>>>>" + dir.getAbsolutePath());
		// 学历证
		FILEUPLOAD_CERTIFICATE_DEST = properties
				.getProperty("fileupload_certificate_dest");
		if (FILEUPLOAD_CERTIFICATE_DEST != null) {
			dir = new File(IMAGEURI + FILEUPLOAD_CERTIFICATE_DEST);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		LOG.info("学历证上传路径------>>>>" + dir.getAbsolutePath());
		// 导游证
		FILEUPLOAD_TOUR_CARD_DEST = properties
				.getProperty("fileupload_tour_card_dest");
		if (FILEUPLOAD_TOUR_CARD_DEST != null) {
			dir = new File(IMAGEURI + FILEUPLOAD_TOUR_CARD_DEST);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		LOG.info("导游证上传路径------>>>>" + dir.getAbsolutePath());
		// 车辆
		FILEUPLOAD_CAR_DEST = properties.getProperty("fileupload_car_dest");
		if (FILEUPLOAD_CAR_DEST != null) {
			dir = new File(IMAGEURI + FILEUPLOAD_CAR_DEST);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		LOG.info("车辆图片上传路径------>>>>" + dir.getAbsolutePath());
		// 生活照
		FILEUPLOAD_LIFE_DEST = properties.getProperty("fileupload_life_dest");
		if (FILEUPLOAD_LIFE_DEST != null) {
			dir = new File(IMAGEURI + FILEUPLOAD_LIFE_DEST);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		LOG.info("生活照上传路径------>>>>" + dir.getAbsolutePath());
		FILEUPLOAD_ROADLINE_DESCRIBE_DEST = properties
				.getProperty("fileupload_roadline_describe_dest");
		dir = new File(IMAGEURI, FILEUPLOAD_ROADLINE_DESCRIBE_DEST);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		LOG.info("路线描述图片的地址---->>>" + dir.getAbsolutePath());

		FILEUPLOAD_ORDER_EVALUATION_DEST = properties
				.getProperty("fileupload_order_evaluation_dest");
		dir = new File(IMAGEURI, FILEUPLOAD_ORDER_EVALUATION_DEST);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		LOG.info("订单评价图片的地址---->>>" + dir.getAbsolutePath());
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = "";// 文件保存的路径
		String imageURL = "";

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		try {
			// 是上传文件
			if (isMultipart) {
				final TUser user = new TUser();
				TUser sessionUser = WebUtil.getUserFromSession(request);
				user.setUserId(sessionUser.getUserId());

				FileUploadForm fileupload = (FileUploadForm) BeanUtil.populate(
						request, FileUploadForm.class);
				// 解析request请求
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				Iterator<?> items = upload.parseRequest(request).iterator();
				FileItem item = null;
				StringBuilder builder = new StringBuilder();
				String userCar = null;
				String imageName = null;
				while (items.hasNext()) {
					item = (FileItem) items.next();
					// 该字段是普通字段
					if (item.isFormField()) {
						break;
					}
					// 获取文件上传的类型
					if (fileupload != null) {
						String name = item.getName();
						String prefix = name.substring(name.lastIndexOf("."));
						imageName = StringUtil.getFileName() + prefix;
						switch (fileupload.getType()) {
						// 头像上传
						case FileUploadForm.CHANGE_USER_AVATAR:
							imageURL = FILEUPLOAD_ACATAR_DEST + imageName;
							user.setUserAvatar(imageURL);
							path = IMAGEURI + FILEUPLOAD_ACATAR_DEST
									+ imageName;
							break;
						// 身份证上传
						case FileUploadForm.VALIDATE_USER_IDENTITY:
							imageURL = FILEUPLOAD_IDENTITY_DEST + imageName;
							user.setUserIdentity(imageURL);
							path = IMAGEURI + FILEUPLOAD_IDENTITY_DEST
									+ imageName;
							user.setUserIdValidate(TUser.VALIDATE_RUNING);
							user.setUserIdValidateDate(new Date());
							break;
						// 学历证上传
						case FileUploadForm.VALIDATE_USER_CERTIFICATE:
							imageURL = FILEUPLOAD_CERTIFICATE_DEST + imageName;
							user.setUserCertificate(imageURL);
							path = IMAGEURI + FILEUPLOAD_CERTIFICATE_DEST
									+ imageName;
							user.setUserCertificateValidate(1);
							user.setUserCertificateValidateDate(new Date());
							break;
						// 导游证上传
						case FileUploadForm.VALIDATE_USER_TOUR_CARD:
							imageURL = FILEUPLOAD_TOUR_CARD_DEST + imageName;
							user.setUserTourCard(imageURL);
							path = IMAGEURI + FILEUPLOAD_TOUR_CARD_DEST
									+ imageName;
							user.setUserTourValidate(TUser.VALIDATE_RUNING);
							user.setUserTourValidateDate(new Date());
							break;
						// 车辆的照片
						case FileUploadForm.FILEUPLOAD_CAR_IMAGE:
							imageURL = FILEUPLOAD_CAR_DEST + imageName;
							path = IMAGEURI + FILEUPLOAD_CAR_DEST + imageName;

							user.setUserCar(imageURL);
							user.setUserCarValidate(TUser.VALIDATE_RUNING);
							user.setUserCarValidateDate(new Date());
							break;
						// 生活照
						case FileUploadForm.FILEUPLOAD_LIFE_DEST:
							path = IMAGEURI + FILEUPLOAD_LIFE_DEST + imageName;
							imageURL = FILEUPLOAD_LIFE_DEST + imageName;
							builder.append(FILEUPLOAD_LIFE_DEST + imageName
									+ ",");
							break;
						// 路线照片
						case FileUploadForm.FILEUPLOAD_DESCRIBE_ROADLINE_DEST:
							path = IMAGEURI + FILEUPLOAD_ROADLINE_DESCRIBE_DEST
									+ name;
							imageURL = FILEUPLOAD_ROADLINE_DESCRIBE_DEST + name;
							break;
						// 订单评价照片
						case FileUploadForm.FILEUPLOAD_ORDER_EVALUATION_DEST:
							path = IMAGEURI + FILEUPLOAD_ORDER_EVALUATION_DEST
									+ imageName;
							imageURL = FILEUPLOAD_ORDER_EVALUATION_DEST
									+ imageName;
							break;
						// 驾驶证照片
						case FileUploadForm.FILEUPLOAD_CAR_DRIVER_IMAGE_DEST:
							path = IMAGEURI + FILEUPLOAD_CAR_DEST + imageName;
							imageURL = FILEUPLOAD_CAR_DEST + imageName;

							userCar = user.getUserCar();
							if (!"".equals(userCar)) {
								String[] cars = userCar.split(",");
								userCar = imageURL + "," + cars[1] + ","
										+ cars[2];
							} else {
								userCar = imageURL + ",,";// 驾驶证 行驶证 车辆
								user.setUserCarValidate(TUser.VALIDATE_RUNING);
								user.setUserCarValidateDate(new Date());
							}
							user.setUserCar(userCar);
							break;
						// 行驶证照片
						case FileUploadForm.FILEUPLOAD_CAR_DRIVING_IMAGE_DEST:
							path = IMAGEURI + FILEUPLOAD_CAR_DEST + imageName;
							imageURL = FILEUPLOAD_CAR_DEST + imageName;
							userCar = user.getUserCar();
							if (!"".equals(userCar)) {
								String[] cars = userCar.split(",");
								userCar = cars[0] + "," + imageURL + ","
										+ cars[2];
							} else {
								userCar = "," + imageURL + ",";// 驾驶证 行驶证 车辆
								user.setUserCarValidate(TUser.VALIDATE_RUNING);
								user.setUserCarValidateDate(new Date());
							}
							user.setUserCar(userCar);
							break;
						default:
							LOG.error("上传文件参数设置错误");
							break;
						}
						item.write(new File(path));
					}
				}
				// 生活照
				if (fileupload.getType().equals(
						FileUploadForm.FILEUPLOAD_LIFE_DEST)) {
					String userLifePhoto = sessionUser.getUserLifePhoto();
					String lifePhoto = builder.deleteCharAt(
							builder.lastIndexOf(",")).toString();
					if (StringUtil.isNotEmpty(userLifePhoto)) {
						lifePhoto = userLifePhoto + StringConstant.QUOTA
								+ lifePhoto;
					}
					user.setUserLifePhoto(lifePhoto);
				}
				// 路线照片 直接保存图片
				if (fileupload.getType().equals(
						FileUploadForm.FILEUPLOAD_DESCRIBE_ROADLINE_DEST)) {
					ResponseEntity.println(response, new FileUploadEntity(
							imageName));
					return;
				}
				// 订单评价照片 评论 回复
				if (fileupload.getType().equals(
						FileUploadForm.FILEUPLOAD_ORDER_EVALUATION_DEST)) {
					ResponseEntity.println(response, new FileUploadEntity(
							imageURL));
					return;
				}
				// 更新session域中的值
				BeanUtil.setPropertys(user, sessionUser);
				WebUtil.putUserToSession(request, sessionUser);
				// 保存修改的用户信息
				new Thread(new Runnable() {
					@Override
					public void run() {
						IUserService userService = new UserServiceImpl();
						userService.updateUser(user);
					}
				}).start();
				ResponseEntity
						.println(response, new FileUploadEntity(imageURL));
			}
			// 不是上传文件
			else {
				ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
						"不是上传文件,上传文件格式出现错误");
				LOG.error("不是上传文件,上传文件格式出现错误");
			}
		} catch (Exception e) {
			ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
					"文件上传出现异常");
			LOG.error("文件上传出现异常", e);
		}
	}

	public static class FileUploadEntity {

		private String imageURL;

		public FileUploadEntity(String imageURL) {
			super();
			this.imageURL = imageURL;
		}

		public String getImageURL() {
			return imageURL;
		}

		public void setImageURL(String imageURL) {
			this.imageURL = imageURL;
		}

	}
}