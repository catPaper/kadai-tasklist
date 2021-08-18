package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * データベースからタスクテーブルを全取得し、index.jspに渡し表示させるサーブレット
 * @author ryouta.osada
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //EntityManagerを使用してTaskテーブルの全データをリストに格納
        EntityManager em = DBUtil.createEntityManager();
        List<Task> tasks = em.createNamedQuery("getAllTasks",Task.class).getResultList();
        em.close();

        //requestにtaskのリストをセットし、index.jspを表示させる
        request.setAttribute("tasks", tasks);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/content/index.jsp");
        rd.forward(request, response);
    }
}
