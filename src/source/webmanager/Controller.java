package webmanager;

import webmanager.database.DatabaseController;
import webmanager.file.FileManager;
import webmanager.properties.PropertyManager;
import webmanager.renderer.RenderExecutor;
import webmanager.sender.SendExecutor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/controller")
@MultipartConfig
public class Controller extends HttpServlet {
    public static Logger logger = Logger.getLogger(Controller.class.getName());

    private static DatabaseController databaseController;
    private FileManager fileManager;

    @Override
    public void init() {
        logger.info("Controller initialization");
        PropertyManager.setServletContext(getServletContext());
        fileManager = new FileManager(getServletContext());
        DatabaseController.init(PropertyManager.getInstance().getProperty(PropertyManager.DATABASE_INITIALIZE));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String page = SendExecutor.execute(request, response, fileManager, databaseController);
        RenderExecutor.execute(page, request, databaseController, fileManager);

        page = "/" + page + ".jsp";

        request.setAttribute("contextPath", request.getContextPath());
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
