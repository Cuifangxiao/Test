import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Window extends JFrame {
    private int width_screen;
    private int height_screen;

    public static org.fife.ui.rsyntaxtextarea.RSyntaxTextArea workArea;
    private RTextScrollPane  scrollPane;
    public  JFrame jf = new JFrame();
    public static File file;
    private JLabel Time;
    private FileDialog saveDia;
    private static String str = "";
    private String currentTime;
    private int times = 0;
    Window() {
        Toolkit tit = Toolkit.getDefaultToolkit();
        Dimension screenSize = tit.getScreenSize();
        width_screen = screenSize.width;
        height_screen = screenSize.height;

        init(width_screen, height_screen);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        while (true){
            getTime();
            Time.setText(currentTime);
        }
    }

    void init(int w, int h) {
        //set main table
        jf.setTitle("Text Editor");
        jf.setBounds((w - 500) / 2, (h - 700) / 2, 500, 700);

        //Initialize the tab
        workArea = new org.fife.ui.rsyntaxtextarea.RSyntaxTextArea();
        workArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        scrollPane = new RTextScrollPane(workArea);


        jf.add(scrollPane);

        //Initialize the menu
        initMenuBar();

        //Set font
        workArea.setFont(new Font("Cui", Font.PLAIN, 20));
    }

    void initMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.setSize(200,30);
        jf.setJMenuBar(menuBar);
        Time = new JLabel();

        //Menu Bar
        JMenu menu_file = new JMenu("  File  ");
        JMenu menu_edit = new JMenu("  Edit  ");
        JMenu menu_view = new JMenu("  View  ");
        JMenu menu_help = new JMenu("  Help  ");
        menuBar.add(menu_file);
        menuBar.add(menu_edit);
        menuBar.add(menu_view);
        menuBar.add(menu_help);

        //Add here fot Time&Date and let it not visible
        menuBar.add(Time);
        Time.setVisible(false);

        //File
        JMenuItem fileItem_new = new JMenuItem("new");
        JMenuItem fileItem_open = new JMenuItem("open");
        JMenuItem fileItem_print = new JMenuItem("print");
        JMenuItem fileItem_exit = new JMenuItem("exit");

        //save
        JMenu file_save_menu = new JMenu("save");
        JMenuItem savetxt = new JMenuItem("save as '.txt'");
        JMenuItem savepdf = new JMenuItem("save as '.pdf'");
        file_save_menu.add(savetxt);
        file_save_menu.add(savepdf);


        menu_file.add(fileItem_new);
        menu_file.add(fileItem_open);
        menu_file.add(file_save_menu);
        menu_file.add(fileItem_print);
        menu_file.add(fileItem_exit);

        //Edit
        JMenuItem editItem_search = new JMenuItem("search");
        JMenuItem editItem_copy = new JMenuItem("copy");
        JMenuItem editItem_paste = new JMenuItem("paste");
        JMenuItem editItem_cut = new JMenuItem("cut");
        menu_edit.add(editItem_search);
        menu_edit.add(editItem_copy);
        menu_edit.add(editItem_paste);
        menu_edit.add(editItem_cut);

        //View Menu
        JMenuItem viewItem_TD = new JMenuItem("Time and Date");
        menu_view.add(viewItem_TD);
        //Help Menu
        JMenuItem helpItem_about = new JMenuItem("about");
        menu_help.add(helpItem_about);

        //new
        fileItem_new.addActionListener(e -> New());

        //open
        fileItem_open.addActionListener(e -> open());

        //exit
        fileItem_exit.addActionListener(e -> exit());

        //about
        helpItem_about.addActionListener(e -> about());

        //save
        savetxt.addActionListener(e -> saveAstxt());
        savepdf.addActionListener(e -> {
            try {
                saveAspdf();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //copy
        editItem_copy.addActionListener(e -> Copy());

        //paste
        editItem_paste.addActionListener(e -> Paste());

        //cut
        editItem_cut.addActionListener(e -> Cut());


        //search
        editItem_search.addActionListener(e -> Search());


        //print
        fileItem_print.addActionListener(e -> {
            try {
                printer();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //Time and Date
        viewItem_TD.addActionListener(e -> TD());
    }

    void Search(){
        new search(width_screen,height_screen);
    }

    //Get the Current time and pass in the currentTime.
    void getTime(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        currentTime = sdf.format(d);
    }

    void TD(){
        times++;
        //if click once open the label and click it will turn up
        if(times%2 == 0) {
            Time.setVisible(false);
            return;
        }
        Time.setVisible(true);
    }

    void New() {
//        //Whether to save the current file when creating a JDialog
//        JDialog jd1 = new JDialog(jf);
//        jd1.setLayout(new FlowLayout(FlowLayout.LEFT));
//        jd1.setBounds((width_screen-260)/2,(height_screen-130)/2,260,130);
//        jd1.setVisible(true);
//        jd1.setTitle("Notepad");
//        //add buttons
//        JLabel jLabel = new JLabel("Do you want to save changes?");
//        jLabel.setFont(new Font("QWE",Font.PLAIN,16));
//        JButton save = new JButton("Save as 'txt'");
//        JButton savePDF = new JButton("Save as 'pdf'");
//        JButton notSave = new JButton("Don't save");
//        JButton Cancel = new JButton("Cancel");
//        JPanel jp = new JPanel();
//        jp.setLayout(new GridLayout(2,2));
//
//        //ActionListener
//        save.addActionListener(e -> {
//            saveAstxt();
//            jd1.dispose();
//        });
//        savePDF.addActionListener(e -> {
//            try {
//                saveAspdf();
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
//            jd1.dispose();
//        });
//        notSave.addActionListener(e -> {
//            workArea.setText("");
//            jd1.dispose();
//        });
//        Cancel.addActionListener(e -> {
//            jd1.dispose();
//        });
//
//        //add
//        jd1.add(jLabel);
//        jd1.add(jp);
//        jp.add(save);
//        jp.add(savePDF);
//        jp.add(notSave);
//        jp.add(Cancel);
    }

    void open() {
        JFileChooser jFileChooser = new JFileChooser();
        int chose = jFileChooser.showOpenDialog(null);
        if (chose == JFileChooser.CANCEL_OPTION) {
            return ;
        }
        File F = jFileChooser.getSelectedFile();
        workArea.setText("");
        jf.setTitle(F.getName());
        if(F.getName().contains(".rtf")){
            openRtf(F);
        }else if(F.getName().contains(".odt")){
            openOdt(F);
        }else {
            openElse(F);
        }
    }

    //Open .rtf
    void openRtf(File F){
        DefaultStyledDocument styleDoc = new DefaultStyledDocument();
        String result;
        try {
            InputStream inputStream = new FileInputStream(F);
            try {
                new RTFEditorKit().read(inputStream,styleDoc,0);
                result = new String(styleDoc.getText(0,styleDoc.getLength()).getBytes("ISO8859-1"),"GBK");
            } catch (IOException | BadLocationException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        workArea.setText(result);
    }

    //open odt File
    void openOdt(File F){
        try {
            ZipFile zipFile = new ZipFile(F);
            org.w3c.dom.Document doc = null;
            Enumeration<?> entries = zipFile.entries();
            ZipEntry entry;
            while (entries.hasMoreElements()){
                entry = (ZipEntry)entries.nextElement();
                if(entry.getName().equals("content.xml")){
                    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                    domFactory.setNamespaceAware(true);
                    DocumentBuilder docBuilder = null;
                    try {
                        docBuilder = domFactory.newDocumentBuilder();
                    } catch (ParserConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        doc = docBuilder.parse(zipFile.getInputStream(entry));
                    } catch (SAXException e) {
                        throw new RuntimeException(e);
                    }
                    NodeList list = doc.getElementsByTagName("text:p");
                    for (int a = 0; a < list.getLength(); a++){
                        Node node =list.item(a);
                        getText(node);
                        workArea.setText(str);
                        str = "";
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getText(org.w3c.dom.Node node) {
        if (node.getChildNodes().getLength() > 1) {
            NodeList childNodes = node.getChildNodes();
            for (int a = 0; a < childNodes.getLength(); a++) {
                getText(node.getChildNodes().item(a));
            }
        } else {
            if (node.getNodeValue() != null) {
                str = str + node.getNodeValue();
            }
            if (node.getFirstChild() != null) {
                str = str + node.getFirstChild().getNodeValue();
            }
        }
    }

    void openElse(File F){
        if (F != null) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(F));
                String line;
                while((line = br.readLine()) != null){
                    workArea.append(line + "\r\n");
                }
            }catch (IOException er1){
                throw new RuntimeException("Failed!???");
            }
        }
    }
    void exit(){
        jf.dispose();
    }

    void about(){
        JOptionPane.showMessageDialog(null,"Qiu Yifan\n  Cui Fangxiao is handsome","About Us",JOptionPane.PLAIN_MESSAGE);
    }

    void Cut(){
        workArea.cut();
    }

    void Copy(){
        workArea.copy();
    }

    void Paste(){
        workArea.paste();
    }



    void saveAstxt(){
        saveDia = new FileDialog(this,"save as(A)",FileDialog.SAVE);
        File fileS = null;
        saveDia.setVisible(true);
        String dirPath = saveDia.getDirectory();
        String fileName = saveDia.getFile();
        if(dirPath == null || fileName == null) {
            return;
        }
        if (!fileName.contains(".txt")) {
            fileName += ".txt";
        }

        fileS = new File(dirPath,fileName);

        try{
            BufferedWriter bufw = new BufferedWriter(new FileWriter(fileS));
            String text = workArea.getText();
            bufw.write(text);
            bufw.close();
        }catch(IOException er){
            throw new RuntimeException("file saved failed");
        }
    }


    void saveAspdf() throws Exception{
        saveDia = new FileDialog(this,"save as(B)",FileDialog.SAVE);
        file = null;
        saveDia.setVisible(true);
        String dirPath = saveDia.getDirectory();
        String fileName = saveDia.getFile();
        if(dirPath == null || fileName == null) {
            return;
        }
        if (!fileName.contains(".pdf")) {
            fileName += ".pdf";
        }
        file = new File(dirPath,fileName);
        try {
            createPdf();
        }catch (IOException er){
            throw new RuntimeException("file saved failed");
        }
    }


    public void createPdf() throws Exception {
        String s = workArea.getText();
        String[] strings = s.split("\n");
        PDDocument document=new PDDocument();
        PDPage my_page=new PDPage(PDRectangle.A4);
        document.addPage(my_page);
        PDFont font= PDType0Font.load(document, new File("C:/Windows/Fonts/Arial.ttf"));
        PDPageContentStream contentStream = new PDPageContentStream(document,my_page);
        my_page.getResources().add(font);

        //set font for pdf
        workArea.getText(0,1);
        for(int i=0;i<strings.length;i++){
            contentStream.beginText();
            contentStream.setFont(font,10);
            contentStream.newLineAtOffset(10,  820-i*20);
            contentStream.showText(strings[i]);
            contentStream.endText();
        }
        contentStream.close();
        document.save(file);
        document.close();
    }

    void printer() throws Exception {
        file=new File("D:","a.pdf");
        createPdf();
        print print=new print();
        print.PDFprint();
        if(file.exists() && file.isFile()){
          System.out.println(file.delete());
       }
    }
}
