package vn.hoangphat.services.impl;

import java.util.List;

import vn.hoangphat.dao.ICategoryDao;
import vn.hoangphat.dao.impl.CategoryDao;
import vn.hoangphat.entity.Category;
import vn.hoangphat.services.ICategoryService;

public class CategoryService implements ICategoryService {
	ICategoryDao cateDao = new CategoryDao();

	@Override
	public int count() {

		return cateDao.count();
	}

	@Override
	public List<Category> findAll(int page, int pagesize) {

		return cateDao.findAll(page, pagesize);
	}

	@Override

	public Category findByCategoryname(String name) {
		try {
			return cateDao.findByCategoryname(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Category> findAll() {

		return cateDao.findAll();
	}

	@Override
	public Category findById(int cateid) {

		return cateDao.findById(cateid);
	}

	@Override

	public void delete(int id) {
		try {
			cateDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override

	public void update(Category category) {
		Category cate = this.findById(category.getCategoryId());
		if (cate != null) {
			cateDao.update(category);
		}
	}

	@Override

	public void insert(Category category) {
		Category cate = this.findByCategoryname(category.getCategoryname());
		if (cate == null) {
			cateDao.insert(category);
		}
	}

	@Override

	public List<Category> searchByName(String keyword) {

		return cateDao.searchByName(keyword);

	}
}
