package createbulksubdomainconfig;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Pranay
 */
public class CreateFolders {
    private static final int FOLDER_NAME_INDEX = 4;
    private static final int DB_USR_NAME_INDEX = 7;
//    private static final int DB_PASSWORD_INDEX = 8;
    private static final int DB_NAME_INDEX = 5;
    List<String> mDbs = new ArrayList<>();
    protected void start(String strXlsPath){
        String strSrcBasePath = new String("D:/kp/stable/health4all");
        String strDestBasePath = new String("D:/kp/stable/release/");
        try{
            FileInputStream fin = new FileInputStream(strXlsPath);
            HSSFWorkbook myWorkBook = new HSSFWorkbook (fin);
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator<Row> rowIterator = mySheet.iterator(); 
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next(); 
                String strFolderName = getCellValue(row.getCell(FOLDER_NAME_INDEX));
                if(mDbs.indexOf(strFolderName) != -1 || strFolderName.equals("***") || row.getCell(DB_NAME_INDEX).getCellStyle().getFillForegroundColor()== 10){
                    System.out.println("Not Creating >>"+strFolderName);
                    continue;
                }
                String strDBName = getCellValue(row.getCell(DB_NAME_INDEX));
                String strDBUserName = getCellValue(row.getCell(DB_USR_NAME_INDEX));
//                String strDBUserPwd = getCellValue(row.getCell(DB_PASSWORD_INDEX));
//                FolderCopy mCopy = new FolderCopy(strSrcBasePath, strDestBasePath,strFolderName, strDBUserName,strDBUserPwd, strDBName);
                mDbs.add(strFolderName);
                FolderCopy mCopy = new FolderCopy(strSrcBasePath, strDestBasePath,strFolderName, strDBUserName,"password", strDBName);
                mCopy.start();
//                break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    private static String getCellValue(Cell cell){
        
            
        String strCellValue = "";
        if(cell == null)
            return "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING: 
                strCellValue = cell.getStringCellValue();
                break; 
            case Cell.CELL_TYPE_NUMERIC: 
                strCellValue = ""+cell.getNumericCellValue();
                break; 
            case Cell.CELL_TYPE_BOOLEAN: 
                strCellValue = ""+cell.getBooleanCellValue();
                break; 
            default : break;
        }
        return strCellValue;
    }
}
