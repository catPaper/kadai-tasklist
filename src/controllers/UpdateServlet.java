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
 * 送られてきたパラメータを該当idのデータに上書きしてデータベースを更新する
 * @author ryouta.osada
 *
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();
            //リクエストスコープからタスクのIDを取得して
            //該当のIDのタスク1件のみをデータベースから取得
            Task t = em.find(Task.class, Integer.parseInt(request.getParameter("id")));

            //編集可能な項目の数
            final int ALL_EDIT_DATA = 1;
            //変更していない項目数
            int noEditData = 0;
            String content = request.getParameter("content");
            if(content.equals(t.getContent())){
                noEditData++;
            }else {
                //フォームの内容をフィールドに上書き
                t.setContent(request.getParameter("content"));
            }
            //更新日時のみ更新
            t.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            //必須入力チェック
            List<String> errors = TaskValidator.validate(t);

            //全ての編集項目＝変更していない項目だった場合エラー文を追記
            if(noEditData == ALL_EDIT_DATA) {
                errors.add("更新するには項目を変更してください");
            }

            //エラーがあったら新規登録のフォームに戻る
            if(errors.size() > 0) {
                //フォームに初期値を設定、さらにエラーメッセージを送る
                request.setAttribute("_token", _token);
                request.setAttribute("task", t);
                request.setAttribute("errors", errors);

                //editページへ遷移
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/content/edit.jsp");
                rd.forward(request, response);
            }else {
                //データベースのtasksテーブルを更新
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                //indexページへリダイレクト
                response.sendRedirect(request.getContextPath() + "/index");
            }
        }
    }

}
