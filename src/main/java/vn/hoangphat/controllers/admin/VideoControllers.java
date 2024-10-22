package vn.hoangphat.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.hoangphat.entity.Category;
import vn.hoangphat.entity.User;
import vn.hoangphat.entity.Video;
import vn.hoangphat.services.IVideoService;
import vn.hoangphat.services.impl.CategoryService;
import vn.hoangphat.services.impl.VideoService;
import vn.hoangphat.utils.Constant;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 15) // 15MB
@WebServlet(urlPatterns = { "/admin/videos", "/admin/video/add", "/admin/video/insert",
		"/admin/video/edit", "/admin/video/update", "/admin/video/delete", "/admin/video/search"})

public class VideoControllers extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public IVideoService videoService = new VideoService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CategoryService categoryService = new CategoryService();
	    List<Category> categories = categoryService.findAll(); // Giả sử hàm này trả về danh sách tất cả Category

	    req.setAttribute("categories", categories); // Đặt danh sách category vào request attribute
	    //req.getRequestDispatcher("/view/admin/video-add.jsp").forward(req, resp);
	    //req.getRequestDispatcher("/view/admin/video-edit.jsp").forward(req, resp);
	    
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		if (url.contains("videos")) {

			List<Video> list = videoService.findAll();
			req.setAttribute("listvideo", list);
			req.getRequestDispatcher("/view/admin/video-list.jsp").forward(req, resp);

		} else if (url.contains("add")) {
			req.getRequestDispatcher("/view/admin/video-add.jsp").forward(req, resp);
		}else if (url.contains("edit")) {
			String id = req.getParameter("id");
			Video video = videoService.findById(id);
			
			req.setAttribute("video", video);
			req.getRequestDispatcher("/view/admin/video-edit.jsp").forward(req, resp);
			
		}else if (url.contains("delete")) {
			String id = req.getParameter("id");
			try {
				videoService.delete(id);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		if (url.contains("insert")) {
			
			String videoid = req.getParameter("videoId");
			String videotitle = req.getParameter("title");
			String description = req.getParameter("description");
			String categoryId = req.getParameter("categoryId");
			String status = req.getParameter("active");
			int statuss = Integer.parseInt(status);
			Video video = new Video();
			video.setVideoId(videoid);
			video.setTitle(videotitle);
			video.setViews(0);
			video.setDescription(description);
			video.setActive(statuss);
			
			CategoryService categoryService = new CategoryService();
			Category category = categoryService.findById(Integer.parseInt(categoryId));
	        video.setCategory(category);
	        
			//upload image
			String fname = "";
			String uploadPath = Constant.DIR;
			File uploadDir = new File(uploadPath);
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				Part part = req.getPart("images");
				if (part.getSize()>0)
				{
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					//đổi tên file
					int index = filename.lastIndexOf(".");
					String ext = filename.substring(index+1);
					fname = System.currentTimeMillis() +  "." + ext;
					//upload file 
					part.write(uploadPath + "/" + fname);
					//ghi ten file vao data
					video.setImages(fname);
				}else {
					video.setImages("avata.png");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			videoService.insert(video);
			resp.sendRedirect(req.getContextPath() + "/admin/videos");

		}else if (url.contains("update")) {
			String videoid = req.getParameter("videoId");
			String videotitle = req.getParameter("title");
			String description = req.getParameter("description");
			String categoryId = req.getParameter("categoryId");
			String status = req.getParameter("active");
			int statuss = Integer.parseInt(status);
			Video video = new Video();
			video.setVideoId(videoid);
			video.setTitle(videotitle);
			video.setViews(0);
			video.setDescription(description);
			video.setActive(statuss);
			
			CategoryService categoryService = new CategoryService();
			Category category = categoryService.findById(Integer.parseInt(categoryId));
	        video.setCategory(category);
			
			//luu hinh cu~
			Video userold = videoService.findById(videoid);
			String fileold = userold.getImages();
			//upload image
			String fname = "";
			String uploadPath = Constant.DIR;
			File uploadDir = new File(uploadPath);
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				Part part = req.getPart("images");
				if (part.getSize()>0)
				{
					//xóa hình cũ
					
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					//đổi tên file
					int index = filename.lastIndexOf(".");
					String ext = filename.substring(index+1);
					fname = System.currentTimeMillis() +  "." + ext;
					//upload file 
					part.write(uploadPath + "/" + fname);
					//ghi ten file vao data
					video.setImages(fname);
				}else {
					video.setImages(fileold);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			videoService.update(video);
			resp.sendRedirect(req.getContextPath() + "/admin/videos");

		}
	}
	
}
