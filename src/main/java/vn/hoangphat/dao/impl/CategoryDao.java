package vn.hoangphat.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.hoangphat.configs.JPAConfig;
import vn.hoangphat.dao.ICategoryDao;
import vn.hoangphat.entity.Category;

public class CategoryDao implements ICategoryDao {
	// viết phương thức trước khai báo hàm sau
	@Override
	public void insert(Category category) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			enma.persist(category);// insert vào bảng
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Category> findAll(int page, int pagesize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> searchByName(String catname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category findById(int cateid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int cateid) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Category category) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Category findByCategoryname(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
