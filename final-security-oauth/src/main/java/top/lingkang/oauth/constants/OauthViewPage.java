package top.lingkang.oauth.constants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * @author lingkang
 * Created by 2022/1/18
 */
public class OauthViewPage {
    private static final Log log = LogFactory.getLog(OauthViewPage.class);
    private static HashMap<String, String> viewPage = new HashMap<>();

    public static String getLoginCodeHtml() {
        String login_code = viewPage.get("login_code.html");
        if (login_code == null) {
            login_code = getFileContext("/templates/login_code.html");
            viewPage.put("login_code.html", login_code);
        }
        return login_code;
    }

    private static String getFileContext(String filePath) {
        try {
            URL resource = OauthViewPage.class.getResource(filePath);
            File file = new File(resource.getFile());
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                builder.append(temp + "\n");
            }
            try {
                reader.close();
            } catch (IOException e) {
            }
            return builder.toString();
        } catch (Exception e) {
            log.warn(e);
        }
        return null;
    }
}
