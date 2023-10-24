import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import java.awt.print.Printable;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;

import java.util.ArrayList;
import java.util.List;

public class Print implements Printable {
    private final String data;

    private int[] pageBreaks;  // array of page break line positions.

    /* Synthesise some sample lines of text */
    private List<String> textLines;

    private Font font;

    public Print(String data, Font font) {
        this.font = font;
        this.data = data;
    }

    private void initTextLines(FontMetrics metrics, int pageHeight, int pageWidth) {

        if (textLines == null) {
            textLines = new ArrayList<>();
            String[] lines = data.split("\n");

            for (int line = 0; line < lines.length; line++) {
                System.out.println("line " + lines[line]);
                String text = lines[line];
                int textWidth = metrics.stringWidth(text);

                if (textWidth > pageWidth) {
                    // The text does not fit on the width of the page, move it to a new line
                    while (textWidth > pageWidth) {
                        int cutoff = (text.length() * (pageWidth - 100)) / textWidth;
                        if (cutoff == 0) {
                            cutoff = 1;
                        }
                        String lineText = text.substring(0, cutoff);
                        System.out.println("lineText " + lineText);

                        textLines.add(lineText);

                        text = text.substring(cutoff);
                        textWidth = metrics.stringWidth(text);
                    }
                }
                textLines.add(text);
                System.out.println("textLines size " + textLines.size());
            }
        }
    }


    public void printDocument() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
                javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Document printed.");
            } catch (PrinterException ex) {
                System.out.println("Errors in Print Document");
            }
        }
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) {
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int lineHeight = metrics.getHeight();
        int pageHeight = (int) pf.getImageableHeight();
        int pageWidth = (int) pf.getImageableWidth();

        if (pageBreaks == null) {
            initTextLines(metrics, pageHeight, pageWidth);
            int linesPerPage = ((int)pf.getImageableHeight() - 100) / lineHeight;
            int numBreaks = (textLines.size() - 1) / linesPerPage;
            pageBreaks = new int[numBreaks];
            for (int b = 0; b < numBreaks; b++) {
                pageBreaks[b] = (b + 1) * linesPerPage;
            }
        }

        if (pageIndex > pageBreaks.length) {
            return Printable.NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
        * translate by the X and Y values in the PageFormat to avoid clipping
        * Since we are drawing text we
        */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Draw each line that is on this page.
        * Increment 'y' position by lineHeight for each line.
        */
        int y = 50;
        int x = 50;
        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex - 1];

        int end = (pageIndex == pageBreaks.length)
                ? textLines.size() : pageBreaks[pageIndex];

        for (int line = start; line < end; line++) {
            String text = textLines.get(line);
            // int textWidth = metrics.stringWidth(text);
            //
            // if (textWidth > pageWidth) {
            //     // The text does not fit on the width of the page, move it to a new line
            //     while (textWidth > pageWidth) {
            //         int cutoff = (text.length() * pageWidth) / textWidth;
            //         String lineText = text.substring(0, cutoff);
            //         g.drawString(lineText, x, y);
            //         y += lineHeight;
            //         text = text.substring(cutoff);
            //         textWidth = metrics.stringWidth(text);
            //     }
            // }

            g.drawString(text, x, y);
            System.out.println("text drawString:  " + text);
            y += lineHeight;
        }

        Font fontColumnar = new Font("Serif", Font.PLAIN, 12);
        g.setFont(fontColumnar);
        g.drawString("Nurs " + (pageIndex + 1), ((int)pf.getImageableWidth() / 2), 20);
        System.out.println("Nurs " + pageIndex + 1);
        g.drawString("Number of page: " + (pageIndex + 1), ((int)pf.getImageableWidth() / 2), ((int)pf.getImageableHeight() - 20));
        /* tell the caller that this page is part of the printed document */
        // if (end < textLines.size()) {
        //     return Printable.PAGE_EXISTS;
        // } else {
        //     return Printable.NO_SUCH_PAGE;
        // }
        return Printable.PAGE_EXISTS;
    }
}
