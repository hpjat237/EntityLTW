package vn.hoangphat.services;

import java.util.List;

import vn.hoangphat.entity.Category;

public interface ICategoryService {

	int count();

	List<Category> findAll(int page, int pagesize);

	Category findByCategoryname(String catname);

	List<Category> findAll();

	Category findById(int cateid);

	void delete(int cateid) throws Exception;

	void update(Category category);

	void insert(Category category);
	
	List<Category> searchByName(String catname);
}