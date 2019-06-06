package download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author BURNERON
 */

@WebServlet(name = "DownloadServlet", urlPatterns = {"/download.jsp"})
public class DownloadServlet extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        try {
            
            String fileName = request.getParameter("filename");
            File fileSaveDir = FileListProvider.getFilepath(getServletContext());
            
            File file = new File(fileSaveDir, fileName);
            if(!fileSaveDir.equals(file.getParentFile())) {
                throw new IllegalArgumentException("you do not have access rights");
            }
            
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment; filename=\"" + encodedFileName + "\"");
            
            try (InputStream input = new FileInputStream(file);
                OutputStream output = response.getOutputStream()) {
                response.setContentLength((int)file.length());
                
                byte[] buf = new byte[4096];
                int length;
                while ((length = input.read(buf)) >= 0) {
                    output.write(buf, 0, length);
                }
            }
            
        } catch(Exception e) {
            getServletContext().getRequestDispatcher("/WEB-INF/templates/error.jsp");
        }
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
