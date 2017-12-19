package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.Product;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.service.impl.CategoryService;
import org.marker.mushroom.service.impl.DictionariesService;
import org.marker.mushroom.service.impl.ProductService;
import org.marker.mushroom.support.SupportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 服务产品后台接口
 * Created by 30 on 2017/12/19.
 */
@Controller
@RequestMapping("/admin/product")
public class ProductController extends SupportController {

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	DictionariesService dictionariesService;

	public ProductController() {
		this.viewPath = "/admin/product/";
	}

	@GetMapping("/list")
	public ModelAndView listView(Page page, @RequestParam(value="cid", required = false, defaultValue = "0") int cid) {
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		view.addObject("cid", cid);
		view.addObject("categorys", categoryService.list("product"));
		view.addObject("page", productService.findByPage(page.getCurrentPageNo(), 10, cid));
		return view;
	}

	@GetMapping("/add")
	public ModelAndView add() {
		final ModelAndView view = new ModelAndView(this.viewPath + "add");
		view.addObject("categorys", categoryService.list("product"));
		return view;
	}

	@GetMapping("/edit")
	public ModelAndView edit(@RequestParam("id") final int id) {
		final ModelAndView view = new ModelAndView(this.viewPath + "edit");
		view.addObject("product", commonDao.findById(Product.class, id));
		view.addObject("categorys", categoryService.list("product"));
		return view;
	}

	@PostMapping("/save")
	@ResponseBody
	public ResultMessage save(final Product product) {
		if (commonDao.save(product)) {
			return new ResultMessage(true, "添加成功!");
		} else {
			return new ResultMessage(false,"添加失败!");
		}
	}

	@ResponseBody
	@RequestMapping("/update")
	public ResultMessage update(@ModelAttribute("product") final Product product) {
		if (commonDao.update(product)) {
			return new ResultMessage(true, "更新成功!");
		} else {
			return new ResultMessage(false, "更新失败!");
		}
	}

	@ResponseBody
	@RequestMapping("/delete")
	public ResultMessage delete(@RequestParam("rid") final String rid) {
		if (commonDao.deleteByIds(Product.class, rid)) {
			return new ResultMessage(true, "删除成功!");
		} else {
			return new ResultMessage(false, "删除失败!");
		}
	}

}
