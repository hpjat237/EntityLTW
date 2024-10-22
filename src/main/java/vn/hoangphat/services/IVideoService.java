package vn.hoangphat.services;

import java.util.List;

import vn.hoangphat.entity.Video;

public interface IVideoService {
	int count();

	Video findByTitle(String title) throws Exception;

	List<Video> findAll();

	Video findById(String videoid);

	void delete(String id) throws Exception;

	void update(Video video);

	void insert(Video video);

	List<Video> searchByTitle(String keyword);
}
