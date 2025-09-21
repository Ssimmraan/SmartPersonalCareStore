package com.personalcare.services;

import com.personalcare.models.CartItem;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PDFService {
    public static String generateInvoiceFile(List<CartItem> cartItems, double totalAmount) {
        try {
            File invoicesDir = new File("invoices");
            if (!invoicesDir.exists()) invoicesDir.mkdirs();
            String filename = "invoices/invoice_" + System.currentTimeMillis() + ".pdf";

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Invoice", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{5, 1, 2, 2});

            table.addCell(getHeaderCell("Product"));
            table.addCell(getHeaderCell("Qty"));
            table.addCell(getHeaderCell("Unit Price"));
            table.addCell(getHeaderCell("Total"));

            for (CartItem item : cartItems) {
                table.addCell(item.getProduct().getName());
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(String.format("%.2f", item.getProduct().getPrice()));
                table.addCell(String.format("%.2f", item.getTotalPrice()));
            }

            document.add(table);
            document.add(new Paragraph(" "));

            Paragraph total = new Paragraph("Total Amount: $" + String.format("%.2f", totalAmount),
                    new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static PdfPCell getHeaderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
}
