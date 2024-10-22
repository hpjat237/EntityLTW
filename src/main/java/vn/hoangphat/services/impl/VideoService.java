package vn.hoangphat.services.impl;

import java.util.List;

import vn.hoangphat.entity.Video;
import vn.hoangphat.services.IVideoService;
import vn.hoangphat.dao.IVideoDao;
import vn.hoangphat.dao.impl.VideoDao;
public class VideoService implements IVideoService{
	IVideoDao videoDao = new VideoDao();
	
	@Override
	public int count() {
		return videoDao.count();
	}

	@Override
	public Video findByTitle(String title) throws Exception {
		try {
			return videoDao.findByTitle(title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Video> findAll() {
		return videoDao.findAll();
	}

	@Override
	public Video findById(String videoid) {
		return videoDao.findById(videoid);
	}

	@Override
	public void delete(String videoid) throws Exception {
		try {
			videoDao.delete(videoid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Video video) {
		Video vid = this.findById(video.getVideoId());
		if (vid != null) {
			videoDao.update(video);
		}
	}

	@Override
	public void insert(Video video) {
		try {
	        Video vid = this.findByTitle(video.getTitle());
	        if (vid == null) {
	            videoDao.insert(video);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public List<Video> searchByTitle(String keyword) {
		return videoDao.searchByTitle(keyword);
	}

}
