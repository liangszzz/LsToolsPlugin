import com.google.gson.Gson;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;

import java.util.List;

/**
 * Created by liangshan on 2017/4/17.
 */
public class BaiduTranAction extends AnAction {

    private Application application=null;
    private Editor editor;
    private BaiduTran baiduTran=null;
    @Override
    public void actionPerformed(AnActionEvent e) {
        if(application==null){
            application= ApplicationManager.getApplication();
        }
        //获取编辑器
        editor=e.getData(PlatformDataKeys.EDITOR);
        //获取选择的文字
        String selectText = editor.getSelectionModel().getSelectedText();
        if(StringUtil.isNotEmpty(selectText)){
            if(baiduTran==null){
                baiduTran=application.getComponent(BaiduTran.class);
            }
            tranResult(baiduTran.query(selectText));
        }
    }

    private void tranResult(String str){
        Gson gson=new Gson();
        AllResult result=gson.fromJson(str,AllResult.class);
        StringBuffer buffer=new StringBuffer();
        for(TransResult transResult:result.trans_result){
            buffer.append(transResult.src+"  :  "+transResult.dst+"\n");
        }

        Messages.showInfoMessage(buffer.toString(),"翻译结果");
    }

    class AllResult{
        String from;
        String to;
        List<TransResult> trans_result;
    }

    class TransResult{
        String src;
        String dst;
    }

}
