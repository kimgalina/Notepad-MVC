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

import java.util.ArrayList;
import java.util.List;


public class Print implements Printable {
  private int[] pageBreaks;
  private List<String> textLinesList;
  private final Font font;
  private final Font teamFontName;
  private final Font fontPageNumber;
  private final String textPageNumber;
  private final String textNameTeam;
  private boolean aDocumentHasBeenPrinted;
  private FontMetrics metrics;

    public Print(List<String> textLinesList, Font font, String textPageNumber) throws Exception {
      if (textLinesList == null) {
          throw new Exception("Invalid text content");
      }
      if (font == null) {
          throw new Exception("Invalid font object");
      }

      this.textPageNumber = textPageNumber;
      textNameTeam = "Notepad TMDP team";
      fontPageNumber = new Font("Consolas", Font.PLAIN, 14);
      teamFontName = new Font("Vladimir Script", Font.BOLD, 16);
      this.textLinesList = textLinesList;
      this.font = font;
    }

    public boolean isPrinted() {
        return aDocumentHasBeenPrinted;
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
              metrics = null;
          }
      }
    }

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
      g2d.translate(pf.getImageableX(), pf.getImageableY());
      g2d.setFont(font);
      g2d.setColor(Color.BLACK);

      int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex - 1];
      int end = (pageIndex == pageBreaks.length)
              ? textLinesList.size() : pageBreaks[pageIndex];
      for (int line = start; line < end; line++) {
          y += lineHeight;
          g.drawString(textLinesList.get(line), x, y);
      }
      return PAGE_EXISTS;
    }

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

    private int getLineWidth(String line, int index) {
        char[] symbols = new char[index];
        line.getChars(0, index, symbols, 0);
        int lineWidth = metrics.charsWidth(symbols, 0, index);

        symbols = null;
        return lineWidth;
    }

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
