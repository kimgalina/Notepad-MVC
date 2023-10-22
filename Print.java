import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import java.awt.print.Printable;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;

public class Print implements Printable {
  private final String data;

  private int[] pageBreaks;  // array of page break line positions.

  /* Synthesise some sample lines of text */
  private String[] textLines;

  private Font font;

  public Print(String data, Font font) {
    this.font = font;
    this.data = data;
  }

  private void initTextLines() {
    if (textLines == null) {
      int numLines = 2000;
      textLines = data.split("\n");
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

    if (pageBreaks == null) {
      initTextLines();
      int linesPerPage = ((int)pf.getImageableHeight() - 100) / lineHeight;
      int numBreaks = (textLines.length - 1) / linesPerPage;
      pageBreaks = new int[numBreaks];
      for (int b = 0; b < numBreaks; b++) {
        pageBreaks[b] = (b + 1) * linesPerPage;
      }
    }

    if (pageIndex > pageBreaks.length) {
      return NO_SUCH_PAGE;
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
    int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
    int end   = (pageIndex == pageBreaks.length)
    ? textLines.length : pageBreaks[pageIndex];
    for (int line = start; line < end; line++) {
      y += lineHeight;
      g.drawString(textLines[line], x, y);
    }

    Font fontColumnar = new Font("Serif", Font.PLAIN, 12);
    g.setFont(fontColumnar);
    g.drawString("Nurs " + (pageIndex + 1), 100, 20);
    g.drawString("Number of page: " + (pageIndex + 1), 100, ((int)pf.getImageableHeight() - 20));
    /* tell the caller that this page is part of the printed document */
    return PAGE_EXISTS;
  }
}
