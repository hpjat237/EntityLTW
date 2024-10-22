package vn.hoangphat.services.impl;

import java.util.List;

import vn.hoangphat.dao.IUserDao;
import vn.hoangphat.dao.impl.UserDao;
import vn.hoangphat.entity.User;
import vn.hoangphat.services.IUserService;

public class UserService implements IUserService{
	IUserDao userDao = new UserDao();
	@Override
	public int count() {
		return userDao.count();
	}

	@Override
	public User findByUsername(String uname) throws Exception {
		try {
			return userDao.findByUsername(uname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public User findById(int uid) {
		return userDao.findById(uid);
	}

	@Override
	public void delete(int uid) throws Exception {
		try {
			userDao.delete(uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(User user) {
		User usr = this.findById(user.getId());
		if (usr != null) {
			userDao.update(user);
		}
		
	}

	@Override
	public void insert(User user) {
		try {
	        User usr = this.findByUsername(user.getUsername());
	        if (usr == null) {
	            userDao.insert(user);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}

	@Override
	public List<User> searchByName(String keyword) {
		return userDao.searchByName(keyword);
	}

}
