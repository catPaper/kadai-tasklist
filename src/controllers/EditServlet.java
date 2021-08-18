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
 * idからタスク情報を取得し、タスク情報とセッションIDをリクエストスコープに登録してedit.jspを呼び出す
 * @author ryouta.osada
 *
 */
@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //該当のIDのタスク１件のみをデータベースから取得
        EntityManager em = DBUtil.createEntityManager();
        Task t = em.find(Task.class,Integer.parseInt(request.getParameter("id")));
        em.close();

        //タスク情報とセッションIDをリクエストスコープに登録してedit.jspを呼び出す
        request.setAttribute("task", t);
        request.setAttribute("_token", request.getSession().getId());
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/content/edit.jsp");
        rd.forward(request, response);
    }

}
