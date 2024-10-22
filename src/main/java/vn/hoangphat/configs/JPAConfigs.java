package vn.hoangphat.configs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.*;
import vn.hoangphat.entity.Category;

@PersistenceContext
public class JPAConfig {
	public static EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
		return factory.createEntityManager();
	}

	public static void main(String[] args) {
		EntityManager enma = getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		Category cate = new Category();
		cate.setCategoryname("Iphone");
		cate.setImages("abc.jpg");
		cate.setStatus(1);
		try {
			trans.begin();
			enma.persist(cate);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		} finally {
			enma.close();
		}

	}
}
