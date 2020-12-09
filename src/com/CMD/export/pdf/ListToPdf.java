package com.CMD.export.pdf;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.datatable.DataTable;
import be.quodlibet.boxable.line.LineStyle;
import be.quodlibet.boxable.utils.PDStreamUtils;
import com.CMD.model.Record;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListToPdf {

    public enum Orientation{
        PORTRAIT, LANDSCAPE
    }

    private int totalBalance;

    public boolean doPrintToPdf(List<Record> records, File saveLoc, Orientation orientation){
        List<List> list = getDoubleList(records);
        try{
            if (saveLoc == null){
                return false;
            }
            if (!saveLoc.getName().endsWith(".pdf")){
                saveLoc = new File(saveLoc.getAbsolutePath() + ".pdf");
            }

//          Initialize document
            PDDocument doc = new PDDocument();
            PDPage page  = new PDPage();

//          Create a landscape page
            if (orientation == Orientation.LANDSCAPE){
                page.setMediaBox(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
            }else{
                page.setMediaBox(new PDRectangle(PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight()));
            }

            doc.addPage(page);
//          Initialize table
            float margin = 10;
            float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
            float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
            float yStart = yStartNewPage;
            float bottomMargin = 0;

            BaseTable datTable = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth,
                    margin, doc, page, true, true);
            DataTable table = new DataTable(datTable, page);
            table.getDataCellTemplateEven().setBorderStyle(new LineStyle(Color.BLACK, 1.5f));
            table.getDataCellTemplateOdd().setBorderStyle(new LineStyle(Color.BLACK, 1.5f));
            table.addListToTable(list, DataTable.HASHEADER);

            datTable.draw();
            doc.save(saveLoc);
            doc.close();

            return true;
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }


    private List<List> getDoubleList(List<Record> records) {
        totalBalance = 0;
        List<List> doubleList = new ArrayList<>();
        List headers = Arrays.asList("First Name", "Last Name", "Amount", "Month", "Year", "Details");
        doubleList.add(headers);
        records.forEach(m -> {
            List list = new ArrayList();
            list.add(m.getMemberFirstName());
            list.add(m.getMemberLastName());

            String amount = m.getAmount();
            list.add(amount);
            if(amount.startsWith("500")) {
                totalBalance += 500;
            } else
                totalBalance += Integer.parseInt(amount);

            list.add(m.getMonth());
            list.add(m.getYear());
            list.add(m.getDetails());
            doubleList.add(list);
        });
        List balance = Arrays.asList("Total Balance", "", totalBalance, "", "", "");
        doubleList.add(balance);

        return doubleList;
    }



}
