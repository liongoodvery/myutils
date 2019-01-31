package org.lion;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDStream;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ReplaceText implements Runnable {
    private final String inFile;
    private final String outFile;
    private final String searchString;
    private final String replacement;

    public ReplaceText(String inFile, String outFile, String searchString, String replacement) {
        this.inFile = inFile;
        this.outFile = outFile;
        this.searchString = searchString;
        this.replacement = replacement;
    }


    private PDDocument _ReplaceText(PDDocument document, String searchString, String replacement) throws IOException {

        PDPageTree pages = document.getPages();
        for (PDPage page : pages) {
            PDFStreamParser parser = new PDFStreamParser(page);
            parser.parse();
            List tokens = parser.getTokens();

            for (int j = 0; j < tokens.size(); j++) {
                Object next = tokens.get(j);
                if (next instanceof Operator) {
                    Operator op = (Operator) next;

                    String pstring = "";
                    int prej = 0;

                    //Tj and TJ are the two operators that display strings in a PDF
                    if (op.getName().equals("Tj")) {
                        // Tj takes one operator and that is the string to display so lets update that operator
                        COSString previous = (COSString) tokens.get(j - 1);
                        String string = previous.getString();
                        string = string.replaceFirst(searchString, replacement);
                        previous.setValue(string.getBytes());
                    } else if (op.getName().equals("TJ")) {
                        COSArray previous = (COSArray) tokens.get(j - 1);
                        for (int k = 0; k < previous.size(); k++) {
                            Object arrElement = previous.getObject(k);
                            if (arrElement instanceof COSString) {
                                COSString cosString = (COSString) arrElement;
                                String string = cosString.getString();

                                if (j == prej) {
                                    pstring += string;
                                } else {
                                    prej = j;
                                    pstring = string;
                                }
                            }
                        }


                        if (searchString.equals(pstring.trim())) {
                            COSString cosString2 = (COSString) previous.getObject(0);
                            cosString2.setValue(replacement.getBytes());

                            int total = previous.size() - 1;
                            for (int k = total; k > 0; k--) {
                                previous.remove(k);
                            }
                        }
                    }
                }
            }

            // now that the tokens are updated we will replace the page content stream.
            PDStream updatedStream = new PDStream(document);
            OutputStream out = updatedStream.createOutputStream(COSName.FLATE_DECODE);
            ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
            tokenWriter.writeTokens(tokens);
            out.close();
            page.setContents(updatedStream);
        }

        return document;
    }

    public void run() {
        PDDocument document = null;
        try {
            document = PDDocument.load(new File(inFile));
            if (document.isEncrypted()) {
                System.err.println("Error: Encrypted documents are not supported for this example.");
                System.exit(1);
            }

            System.out.println(searchString + " => " + replacement);

            document = _ReplaceText(document, searchString, replacement);
            document.save(outFile);
        } catch (Exception e) {
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
