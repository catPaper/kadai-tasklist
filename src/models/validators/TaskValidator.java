package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Task;

/**
 * タスク内容が空欄にならないようバリデーションを行う
 * @author ryouta.osada
 *
 */
public class TaskValidator {
    /**
     * Taskテーブル内のデータの必須入力をチェックを行い、空欄のデータがある場合、Listにエラー文を追加して返すメソッド
     * @param t Task型（DTOクラス）のデータ
     * @return 空欄項目に応じたエラー文のリスト
     */
    public static List<String> validate(Task t){
        //現在はタスク内容のみだが今後の拡張のためListで管理
        List<String> errors = new ArrayList<String>();

        String error = validateContent(t.getContent());
        if(!error.equals("")) {
            errors.add(error);
            //空白文字が残らないようリセット
            t.setContent("");
        }

        return errors;
    }

    /**
     * タスク内容の必須入力をチェックし、空欄の場合(スペース文字含む)はエラー文を返すメソッド
     * @param content 必須入力をチェックを行うタスク内容
     * @return contentが空欄の場合エラー文、そうでない場合""を返す
     */
    private static String validateContent(String content) {
        if(content == null || "".equals(content.strip())) {
            return "タスク内容を入力してください。";
        }
        return "";
    }
}
