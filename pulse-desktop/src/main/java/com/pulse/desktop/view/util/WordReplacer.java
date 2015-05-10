/*
 * Copyright 2015 Vladimir Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pulse.desktop.view.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class WordReplacer {
    
    public WordReplacer() {
    }
    
    public void swap(File file, String pattern, String replaceTo) throws IOException {
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
