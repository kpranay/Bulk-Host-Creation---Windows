
package createbulksubdomainconfig;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Pranay
 */
public class FolderCopy extends Thread{
    String strSrc;
    String strDest;
    String strFolderName;
    String strDbUsrName, strDbPwd, strDbName;
    FolderCopy(String strSrc, String strDest, String strFolderName, String strDbUsrName, String strDbPwd, String strDbName){
        this.strSrc = strSrc;
        this.strDest = strDest+strFolderName;
        this.strFolderName = strFolderName;
        this.strDbUsrName = strDbUsrName;
        this.strDbPwd = strDbPwd;
        this.strDbName = strDbName;
    }
    public void run() {
        try{
            String cmd = "xcopy /E /I /Q /H \""+strSrc+"\" \""+strDest+"\"";
            System.out.println(cmd);
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            FileOutputStream mOut = new FileOutputStream(strDest+"/.htaccess");
            mOut.write(CreateBulkSubdomainConfig.getHtAccessFileContent(this.strFolderName).getBytes());
            mOut.close();
            mOut = new FileOutputStream(strDest+"/application/config/config.php");
            mOut.write(CreateBulkSubdomainConfig.getConfigFileContent(this.strFolderName).getBytes());
            mOut.close();
            mOut = new FileOutputStream(strDest+"/application/config/database.php");
//            System.out.println(CreateBulkSubdomainConfig.getDbFileContent(this.strDbUsrName,this.strDbPwd,this.strDbName));
            mOut.write(CreateBulkSubdomainConfig.getDbFileContent(this.strDbUsrName,this.strDbPwd,this.strDbName).getBytes());
            mOut.close();
            System.out.println("Completed >>"+strFolderName);
        }catch(IOException | InterruptedException ie){
            System.out.println("Error while copying >>"+strDest+"\n"+ie.getLocalizedMessage());
        }
    }
}
