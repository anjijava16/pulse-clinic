package com.pulse.desktop.view.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

/**
 *
 * @author ninja
 */
public class WordReplacer {
    
    public WordReplacer() {
    }
    
    public void swap(File file, String pattern, String replaceTo) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(file);
        XWPFDocument docx = new XWPFDocument(fis);
        List<XWPFParagraph> parag  = docx.getParagraphs();
        
        for ( XWPFParagraph xwpfPar: parag ) {
            String paragraphText = xwpfPar.getParagraphText();
            if (paragraphText.contains(pattern)) {
                List<XWPFRun> listRuns = xwpfPar.getRuns();
                for(int x = 0; x< listRuns.size(); x++) {
                    XWPFRun run = listRuns.get(x);
                    List<CTText> listCtText = run.getCTR().getTList();
                    for(int y = 0; x< listCtText.size(); x++) {
                        CTText ctText = listCtText.get(x);
                        if (ctText.getStringValue().contains(pattern)){
                            run.setText(replaceTo);
                        }
                    }
                }
            }
        }
        FileOutputStream fos = new FileOutputStream(file);
        docx.write(fos);
        
        fis.close();
        fos.close();
    }
}

