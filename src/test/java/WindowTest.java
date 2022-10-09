import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;



class WindowTest {
    Window window = new Window();

    @Test
    void openRtf() {
        //This file content is Qweqweqweqweqwe
        File file = new File("testrtf.rtf");
        String s = window.openRtf(file);
        assertEquals("Qweqweqweqweqwe\n", s);
    }

    @Test
    void openOdt() {
        File file = new File("TestOdt.odt");
        window.openOdt(file);
        assertEquals("Thisisatest odt file", Window.workArea.getText());
    }

    @Test
    void saveTxt(){
        String s = "This is a txt";
        File file = new File("TestTxt.txt");
        window.saveAstxt(file,s);
        window.openElse(file);
        assertEquals(s+"\r\n",Window.workArea.getText());
    }

    @Test
    void savePdf(){
        //this will creat a new pdf in the root directory
        Window.workArea.setText("qweqwe");
         File file=new File("a.pdf");
        boolean s;
        try {
           s= window.createPdf(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertTrue(s);
    }
}