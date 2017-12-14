package org.marker.mushroom.controller;

import org.marker.ext.Msei;
import org.marker.ext.module.Module;
import org.marker.ext.module.ModuleContext;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.holder.WebRealPathHolder;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.FileTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.UUID;

/**
 * Bundles管理器
 *
 * @author marker
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/module")
public class ModuleController extends SupportController {

	@Autowired
	private ServletContext application;

	public ModuleController() {
		this.viewPath = "/admin/module/";
	}

	/**
	 * 创建模块
	 *
	 * @return
	 */
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView view = new ModelAndView(this.viewPath + "create");
		view.addObject("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
		return view;
	}

	/**
	 * 保存模块
	 *
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Object save(@RequestParam("uuid") String uuid,
					   @RequestParam("config") String config) {

		String moduleDir = WebRealPathHolder.REAL_PATH + "modules" + File.separator + uuid;

		File moduleFile = new File(moduleDir);
		if (moduleFile.mkdir()) {
			// 解压模块模板数据

			String sourceDir =
				WebRealPathHolder.REAL_PATH + "META-INF" + File.separator + "source" + File.separator + "module.source";
			String targetDir = moduleDir + File.separator;

			try {
				FileTools.extract(sourceDir, targetDir);// 解压模板

				// 篡改package.json文件
				File packageJsonFile = new File(moduleDir + File.separator + "package.json");

				FileTools.setFileContet(packageJsonFile, config, FileTools.FILE_CHARACTER_UTF8);

				ModuleContext mc = (ModuleContext) application.getAttribute(Msei.MODULE_CONTEXT);

				mc.install(new File(moduleDir));

			} catch (Exception e) {
				return new ResultMessage(false, "创建失败!");
			}
			return new ResultMessage(true, "创建成功!");
		}
		return new ResultMessage(false, "创建失败，检查模块是否存在!");
	}

	/**
	 * 编辑模块
	 *
	 * @return
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("uuid") String uuid) {
		ModelAndView view = new ModelAndView(this.viewPath + "edit");
		view.addObject("uuid", uuid);
		return view;
	}

	@RequestMapping("/list")
	public ModelAndView list() {
		ModuleContext context = (ModuleContext) application.getAttribute(Msei.MODULE_CONTEXT);

		ModelAndView view = new ModelAndView(this.viewPath + "list");

		view.addObject("modules", context.getList());
		return view;

	}

	/**
	 * 停止某个bundle
	 *
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/stop")
	public Object stop(@RequestParam("id") String uuid) {
		ModuleContext context = (ModuleContext) application.getAttribute(Msei.MODULE_CONTEXT);

		Module module = context.getModule(uuid);
		try {
			module.stop();
		} catch (Exception e) {
			log.error("stop bundleId={}", e.getMessage());
		}
		return list();
	}

	/**
	 * 启用模块
	 *
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/start")
	public Object start(@RequestParam("id") String uuid) {
		ModuleContext context = (ModuleContext) application.getAttribute(Msei.MODULE_CONTEXT);

		Module module = context.getModule(uuid);

		try {
			module.start();
		} catch (Exception e) {
			log.error("start bundleId={}", e.getMessage());
		}
		return list();
	}

	/**
	 * 卸载
	 *
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/uninstall")
	@ResponseBody
	public Object uninstall(@RequestParam("id") String uuid) {
		ModuleContext context = (ModuleContext) application.getAttribute(Msei.MODULE_CONTEXT);
		context.uninstall(uuid);
		return list();
	}

}


