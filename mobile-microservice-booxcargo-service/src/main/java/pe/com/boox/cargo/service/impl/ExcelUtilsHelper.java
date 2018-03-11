package pe.com.boox.cargo.service.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import pe.com.boox.cargo.model.Guia;

public class ExcelUtilsHelper {

	private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT);

    private String contentType;

    public ExcelUtilsHelper() {
        this.setContentType("application/vnd.ms-excel");
    }

    public final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Workbook workbook = this.createWorkbook(model, request);
        this.buildExcelDocument(model, workbook, request, response);
        response.setContentType(this.getContentType());
        this.renderWorkbook(workbook, response);
    }

    protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
        return new HSSFWorkbook();
    }

    protected void renderWorkbook(Workbook workbook, HttpServletResponse response) throws IOException {
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");

        @SuppressWarnings("unchecked")
        List<Guia> lista = (List<Guia>) model.get("guias");

        // create excel xls sheet
        Sheet sheet = workbook.createSheet("Spring MVC AbstractXlsView");

        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Date");

        // Create data cells
        int rowCount = 1;
        
        for (Guia bean : lista){
            Row courseRow = sheet.createRow(rowCount++);
            courseRow.createCell(0).setCellValue(bean.getNumeroGuia());
            courseRow.createCell(1).setCellValue(bean.getDireccionOrigen());
            courseRow.createCell(2).setCellValue(bean.getDireccionDestino());
            //courseRow.createCell(3).setCellValue(DATE_FORMAT.format(bean.getDate()));
        }
    }
}