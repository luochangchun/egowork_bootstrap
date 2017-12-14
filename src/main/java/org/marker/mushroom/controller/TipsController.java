package org.marker.mushroom.controller;

import org.marker.mushroom.service.impl.TipsService;
import org.marker.mushroom.support.SupportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 文章管理
 *
 * @author marker
 */
@Controller
@RequestMapping("/admin/tips")
public class TipsController extends SupportController {

	@Autowired
	private TipsService tipsService;

	public TipsController() {
		this.viewPath = "/admin/tips/";
	}

	@RequestMapping("/view")
	public ModelAndView view() {
		return new ModelAndView(this.viewPath + "list");

	}

	/**
	 * 补贴券列表接口(REST)
	 *
	 * @param currentPageNo
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(@RequestParam("currentPageNo") final int currentPageNo, @RequestParam("pageSize") final int pageSize) {

		return tipsService.find(currentPageNo, pageSize);
	}

}
