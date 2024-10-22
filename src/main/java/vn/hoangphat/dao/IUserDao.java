package vn.hoangphat.dao;

import java.util.List;

import vn.hoangphat.entity.User;

public interface IUserDao {
	int count();

	User findByUsername(String uname) throws Exception;

	List<User> findAll();

	User findById(int uid);

	void delete(int uid) throws Exception;

	void update(User user);

	void insert(User user);

	List<User> searchByName(String uname);

}
