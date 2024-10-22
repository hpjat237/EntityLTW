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
import vn.hoangphat.entity.User;
import vn.hoangphat.services.IUserService;
import vn.hoangphat.services.impl.UserService;
import vn.hoangphat.utils.Constant;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 15) // 15MB
@WebServlet(urlPatterns = { "/admin/users", "/admin/user/add", "/admin/user/insert",
		"/admin/user/edit", "/admin/user/update", "/admin/user/delete", "/admin/user/search"})
public class UserControllers extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public IUserService userService = new UserService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		if (url.contains("users")) {

			List<User> list = userService.findAll();
			req.setAttribute("listuser", list);
			req.getRequestDispatcher("/view/admin/user-list.jsp").forward(req, resp);

		} else if (url.contains("add")) {
			req.getRequestDispatcher("/view/admin/user-add.jsp").forward(req, resp);
		}else if (url.contains("edit")) {
			int id = Integer.parseInt( req.getParameter("id"));
			User user = userService.findById(id);
			
			req.setAttribute("user", user);
			req.getRequestDispatcher("/view/admin/user-edit.jsp").forward(req, resp);
			
		}else if (url.contains("delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			try {
				userService.delete(id);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			resp.sendRedirect(req.getContextPath() + "/admin/users");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		if (url.contains("insert")) {
			
			String fullname = req.getParameter("fullname");
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String roleid = req.getParameter("roleid");
			String phone = req.getParameter("phone");
			String email = req.getParameter("email");
			int roleId = Integer.parseInt(roleid);
			User user = new User();
			user.setFullname(fullname);
			user.setUsername(username);
			user.setPassword(password);
			user.setPhone(phone);
			user.setRoleid(roleId);
			user.setEmail(email);
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
					user.setImages(fname);
				}else {
					user.setImages("avata.png");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			userService.insert(user);
			resp.sendRedirect(req.getContextPath() + "/admin/users");

		}else if (url.contains("update")) {
			User user = new User();
			int id = Integer.parseInt( req.getParameter("id"));
			String fullname = req.getParameter("fullname");
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String phone = req.getParameter("phone");
			String roleid = req.getParameter("roleid");
			String email = req.getParameter("email");
			int roleId = Integer.parseInt(roleid);
			user.setId(id);
			user.setFullname(fullname);
			user.setUsername(username);
			user.setPassword(password);
			user.setPhone(phone);
			user.setRoleid(roleId);
			user.setEmail(email);
			
			//luu hinh cu~
			User userold = userService.findById(id);
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
					user.setImages(fname);
				}else {
					user.setImages(fileold);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			userService.update(user);
			resp.sendRedirect(req.getContextPath() + "/admin/users");

		}
		
		
	}
}