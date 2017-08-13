package servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import model.sys.TfileUploadStatus;
import service.sys.FileUploadStatusServiceI;

@WebServlet(urlPatterns = { "/ckeckFileServlet" })
public class CkeckFileServlet extends HttpServlet {

	private FileUploadStatusServiceI statusService;
	String repositoryPath;
	String uploadPath;

	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext servletContext = config.getServletContext();
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		statusService = (FileUploadStatusServiceI) context.getBean("FileUploadStatusServiceImpl");

		repositoryPath = FileUtils.getTempDirectoryPath();
		uploadPath = config.getServletContext().getRealPath("datas/uploader");
		File up = new File(uploadPath);
		if (!up.exists()) {
			up.mkdir();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String fileName = new String(req.getParameter("filename"));
		//String chunk = req.getParameter("chunk");
		//System.out.println(chunk);
		System.out.println(fileName);
		resp.setContentType("text/json; charset=utf-8");

		TfileUploadStatus file = statusService.get(fileName);

		try {
			if (file != null) {
				int schunk = file.getChunk();
				deleteFile(uploadPath + schunk + "_" + fileName);
				//long off = schunk * Long.parseLong(chunkSize);
				resp.getWriter().write("{\"off\":" + schunk + "}");

			} else {
				resp.getWriter().write("{\"off\":1}");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}
}
