import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import org.junit.Test;

import java.util.Arrays;

/***
 * @author liang-shan@outlook.com
 * @createTime 2018-09-02
 * @DESC:
 */
public class UnderlineToCamelCaseAction extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        //获取编辑器
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
        PsiFile currentEditorFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        //获取选择的文字
        SelectionModel selectionModel = editor.getSelectionModel();
        String selectText = selectionModel.getSelectedText();
        if (selectText == null || !selectText.contains("_")) {
            return;
        }
        String str = underlineToCamel(selectText);
        WriteCommandAction.runWriteCommandAction(project, new Runnable() {
            @Override
            public void run() {
                editor.getDocument().replaceString(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd(), underlineToCamel(str));
            }
        });
    }

    private String underlineToCamel(String str) {
        String[] strings = str.split("_");
        StringBuffer buffer=new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            buffer.append(String.valueOf(strings[i].charAt(0)).toUpperCase() + strings[i].substring(1));
        }
        return buffer.toString();
    }

    @Test
    public void test() {
        System.out.println(underlineToCamel("xx_ff_ss"));
    }
}
