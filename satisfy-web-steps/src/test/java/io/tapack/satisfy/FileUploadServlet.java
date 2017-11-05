package io.tapack.satisfy;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileUploadServlet extends HttpServlet {

    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
    private static final Logger log = LoggerFactory.getLogger
            (FileUploadServlet.class);

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws
            ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            writeErrorToResponse(response);
            return;
        }
        saveFileItemsFromRequest(request);
        forwardToUploadPage(request, response);
    }

    private void saveFileItemsFromRequest(HttpServletRequest request) {
        try {
            ServletFileUpload servletFileUpload = getServletFileUpload();
            processFileItems(servletFileUpload.parseRequest(request));
        } catch (Exception ex) {
            log.error("Exception when upload file!");
            log.error(ex.getMessage(), ex);
        }
    }

    private void forwardToUploadPage(HttpServletRequest request,
                                     HttpServletResponse response) throws
            ServletException, IOException {
        getServletContext().getRequestDispatcher("/download-upload-example" +
                ".html").forward(
                request, response);
    }

    private void processFileItems(List<FileItem> fileItems) throws Exception {
        if (isExists(fileItems)) {
            for (FileItem item : fileItems) {
                if (!item.isFormField()) {
                    saveFileItem(item);
                }
            }
        }
    }

    private void saveFileItem(FileItem item) throws Exception {
        String[] fileNameParts = new File(item.getName()).getName().split("\\" +
                ".");
        String suffix = "." + fileNameParts[1];
        String prefix = fileNameParts[0];
        File storeFile = File.createTempFile(prefix, suffix);
        item.write(storeFile);
        log.info("UPLOADED FILE IS - " + storeFile.getAbsolutePath());
    }

    private boolean isExists(List<FileItem> formItems) {
        return formItems != null && formItems.size() > 0;
    }

    private ServletFileUpload getServletFileUpload() {
        ServletFileUpload upload = new ServletFileUpload
                (getDiskFileItemFactory());
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
        return upload;
    }

    private DiskFileItemFactory getDiskFileItemFactory() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        return factory;
    }

    private void writeErrorToResponse(HttpServletResponse response) throws
            IOException {
        PrintWriter writer = response.getWriter();
        writer.println("Error: Form must has enctype=multipart/form-data.");
        writer.flush();
    }

}