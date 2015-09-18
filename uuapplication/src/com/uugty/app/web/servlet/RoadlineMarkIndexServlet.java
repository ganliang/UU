package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.domain.TMark;
import com.uugty.app.entity.MarkEntity;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IMarkService;
import com.uugty.app.service.impl.MarkServiceImpl;
import com.uugty.app.utils.BeanUtil;

/**
 * @ClassName: RoadlineMarkIndexServlet
 * @Description: 获取首页 标签显示的列表
 * @author ganliang
 * @date 2015年7月28日 上午9:16:15
 */
@WebServlet(urlPatterns = { "/roadlineMarkIndex.do" }, loadOnStartup = 10)
public class RoadlineMarkIndexServlet extends HttpServlet {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 589446746623987090L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		TMark mark = (TMark) BeanUtil.populate(request, TMark.class);
		List<Object> list = null;
		IMarkService markService = new MarkServiceImpl();
		List<Object> markEntitys = new ArrayList<Object>();
		
		//首页搜索
		if (mark.getMarkSearchType() != null) {
			list = markService.getMarkLineList(mark);
			MarkEntity entity = null;
			String markContent = null;
			String markSearchType = null;
			for (Object object : list) {
				entity = (MarkEntity) object;
				markContent = entity.getMarkContent();
				markSearchType = entity.getMarkSearchType();
				if ("mark".equals(markSearchType)) {
					entity.setMarkContent(String.valueOf(Integer
							.parseInt(markContent) - 100000));
				}
				markEntitys.add(entity);
			}
		} else {
			// 找到标签
			markEntitys = markService.getMarkList(mark);
		}

		ResponseEntity.printlns(response, markEntitys);
	}

	@Override
	public void init() throws ServletException {
		// insertMark();
		super.init();
	}

	/**
	 * @Title: insertMark
	 * @Description: 录入mark标签数据
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	public static void insertMark() {
		List<TMark> marks = new ArrayList<TMark>();

		TMark mark1 = new TMark();
		mark1.setMarkImages("images/mark/beijing.png");
		mark1.setMarkSearchType(TMark.MARK_SEARCH_GOAL);
		mark1.setMarkTitle("北京");
		mark1.setMarkWeight(10);
		mark1.setMarkContent("热门目的地");
		marks.add(mark1);

		TMark mark2 = new TMark();
		mark2.setMarkImages("images/mark/sanya.png");
		mark2.setMarkSearchType(TMark.MARK_SEARCH_GOAL);
		mark2.setMarkTitle("三亚");
		mark2.setMarkWeight(9);
		mark2.setMarkContent("热门目的地");
		marks.add(mark2);

		TMark mark3 = new TMark();
		mark3.setMarkImages("images/mark/guilin.png");
		mark3.setMarkSearchType(TMark.MARK_SEARCH_GOAL);
		mark3.setMarkTitle("桂林");
		mark3.setMarkWeight(8);
		mark3.setMarkContent("热门目的地");
		marks.add(mark3);

		TMark mark4 = new TMark();
		mark4.setMarkImages("images/mark/lijiang.png");
		mark4.setMarkSearchType(TMark.MARK_SEARCH_GOAL);
		mark4.setMarkTitle("丽江");
		mark4.setMarkWeight(7);
		mark4.setMarkContent("热门目的地");
		marks.add(mark4);

		TMark mark5 = new TMark();
		mark5.setMarkImages("images/mark/chengdu.png");
		mark5.setMarkSearchType(TMark.MARK_SEARCH_GOAL);
		mark5.setMarkTitle("成都");
		mark5.setMarkWeight(6);
		mark5.setMarkContent("热门目的地");
		marks.add(mark5);

		TMark mark6 = new TMark();
		mark6.setMarkImages("images/mark/hangzhou.png");
		mark6.setMarkSearchType(TMark.MARK_SEARCH_GOAL);
		mark6.setMarkTitle("杭州");
		mark6.setMarkWeight(5);
		mark6.setMarkContent("热门目的地");
		marks.add(mark6);

		TMark mark7 = new TMark();
		mark7.setMarkImages("images/mark/qingdao.png");
		mark7.setMarkSearchType(TMark.MARK_SEARCH_GOAL);
		mark7.setMarkTitle("青岛");
		mark7.setMarkWeight(4);
		mark7.setMarkContent("热门目的地");
		marks.add(mark7);

		TMark mark8 = new TMark();
		mark8.setMarkImages("images/mark/shanghai.png");
		mark8.setMarkSearchType(TMark.MARK_SEARCH_GOAL);
		mark8.setMarkTitle("上海");
		mark8.setMarkWeight(3);
		mark8.setMarkContent("热门目的地");
		marks.add(mark8);

		TMark mark9 = new TMark();
		mark9.setMarkImages("images/mark/xian.png");
		mark9.setMarkSearchType(TMark.MARK_SEARCH_GOAL);
		mark9.setMarkTitle("西安");
		mark9.setMarkWeight(2);
		mark9.setMarkContent("热门目的地");
		marks.add(mark9);

		IMarkService markService = new MarkServiceImpl();
		markService.saveMarkList(marks);
	}
}
