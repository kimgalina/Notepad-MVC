import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import java.awt.print.Printable;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.List;
import java.awt.Color;

/**
 * The Print class is responsible for printing a document using the Printable interface.
 */
public class Print implements Printable {
    private final Font font;
    private int[] pageBreaks;
    private List<String> textLinesList;
    private Color textColor;
    private FontMetrics metrics;
    private boolean aDocumentHasBeenPrinted;

    /**
    * Constructs a Print object for printing text.
    *
    * @param textLinesList  The list of text lines to print.
    * @param font           The font to use for printing.
    * @param textColor      The color of the printed text.
    * @throws Exception if the provided text content or font is invalid.
    */
    public Print(List<String> textLinesList, Font font, Color textColor) throws Exception {
        if (textLinesList == null) {
            throw new Exception("Invalid text content");
        }
        if (font == null) {
            throw new Exception("Invalid font object");
        }
        this.textLinesList = textLinesList;
        this.font = font;
        this.textColor = textColor;
    }

    /**
     * Checks if a document has been successfully printed.
     *
     * @return true if a document has been printed; otherwise, false.
     */
    public boolean isPrinted() {
        return aDocumentHasBeenPrinted;
    }

    /**
     * Initiates the process of printing the document.
     */
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

    /**
   * Implements the print method for rendering the text on each printed page.
   *
   * @param g          The Graphics object for rendering.
   * @param pf         The PageFormat for the printed page.
   * @param pageIndex  The index of the page to print.
   * @return PAGE_EXISTS if the page is successfully printed; otherwise, NO_SUCH_PAGE.
   */
    public int print(Graphics g, PageFormat pf, int pageIndex) {
        metrics = g.getFontMetrics(font);

        int x = 50;
        int y = 50;

        int lineHeight = metrics.getHeight();

        int paddingWidthPage = x * 2;
        int pageWidth = (int) (pf.getImageableWidth()) - paddingWidthPage;
        int pageHeight = (int) pf.getImageableHeight() - (y * 2);

        if (pageBreaks == null) {

            initTextLines(pageWidth);

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
        String textPageNumber = "Page ";
        String textNameTeam = "Notepad TMDP team";
        Font fontPageNumber = new Font("Consolas", Font.PLAIN, 14);
        Font teamFontName = new Font("Vladimir Script", Font.BOLD, 16);


        metrics = g.getFontMetrics(fontPageNumber);

        int pageNumberX = (int) pf.getImageableWidth() - metrics.stringWidth(textPageNumber) - x;
        int pageNumberY = (int) pf.getImageableHeight() - y / 2;

        g.setColor(Color.PINK);
        g.setFont(teamFontName);
        g.drawString(textNameTeam, x, pageNumberY);

        g.setColor(Color.PINK);
        g.setFont(fontPageNumber);
        g.drawString(textPageNumber + (pageIndex + 1), pageNumberX, pageNumberY);

        Graphics2D g2d = (Graphics2D) g;
        Color textDarkThemeColor = new Color(205, 205, 205);

        g2d.translate(pf.getImageableX(), pf.getImageableY());

        if (textColor.equals(textDarkThemeColor) || textColor.equals(Color.WHITE)) {
            textColor = Color.BLACK;
        }
        g2d.setFont(font);
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

    /**
    * Separates a line into two lines at a word boundary to fit within the specified page width.
    *
    * @param line       The line of text to separate.
    * @param pageWidth  The width of the printed page.
    * @param pos        The position of the line within the textLinesList.
    * @return The remaining portion of the line that couldn't fit on the current page.
    */
    private String separateLineByWords(String line, int pageWidth, int pos) {
        int index = 0;
        int lineWidth = -1;

        for (int i = 1; i <= line.length(); i++) {
            lineWidth = getLineWidth(line, i);

            if (pageWidth <= lineWidth) {
                break;
            }
            char symbol = line.charAt(i);
            if (symbol == ' ') {
                index = i;
            }
        }

        if (index == 0) {
            line = separateLine(line, pageWidth, pos);
        } else {
            String newLine = line.substring(0, index);
            textLinesList.set(pos, newLine);
            if (index == line.length()) {
                return null;
            }
            line = line.substring(index + 1);
        }

        return line;
    }

    /**
    * Separates a line into two lines at a character boundary to fit within the specified page width.
    *
    * @param line       The line of text to separate.
    * @param pageWidth  The width of the printed page.
    * @param pos        The position of the line within the textLinesList.
    * @return The remaining portion of the line that couldn't fit on the current page.
    */
    private String separateLine(String line, int pageWidth, int pos) {
        int index = 0;
        int lineWidth = -1;

        for (index = 0; index <= line.length(); index++) {
            lineWidth = getLineWidth(line, index);
            if (pageWidth <= lineWidth) {
                break;
            }
        }
        if (pageWidth < lineWidth) {
            index = index - 1;
        }

        String newLine = line.substring(0, index);
        textLinesList.set(pos, newLine);
        line = line.substring(index);

        return line;
    }

    /**
    * Calculates the width of the line up to a specified character index.
    *
    * @param line   The line of text.
    * @param index  The character index to calculate the width up to.
    * @return The width of the line up to the specified character index.
    */
    private int getLineWidth(String line, int index) {
        char[] symbols = new char[index];
        line.getChars(0, index, symbols, 0);
        int lineWidth = metrics.charsWidth(symbols, 0, index);

        symbols = null;
        return lineWidth;
    }

    /**
    * Initializes the textLinesList by splitting lines to fit within the specified page width.
    *
    * @param pageWidth  The width of the printed page.
    */
    private void initTextLines(int pageWidth) {
        for (int i = 0; i < textLinesList.size(); i++) {
            int strWidth = metrics.stringWidth(textLinesList.get(i));
            if (pageWidth <= strWidth) {
                boolean checkSpace = !textLinesList.get(i).contains(" ");
                do {
                    if (textLinesList.get(i) == null) {
                        textLinesList.remove(i);
                        i = i - 1;
                        break;
                    }
                    if (checkSpace) {
                        textLinesList.add(i + 1, separateLine(textLinesList.get(i), pageWidth, i));
                    } else {
                        textLinesList.add(i + 1, separateLineByWords(textLinesList.get(i), pageWidth, i));
                    }
                    i = i + 1;

                    strWidth = metrics.stringWidth(textLinesList.get(i));
                    checkSpace = !textLinesList.get(i).contains(" ");
                } while (pageWidth <= strWidth);
            }
        }
    }
}
