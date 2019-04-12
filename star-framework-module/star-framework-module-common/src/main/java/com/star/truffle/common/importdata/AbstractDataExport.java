/**create by liuhua at 2018年1月20日 上午9:28:19**/
package com.star.truffle.common.importdata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;

import com.star.truffle.common.properties.Excel;
import com.star.truffle.core.web.config.SpringContextConfig;

public abstract class AbstractDataExport<T> {

  public static final String tempDir = "/opt/temp";
  private int pageSize = 5000;
  private Map<String, Object> params;
  private Excel excel;
  private boolean full;
  private Integer idx;
  
  public AbstractDataExport() {
    super();
    this.getApplication(SpringContextConfig.getApplicationContext());
    File dir = new File(tempDir);
    if (! dir.exists()) {
      dir.mkdirs();
    }
  }

  public Map<String, Object> getParams() {
    return this.params;
  }
  
  public void setParams(Map<String, Object> params) {
    this.params = params;
  }

  public abstract void getApplication(ApplicationContext applicationContext);
  
  public abstract Map<String, Object> getTemplateDatas();
  
  public abstract List<String[]> getRecordsData(Map<String, Object> params, int pageNumber, int pageSize);
  
  public String exportData() throws IOException {
    int pageNumber = 1;
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFCellStyle style = workbook.createCellStyle();
    style.setAlignment(HorizontalAlignment.CENTER);
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    style.setBorderBottom(BorderStyle.THIN);
    style.setBorderLeft(BorderStyle.THIN);//左边框    
    style.setBorderTop(BorderStyle.THIN);//上边框    
    style.setBorderRight(BorderStyle.THIN);//右边框  
    while(true) {
      if (! full) {
        full = true;
        Map<String, Object> args = getTemplateDatas();
        excel = ExcelUtil.fullExcel(excel, args);
      }
      List<String[]> datas = getRecordsData(params, pageNumber, getPageSize());
      XSSFSheet sheet = workbook.createSheet(excel.getSheetName() + (pageNumber > 1 ? pageNumber : ""));
      Map<Integer, Integer> columnWidth = ExcelUtil.createXlsxExcelSheetHead(sheet, excel, style);
      if (null == datas || datas.isEmpty()) {
        break;
      }
      ExcelUtil.createXlsxExcelSheetData(sheet, excel, columnWidth, datas, style);
      pageNumber ++;
    }
    String filename = ExcelUtil.fullDateOfNow(idx + ". " + excel.getFileName()) + ".xlsx";
    String filePath = tempDir + File.separator + filename;
    File xlsFile = new File(filePath);
    FileOutputStream fileOutputStream = new FileOutputStream(xlsFile);
    workbook.write(fileOutputStream);
    fileOutputStream.flush();
    fileOutputStream.close();
    workbook.close();
    return filePath;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public Excel getExcel() {
    return excel;
  }

  public void setExcel(Excel excel) {
    this.excel = excel;
  }

  public Integer getIdx() {
    return idx;
  }

  public void setIdx(Integer idx) {
    this.idx = idx;
  }
}
