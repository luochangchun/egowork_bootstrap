package org.marker.mushroom.controller;

import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.dao.IModelDao;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.ext.model.ContentModelContext;
import org.marker.mushroom.holder.WebRealPathHolder;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.FileTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

/**
 * 内容模型管理
 *
 * @author marker
 */
@Controller
@RequestMapping("/admin/model")
public class ContentModelController extends SupportController {

	@Autowired
	private ISupportDao commonDao;

	@Autowired
	private IModelDao modelDao;

	public ContentModelController() {
		this.viewPath = "/admin/model/";
	}

	//添加模型
	@RequestMapping("/add")
	public String add() {
		return this.viewPath + "add";
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("type") String modelType) {

		// 验证是否被引用

		boolean status = modelDao.deleteByType(modelType);

		ContentModelContext cmc = ContentModelContext.getInstance();

		cmc.remove(modelType);

		File file = new File(WebRealPathHolder.REAL_PATH + "models" + File.separator + modelType);

		FileTools.deleteDirectory(file);// 删除文件
		if (status) {
			return new ResultMessage(true, "删除成功!");
		} else {
			return new ResultMessage(false, "删除失败!");
		}
	}

	//显示列表
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView view = new ModelAndView(this.viewPath + "list");
		view.addObject("data", modelDao.queryAll());
		return view;
	}

}
