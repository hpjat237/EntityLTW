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
import vn.hoangphat.services.ICategoryService;
import vn.hoangphat.services.impl.CategoryService;
import vn.hoangphat.utils.Constant;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 15) // 15MB
@WebServlet(urlPatterns = { "/admin/categories", "/admin/category/add", "/admin/category/insert",
		"/admin/category/edit", "/admin/category/update", "/admin/category/delete", "/admin/category/search"})
public class CategoryControllers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ICategoryService cateService = new CategoryService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		if (url.contains("categories")) {

			List<Category> list = cateService.findAll();
			req.setAttribute("listcate", list);
			req.getRequestDispatcher("/view/admin/category-list.jsp").forward(req, resp);

		} else if (url.contains("add")) {
			req.getRequestDispatcher("/view/admin/category-add.jsp").forward(req, resp);
		}else if (url.contains("edit")) {
			int id = Integer.parseInt( req.getParameter("id"));
			Category category = cateService.findById(id);
			
			req.setAttribute("cate", category);
			req.getRequestDispatcher("/view/admin/category-edit.jsp").forward(req, resp);
			
		}else if (url.contains("delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			try {
				cateService.delete(id);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		if (url.contains("insert")) {

			String categoryname = req.getParameter("Categoryname");
			String status = req.getParameter("status");
			int statuss = Integer.parseInt(status);
			Category category = new Category();
			category.setCategoryname(categoryname);
			category.setStatus(statuss);
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
					category.setImages(fname);
				}else {
					category.setImages("avata.png");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			cateService.insert(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");

		} else if (url.contains("update")) {
			Category category = new Category();
			int categoryid = Integer.parseInt( req.getParameter("CategoryId"));
			String categoryname = req.getParameter("Categoryname");
			String status = req.getParameter("status");
			int statuss = Integer.parseInt(status);
			category.setCategoryId(categoryid);
			category.setCategoryname(categoryname);
			category.setStatus(statuss);
			//luu hinh cu~
			Category cateold = cateService.findById(categoryid);
			String fileold = cateold.getImages();
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
					category.setImages(fname);
				}else {
					category.setImages(fileold);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			cateService.update(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");

		}
	}
}
