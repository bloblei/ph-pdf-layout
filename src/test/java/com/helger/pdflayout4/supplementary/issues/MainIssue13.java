package com.helger.pdflayout4.supplementary.issues;

import java.awt.Color;
import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;

public class MainIssue13
{
  public static void main (final String [] args) throws Exception
  {
    try (final PDDocument doc = new PDDocument ())
    {
      final PDPage page = new PDPage (new PDRectangle (250, 150));
      doc.addPage (page);

      try (final PDPageContentStream contentStream = new PDPageContentStream (doc, page))
      {
        final PDAnnotationLink txtLink = new PDAnnotationLink ();

        // border style
        final PDBorderStyleDictionary linkBorder = new PDBorderStyleDictionary ();
        linkBorder.setStyle (PDBorderStyleDictionary.STYLE_UNDERLINE);
        linkBorder.setWidth (10);
        txtLink.setBorderStyle (linkBorder);

        // Border color
        final Color color = Color.GREEN;
        final float [] components = new float [] { color.getRed () / 255f, color.getGreen () / 255f, color.getBlue () / 255f };
        txtLink.setColor (new PDColor (components, PDDeviceRGB.INSTANCE));

        // Destination URI
        final PDActionURI action = new PDActionURI ();
        action.setURI ("https://www.helger.com");
        txtLink.setAction (action);

        // Position
        final PDRectangle position = new PDRectangle ();
        position.setLowerLeftX (10);
        position.setLowerLeftY (10);
        position.setUpperRightX (200);
        position.setUpperRightY (10 + 2 + 10 + 2);
        txtLink.setRectangle (position);
        page.getAnnotations ().add (txtLink);

        // Main page content
        contentStream.beginText ();
        contentStream.newLineAtOffset (12, 12);
        contentStream.setFont (PDType1Font.COURIER_BOLD, 10);
        contentStream.showText ("This is linked to the outside world");
        contentStream.endText ();
      }

      // No need to save
      doc.save (new File ("target/issue13.pdf"));
    }
  }
}
