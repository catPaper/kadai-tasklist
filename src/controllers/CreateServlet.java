package controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import models.validators.TaskValidator;
import utils.DBUtil;

/**
 * new.jspからポストされたデータをデータベースに新規登録し、タスク一覧画面に遷移するサーブレット
 * @author ryouta.osada
 *
 */
@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())){
            Task t = new Task();
            //requestに格納されているタスク内容をtにセット
            t.setContent(request.getParameter("content"));
            //現在時刻を作成日時と更新日時にセット
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            t.setCreated_at(currentTime);
            t.setUpdated_at(currentTime);

            //バリデーションを実行してエラーがあったら新規登録のフォームに戻る
            List<String> errors = TaskValidator.validate(t);
            if(errors.size() > 0) {
                //フォームに初期値を設定、さらにエラーメッセージを送る
                request.setAttribute("_token", _token);
                request.setAttribute("task", t);
                request.setAttribute("errors", errors);

                //newページへ遷移
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/content/new.jsp");
                rd.forward(request, response);
            }else {
              //データベースにtaskを新規保存
                EntityManager em = DBUtil.createEntityManager();
                em.getTransaction().begin();
                em.persist(t);
                em.getTransaction().commit();
                em.close();

                //indexページへリダイレクト
                response.sendRedirect(request.getContextPath() + "/index");
            }
        }
    }

}
