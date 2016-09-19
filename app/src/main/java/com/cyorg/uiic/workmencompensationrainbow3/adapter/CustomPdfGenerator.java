package com.cyorg.uiic.workmencompensationrainbow3.adapter;

import android.content.Context;
import android.util.Log;

import com.cyorg.uiic.workmencompensationrainbow3.model.SingleEntryModel;
import com.cyorg.uiic.workmencompensationrainbow3.utils.CommonUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoganand on 18-09-2016.
 */
public class CustomPdfGenerator {

    private static final String TAG = CustomPdfGenerator.class.getSimpleName();
    //private static final String TITLE_TAG = "Workmen Compensation - ";

    public static String generatePdf(Context context) {

        Document finalReport;
        //PdfWriter pdfWriter;
        String filePath = CommonUtils.filePath;
        float[] columnWidths = {3f, 1.5f, 1f, 3f, 4f, 2f};

//        Font textFoneBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
//        Font textFontRegular = new Font(Font.FontFamily.TIMES_ROMAN, 12);

        try {
            finalReport = new Document();

            finalReport.addCreationDate();
            finalReport.setPageSize(PageSize.A4);
            //finalReport.addTitle(TITLE_TAG + CommonUtils.insurerName);

            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(85f);

            for (String headerCell : getTableHeaderNames()) {
                table.addCell(headerCell);
            }
            table.setHeaderRows(1);

            for (SingleEntryModel entryModel : CommonUtils.getDataList()) {
                table.addCell(String.valueOf(entryModel.getSalary()));
                table.addCell(String.valueOf(entryModel.getNoPersons()));
                table.addCell(String.valueOf(CommonUtils.rate));
                table.addCell(String.valueOf(entryModel.getAnnSalary()));
                table.addCell(String.valueOf(entryModel.getSumInsured()));
                table.addCell(String.valueOf(entryModel.getPremium()));
            }

            for (PdfPCell cell : getTotalRow()) {
                table.addCell(cell);
            }

            for (PdfPCell cell : getDiscountRow()) {
                table.addCell(cell);
            }

            for (PdfPCell cell : getServiceTaxRow()) {
                table.addCell(cell);
            }

            for (PdfPCell cell : getFinalPremiumRow()) {
                table.addCell(cell);
            }

            PdfWriter.getInstance(finalReport, new FileOutputStream(filePath));
            finalReport.open();

            finalReport.add(table);

            finalReport.close();

        } catch (Exception exception) {
            Log.e(TAG, "Exception Occured :: " + exception.getMessage());
        }


        return filePath;
    }

    private static List<PdfPCell> getTotalRow() {
        List<PdfPCell> totalRow = new ArrayList<>();

        PdfPCell totalStringCell = new PdfPCell();
        totalStringCell.setColspan(4);
        totalStringCell.setPhrase(new Phrase("Total"));
        totalRow.add(totalStringCell);

        PdfPCell sumInsuredTotalCell = new PdfPCell();
        sumInsuredTotalCell.setColspan(1);
        sumInsuredTotalCell.setPhrase(new Phrase(String.valueOf(CommonUtils.totalSumInsured)));
        totalRow.add(sumInsuredTotalCell);

        PdfPCell premiumTotalCell = new PdfPCell();
        premiumTotalCell.setColspan(1);
        premiumTotalCell.setPhrase(new Phrase(String.valueOf(CommonUtils.totalPremium)));
        totalRow.add(premiumTotalCell);

        return totalRow;

    }

    private static List<PdfPCell> getDiscountRow() {
        List<PdfPCell> discountRow = new ArrayList<>();

        PdfPCell discountStringCell = new PdfPCell();
        discountStringCell.setColspan(5);
        discountStringCell.setPhrase(new Phrase("Discounted Premium @ " + CommonUtils.discount + "%"));
        discountRow.add(discountStringCell);

        PdfPCell discountValueCell = new PdfPCell();
        discountValueCell.setPhrase(new Phrase(String.valueOf(CommonUtils.discountedPremium)));
        discountRow.add(discountValueCell);

        return discountRow;
    }

    private static List<PdfPCell> getServiceTaxRow() {
        List<PdfPCell> serviceTaxRow = new ArrayList<>();

        PdfPCell serviceTaxStringCell = new PdfPCell();
        serviceTaxStringCell.setColspan(5);
        serviceTaxStringCell.setPhrase(new Phrase("Service Tax @ " + CommonUtils.discount + "%"));
        serviceTaxRow.add(serviceTaxStringCell);

        PdfPCell serviceTaxValueCell = new PdfPCell();
        serviceTaxValueCell.setPhrase(new Phrase(String.valueOf(CommonUtils.serviceTax)));
        serviceTaxRow.add(serviceTaxValueCell);

        return serviceTaxRow;
    }

    private static List<PdfPCell> getFinalPremiumRow() {
        List<PdfPCell> finalPremiumRow = new ArrayList<>();

        PdfPCell finalPremiumStringCell = new PdfPCell();
        finalPremiumStringCell.setColspan(5);
        finalPremiumStringCell.setPhrase(new Phrase("TOTAL PREMIUM"));
        finalPremiumRow.add(finalPremiumStringCell);

        PdfPCell finalPremiumValueCell = new PdfPCell();
        finalPremiumValueCell.setPhrase(new Phrase(String.valueOf(CommonUtils.finalPremium)));
        finalPremiumRow.add(finalPremiumValueCell);

        return finalPremiumRow;
    }

    private static String[] getTableHeaderNames() {
        String[] names = {"Monthly Salary", "No. Of Person", "Rate", "Annual Salary", "Sum Insured", "Premium"};
        return names;
    }

}
