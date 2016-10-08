/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createbulksubdomainconfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author Pranay
 */
public class CreateBulkSubdomainConfig {

    /**
     * @param args the command line arguments
     */
    // config.php - {{base_url}}
    // database.php - {{db_user_name}}  {{db_pwd}}  {{db_name}}
    // .htaccess - {{rewrite_base}}
    private static String mHtAccess;
    private static String mConfig;
    private static String mDatabase;
    public static void main(String[] args) {
        CreateBulkSubdomainConfig mConfig = new CreateBulkSubdomainConfig();
        mConfig.Initialilze();
    }
    protected void Initialilze(){
        try{
            mHtAccess = new String(Files.readAllBytes(new File("./.htaccess").toPath()));
            mConfig = new String(Files.readAllBytes(new File("./config.php").toPath()));
            mDatabase = new String(Files.readAllBytes(new File("./database.php").toPath()));
            CreateFolders mFolders = new CreateFolders();
            mFolders.start("./bloodbanks_installation.xls");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    protected synchronized static String getHtAccessFileContent(String strFolderName){
        String strTemp = mHtAccess;
        return strTemp.replace("{{rewrite_base}}", strFolderName);
    }
    protected synchronized static String getConfigFileContent(String strFolderName){
        String strTemp = mConfig;
        strTemp = strTemp.replace("{{base_url}}", strFolderName);
        
//        return strTemp.replace("{{encrypt_key}}", new BigInteger(24, new SecureRandom()).toString(32));
        return strTemp.replace("{{encrypt_key}}", RandomStringUtils.randomAlphanumeric(16));
    }
    protected synchronized static String getDbFileContent(String strDbUsrName, String strDbPwd, String strDbName){
//        System.out.println(strDbUsrName+"\t"+strDbName+"\t"+strDbPwd);
        String strTemp = mDatabase;
        strTemp = strTemp.replace("{{db_user_name}}", strDbUsrName);
        strTemp = strTemp.replace("{{db_pwd}}", strDbPwd);
        return strTemp.replace("{{db_name}}", strDbName);
    }
}
