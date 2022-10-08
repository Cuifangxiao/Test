import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import javax.swing.*;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.IOException;

public class print {

    public  void PDFprint() throws Exception {
        String printerName="Microsoft Print to PDF";
        PDDocument document = null;
        try {
            document = PDDocument.load(Window.file);
            PrinterJob printJob = PrinterJob.getPrinterJob();
            printJob.setJobName(Window.file.getName());
            if (printerName != null) {
                //Find and set up printers
                //Get all printers connected to this computer
                PrintService[] printServices = PrinterJob.lookupPrintServices();
                if(printServices == null || printServices.length == 0) {
                    System.out.print("Printing failed, no available printer found, please check.");
                    JOptionPane.showMessageDialog(null,"Printing failed","Warning Message",JOptionPane.PLAIN_MESSAGE);
                    return ;
                }
                PrintService printService = null;
                //Matches the specified printer
                for (int i = 0;i < printServices.length; i++) {
                    if (printServices[i].getName().contains(printerName)) {
                        printService = printServices[i];
                        break;
                    }
                }
                if(printService!=null){
                    printJob.setPrintService(printService);
                }else{
                    System.out.print("Can‘t printing, Couldn't find the name of" + printerName + "printer,please checkout");
                    return ;
                }
            }
            //Set the paper and scale
            PDFPrintable pdfPrintable = new PDFPrintable(document, Scaling.ACTUAL_SIZE);
            //Set up multi-page printing
            Book book = new Book();
            PageFormat pageFormat = new PageFormat();
            //Sets the print orientation
            pageFormat.setOrientation(PageFormat.PORTRAIT);
            //Longitudinal
            pageFormat.setPaper(getPaper());
            //Set the paper
            book.append(pdfPrintable, pageFormat, document.getNumberOfPages());
            printJob.setPageable(book);
            printJob.setCopies(1);
            //Set the number of copies printed
            //Add print properties
            HashPrintRequestAttributeSet pars = new HashPrintRequestAttributeSet();
            pars.add(Sides.DUPLEX);

            //Set up single and double pages
            printJob.print(pars);
        }finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static Paper getPaper() {
        Paper paper = new Paper();
        // The default is A4 paper, corresponding to 595 and 842 pixel widths and heights, respectively
        int width = 595;
        int height = 842;
        // Set the margin, the unit is pixels, 10mm margin, corresponding to 28px
        int marginLeft = 10;
        int marginRight = 0;
        int marginTop = 10;
        int marginBottom = 0;
        paper.setSize(width, height);
        // solves the problem that the printed content is empty
        paper.setImageableArea(marginLeft, marginRight, width - (marginLeft + marginRight), height - (marginTop + marginBottom));
        return paper;
    }
}
