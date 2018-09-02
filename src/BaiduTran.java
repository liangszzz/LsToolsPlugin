import baidu.translate.TransApi;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

/**
 * Created by liangshan on 2017/4/17.
 */
public class BaiduTran implements ApplicationComponent {
    public BaiduTran() {
    }
    private static final String APP_ID = "20170417000044996";
    private static final String SECURITY_KEY = "D_Wv1qWoo_MLH9If5Ikn";
    private TransApi api=null;
    @Override
    public void initComponent() {
        api = new TransApi(APP_ID, SECURITY_KEY);
    }

    @Override
    public void disposeComponent() {
        api=null;
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "translation";
    }

    public String query(String query){
        if(api==null){
            Messages.showMessageDialog("initComponent error","Error",Messages.getErrorIcon());
            return null;
        }
        String from="auto";
        String to="en";
        if(strIsEnglish(query)){
            to="zh";
        }
        if(strIsEnglish(query)){

        }
        return api.getTransResult(query,from,to);
    }

    /**
     *
     * @param word
     * @return true 中文 false 英文
     */
    private boolean strIsEnglish(String word) {
        boolean sign = true; // 初始化标志为为'true'
        int count=0;
        for (int i = 0; i < word.length(); i++) {
            if (!(word.charAt(i) >= 'A' && word.charAt(i) <= 'Z')
                    && !(word.charAt(i) >= 'a' && word.charAt(i) <= 'z')) {
                count++;
            }
        }
        if(word.length()<=2 && count<=1){
            return false;
        }
        if(count>word.length()/2){
            return false;
        }
        else {
            return true;
        }
    }
}
