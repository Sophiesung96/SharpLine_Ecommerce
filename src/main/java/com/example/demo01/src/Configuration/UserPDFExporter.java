package com.example.demo01.src.Configuration;

import com.example.demo01.src.Pojo.Users;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserPDFExporter extends AbstractExporter {

    public void export(List<Users> userlist, HttpServletResponse response) throws IOException {
        super.setResponseHeader("application/pdf", ".pdf", response, "users_");
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(14);
        font.setColor(Color.BLUE);
        Paragraph paragraph = new Paragraph("List of Users", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        PdfPTable pdfPTable = new PdfPTable(6);
        pdfPTable.setWidthPercentage(100f);
        pdfPTable.setSpacingBefore(10);
        pdfPTable.setWidths(new float[]{1.2f, 3.5f, 3.0f, 3.0f, 3.0f, 1.7f});
        writeTableHeader(pdfPTable);
        writeTableData(pdfPTable, userlist);
        document.add(pdfPTable);
        document.close();
    }

    private void writeTableData(PdfPTable pdfPTable, List<Users> userlist) {
        for (Users user : userlist) {
            pdfPTable.addCell(String.valueOf(user.getId()));
            pdfPTable.addCell(user.getEmail());
            pdfPTable.addCell(user.getFirst_name());
            pdfPTable.addCell(user.getLast_name());
            pdfPTable.addCell(user.getUsersRole());
            pdfPTable.addCell(user.getEnabledStatus());
        }
    }

    //create table header
    private void writeTableHeader(PdfPTable pdfPTable) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.blue);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(14);
        font.setColor(Color.white);
        cell.setPhrase(new Phrase("ID", font));
        pdfPTable.addCell(cell);
        cell.setPhrase(new Phrase("E-mail", font));
        pdfPTable.addCell(cell);
        cell.setPhrase(new Phrase("First Name", font));
        pdfPTable.addCell(cell);
        cell.setPhrase(new Phrase("Last Name", font));
        pdfPTable.addCell(cell);
        cell.setPhrase(new Phrase("Role", font));
        pdfPTable.addCell(cell);
        cell.setPhrase(new Phrase("EnabledStatus", font));
        pdfPTable.addCell(cell);

    }


}
