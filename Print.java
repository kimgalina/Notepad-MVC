import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import java.awt.print.Printable;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

public class Print implements Printable {
    private String data;
    private int[] pageBreaks;
    private List<String> textLinesList;
    private Font font;
    private Color textColor;
    private boolean aDocumentHasBeenPrinted;

    public Print(String data, Font font, Color textColor) throws Exception {
        if (data.isEmpty()) {
            throw new Exception("Invalid text content");
        }
        if (font == null) {
            throw new Exception("Invalid font object");
        }
        this.data = data;
        this.font = font;
        this.textColor = textColor;
    }

    public void printDocument() {
        PrinterJob job = PrinterJob.getPrinterJob();
        if (job == null) {
            return;
        }
        job.setPrintable(this);
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
                aDocumentHasBeenPrinted = true;
            } catch (PrinterException e) {
                System.err.println(e.getMessage());
            } finally {
                textLinesList.clear();
                textLinesList = null;
                pageBreaks = null;
            }
        }
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) {

        FontMetrics metrics = g.getFontMetrics(font);

        int x = 50;
        int y = 50;
        int lineHeight = metrics.getHeight();
        int pageHeight = (int) pf.getImageableHeight() - (y * 2);
        int pageWidth = (int) (pf.getImageableWidth()) - (x * 2);

        if (pageBreaks == null) {

            initTextLines(metrics, pageWidth);

            int linesPerPage = pageHeight / lineHeight;
            int numBreaks = (textLinesList.size() - 1) / linesPerPage;
            pageBreaks = new int[numBreaks];
            for (int b = 0; b < numBreaks; b++) {
                pageBreaks[b] = (b + 1) * linesPerPage;
            }
        }

        if (pageIndex > pageBreaks.length) {
            return NO_SUCH_PAGE;
        }

        Font fontForG = new Font("Vladimir Script", Font.ITALIC, 16);
        metrics = g.getFontMetrics(fontForG);

        int pageNumberX = (int) pf.getImageableWidth() - metrics.stringWidth("Page ") - x;
        int pageNumberY = (int) pf.getImageableHeight() - y / 2;

        g.setColor(Color.PINK);
        fontForG = new Font("Vladimir Script", Font.BOLD, 16);
        g.setFont(fontForG);
        g.drawString("Notepad TMDP team", x, pageNumberY);

        g.setColor(Color.PINK);
        fontForG = new Font("Consolas", Font.PLAIN, 14);
        g.setFont(fontForG);
        g.drawString("Page " + (pageIndex + 1), pageNumberX, pageNumberY);

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        g2d.setFont(font);
        
        Color color = new Color(205, 205, 205);
        if (textColor.equals(color) || textColor.equals(Color.WHITE)) {
            textColor = Color.BLACK;
        }
        g2d.setColor(textColor);

        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex - 1];
        int end = (pageIndex == pageBreaks.length)
                ? textLinesList.size() : pageBreaks[pageIndex];

        for (int line = start; line < end; line++) {
            y += lineHeight;
            g.drawString(textLinesList.get(line), x, y);
        }
        return PAGE_EXISTS;
    }

    private void initTextLines(FontMetrics metrics, int pageWidth) {

        if (textLinesList == null) {
            textLinesList = new ArrayList<>();
            String[] lines = data.split("\n");

            for (int line = 0; line < lines.length; line++) {

                String text = lines[line];
                int textWidth = metrics.stringWidth(text);

                if (textWidth > pageWidth) {
                    // The text does not fit on the width of the page, move it to a new line
                    while (textWidth > pageWidth) {
                        int cutoff = (text.length() * pageWidth) / textWidth;
                        if (cutoff == 0) {
                            cutoff = 1;
                        }
                        String lineText = text.substring(0, cutoff);

                        textLinesList.add(lineText);

                        text = text.substring(cutoff);
                        textWidth = metrics.stringWidth(text);
                    }
                }
                textLinesList.add(text);
            }
            lines = null;
        }
    }

    public boolean isPrinted() {
        return aDocumentHasBeenPrinted;
    }
}
