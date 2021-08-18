package controllers;

import java.io.IOException;

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
 *
 * @author ryouta.osada
 *
 */
@WebServlet("/show")
public class ShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 送られてきたidをもとにタスクデータを取得、リクエストスコープにセットしてshow.jspを呼び出す
     * @see HttpServlet#HttpServlet()
     */
    public ShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        //該当のIDのタスク1件のみをデータベースから取得
        Task t = em.find(Task.class,Integer.parseInt(request.getParameter("id")));
        em.close();

        //タスクデータをリクエストスコープにセットして、show.jspを呼び出す
        request.setAttribute("task", t);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/content/show.jsp");
        rd.forward(request, response);
    }

}