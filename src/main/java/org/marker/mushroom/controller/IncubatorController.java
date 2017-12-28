package org.marker.mushroom.controller;

import org.apache.commons.codec.binary.Base64;
import org.marker.mushroom.beans.Incubator;
import org.marker.mushroom.beans.IncubatorPhoto;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.core.AppStatic;
import org.marker.mushroom.core.proxy.SingletonProxyFrontURLRewrite;
import org.marker.mushroom.dao.IArticleDao;
import org.marker.mushroom.service.impl.CategoryService;
import org.marker.mushroom.service.impl.DictionariesService;
import org.marker.mushroom.service.impl.IncubatorService;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.DateUtils;
import org.marker.mushroom.utils.HttpUtils;
import org.marker.mushroom.utils.StringUtil;
import org.marker.mushroom.utils.UUIDGenerator;
import org.marker.urlrewrite.URLRewriteEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文章管理
 *
 * @author marker
 */
@Controller
@RequestMapping("/admin/incubator")
public class IncubatorController extends SupportController {

	// 文章Dao
	@Autowired
	IArticleDao articleDao;

	@Autowired
	IncubatorService incubatorService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	DictionariesService dictionariesService;

	private final String QB = "incubator";

	public IncubatorController() {
		this.viewPath = "/admin/incubator/";
	}

	//发布文章
	@RequestMapping("/add")
	public ModelAndView add() {
		final ModelAndView view = new ModelAndView(this.viewPath + "add");
		view.addObject("categorys", categoryService.list(QB));

		view.addObject("levels", dictionariesService.findDictionaries("level", QB));
		view.addObject("labels", dictionariesService.findDictionaries("label", QB));
		view.addObject("patterns", dictionariesService.findDictionaries("pattern", QB));
		view.addObject("regions", dictionariesService.findDictionaries("region", QB));
		view.addObject("provides", dictionariesService.findDictionaries("provide", QB));
		view.addObject("facilities", dictionariesService.findDictionaries("facility", QB));
		view.addObject("enters", dictionariesService.findDictionaries("enter", QB));

		return view;
	}

	//编辑文章
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") final int id) {
		final ModelAndView view = new ModelAndView(this.viewPath + "edit");
		view.addObject(QB, commonDao.findById(Incubator.class, id));
		view.addObject("categorys", categoryService.list(QB));

		view.addObject("levels", dictionariesService.findDictionaries("level", QB));
		view.addObject("labels", dictionariesService.findDictionaries("label", QB));
		view.addObject("patterns", dictionariesService.findDictionaries("pattern", QB));
		view.addObject("regions", dictionariesService.findDictionaries("region", QB));
		view.addObject("provides", dictionariesService.findDictionaries("provide", QB));
		view.addObject("facilities", dictionariesService.findDictionaries("facility", QB));
		view.addObject("enters", dictionariesService.findDictionaries("enter", QB));

		return view;
	}

	@GetMapping("/photo")
	public ModelAndView photoView(@RequestParam("id") final int id) {
		final ModelAndView view = new ModelAndView(this.viewPath + "photo");
		view.addObject(QB, commonDao.findById(Incubator.class, id));
		view.addObject("photos", incubatorService.findPhotos(id));
		return view;
	}

	@DeleteMapping("/photo")
	@ResponseBody
	public ResultMessage delPhoto(@RequestParam("id") final int id) {
		commonDao.deleteByIds(IncubatorPhoto.class, String.valueOf(id));
		return new ResultMessage(true, "删除成功！");
	}

	@PostMapping("/photo")
	@ResponseBody
	public ResultMessage photoUpload(@RequestParam("id") int id,
									 @RequestParam("imgSrc") final String imgSrc,
									 final HttpServletRequest request) {
		final HttpSession session = request.getSession();
		final Integer userId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSION_USER_ID);

		if (StringUtil.isBlank(imgSrc)) {
			log.info("file upload error: no file upload!");
			return new ResultMessage("上传文件不存在!");
		}

		try {
			//转换成图片的base64不要data:image/jpg;base64,这个头文件
			final String header = imgSrc.substring(0, imgSrc.indexOf(","));
			final String suffix = header.substring(header.indexOf("/") + 1, header.indexOf(";"));
			final String fileName = System.currentTimeMillis() + "_" + UUIDGenerator.getPureUUID() + "." + suffix;

			String realPath = request.getSession().getServletContext().getRealPath("");
			String ctxPath = request.getContextPath();
			if (!StringUtil.isBlank(ctxPath)) realPath = realPath.substring(0, realPath.indexOf(ctxPath));
			else if (realPath.contains("/ROOT")) realPath = realPath.substring(0, realPath.indexOf("/ROOT"));
			String uri = File.separator + "upload" + File.separator + "qb"
				+ File.separator + DateUtils.dateToString(new Date(), "yyyyMMdd");
			String dirPath = realPath + uri;
			String fullPath = dirPath + File.separator + fileName;

			log.debug(fullPath);
			new File(dirPath).mkdirs();

			final int delLength = imgSrc.indexOf(',') + 1;
			final String imgBase64 = imgSrc.substring(delLength, imgSrc.length() - delLength);
			byte[] fileByteArr = imgBase64.getBytes("UTF-8");
			//拿到上传文件的输入流
			fileByteArr = Base64.decodeBase64(fileByteArr);

			FileOutputStream os = null;
			InputStream in = null;
			try {
				os = new FileOutputStream(fullPath);
				in = new ByteArrayInputStream(fileByteArr);
				int b;
				while ((b = in.read()) != -1) {
					os.write(b);
				}
				os.flush();
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultMessage("上传失败，文件写入失败!");
			} finally {
				if (os != null) os.close();
				if (in != null) in.close();
			}

			IncubatorPhoto photo = new IncubatorPhoto();
			photo.setUri(uri + File.separator + fileName);
			photo.setIncubatorId(id);
			if (!commonDao.save(photo)) {
				return new ResultMessage("上传失败，数据写入失败!");
			}
			return new ResultMessage(true, "上传成功!", photo);
		} catch (final Exception e) {
			e.printStackTrace();
			return new ResultMessage("上传失败!");
		}
	}

	/**
	 * 持久化文章操作
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Object save(@ModelAttribute(QB) final Incubator incubator, @RequestParam("cid") final int cid) {
		incubator.setTime(new Date());
		incubator.setCreateTime(new Date());
		incubator.setCid(cid);// 这里是因为不能注入bean里

		String msg;
		if (incubator.getStatus() == 1) {
			msg = "发布";
		} else {
			msg = "保存草稿";
		}

		if (commonDao.save(incubator)) {
			return new ResultMessage(true, msg + "成功!");
		} else {
			return new ResultMessage(false, msg + "失败!");
		}
	}

	//保存
	@ResponseBody
	@RequestMapping("/update")
	public Object update(@ModelAttribute(QB) final Incubator incubator) {
		incubator.setTime(new Date());

		if (commonDao.update(incubator)) {
			return new ResultMessage(true, "更新成功!");
		} else {
			return new ResultMessage(false, "更新失败!");
		}
	}

	//置顶
	@ResponseBody
	@RequestMapping("/top")
	public Object top(@RequestParam("ids") final String ids, @RequestParam("level") final String level) {
		String tips = "";
		if (commonDao.top(Incubator.class, ids, level)) {
			if (level.equals("1"))
				tips = "置顶成功！";
			else if (level.equals("0"))
				tips = "取消置顶成功！";

			return new ResultMessage(true, tips);
		} else {
			if (level.equals("1"))
				tips = "置顶失败！";
			else if (level.equals("0"))
				tips = "取消置顶失败！";

			return new ResultMessage(false, tips);
		}
	}

	//删除文章
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("rid") final String rid) {
		final boolean status = commonDao.deleteByIds(Incubator.class, rid);
		if (status) {
			return new ResultMessage(true, "删除成功!");
		} else {
			return new ResultMessage(false, "删除失败!");
		}
	}

	//发布文章
	@RequestMapping("/list")
	public ModelAndView listview(final HttpServletRequest request) {
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		view.addObject("categorys", categoryService.list(QB));

		return view;
	}

	@GetMapping
	@ResponseBody
	public Object list(final HttpServletRequest request, final ModelMap model,
					   @RequestParam("currentPageNo") final int currentPageNo, @RequestParam("cid") String cid,
					   @RequestParam("status") final String status, @RequestParam("keyword") final String keyword,
					   @RequestParam("pageSize") final int pageSize) {
		final HttpSession session = request.getSession();
		final String usercategory = (String) session.getAttribute(AppStatic.WEB_APP_SESSION_USER_CATEGORY);
		final Integer userId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSION_USER_ID);
		final Integer groupId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSSION_USER_GROUP_ID);

		if (StringUtils.isEmpty(cid)) {
			cid = usercategory;
		}

		final Map<String, Object> params = new HashMap<>();
		params.put("cid", cid);
		params.put("status", status);
		params.put("keyword", keyword);
		params.put("userid", userId);
		params.put("groupid", groupId);
		final Page page = incubatorService.find(currentPageNo, pageSize, params);

		final URLRewriteEngine urlRewrite = SingletonProxyFrontURLRewrite.getInstance();

		final String url = HttpUtils.getRequestURL(request);
		// 遍历URL重写
		for (final Map<String, Object> data : page.getData()) {
			data.put("url", url + urlRewrite.encoder(data.get("url").toString()));
		}
		return page;
	}

	@RequestMapping("/view")
	public ModelAndView view() {
		return new ModelAndView(this.viewPath + "applyList");

	}

	/**
	 * 志愿者个人列表接口(REST)
	 *
	 * @param currentPageNo
	 * @return
	 */
	@RequestMapping(value = "/applyList", method = RequestMethod.GET)
	@ResponseBody
	public Object applyList(@RequestParam("currentPageNo") final int currentPageNo,
							@RequestParam("pageSize") final int pageSize) {

		return incubatorService.find(currentPageNo, pageSize);
	}
}
