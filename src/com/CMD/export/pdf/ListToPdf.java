package com.CMD.export.pdf;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.datatable.DataTable;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ListToPdf {

    public enum Orientation{
        PORTRAIT, LANDSCAPE
    }


    public boolean doPrintToPdf(List<List> list, File saveLoc, Orientation orientation){
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

            BaseTable datTable = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, true, true);
            DataTable table = new DataTable(datTable, page);
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
}
