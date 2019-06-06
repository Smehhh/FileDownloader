package download;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "UploadServlet", urlPatterns = {"/upload.jsp"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)

public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        
        try {
            
            File fileSaveDir = FileListProvider.getFilepath(getServletContext());
            FileListProvider.ensureFilePath(fileSaveDir);

            if (!request.getContentType().contains("multipart/form-data")) {
                throw new IOException("Wrong request type");
            }

            for (Part part : request.getParts()) {
                String fileName = part.getSubmittedFileName();
                
                if (fileName == null || !part.getName().equals("file")) {
                    continue;
                }
                
                File file = new File(fileSaveDir, fileName); 
                
                if(!fileSaveDir.equals(file.getParentFile())) {
                    throw new IllegalArgumentException("Access rights violation");
                }
                
                part.write(file.getAbsolutePath());
                
                break;
            }

            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            getServletContext().getRequestDispatcher("/WEB-INF/templates/error.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/templates/error.jsp");
    }
    
    
}
