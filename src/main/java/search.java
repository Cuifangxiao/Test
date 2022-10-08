import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;

public class search extends JDialog {
    JDialog jDialog = new JDialog(Window.jf);
    JLabel lookFor;
    JTextField lookFor_field;
    JButton jb1;
    JLabel replace;
    JTextField replace_field;
    JButton jb2;

    private int index;

    public search(int w,int h){
        jDialog.setBounds((w-400)/2,(h-150)/2,400,150);
        //初始化jDialog
        jDialogInit();
        jDialog.setVisible(true);
        jDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    void  jDialogInit(){
        jDialog.setTitle("Search");
        jDialog.setLayout(null);
        //Create a plate
        JPanel pane =new JPanel();
        //Add plates to the box
        jDialog.add(pane);
        //Set the layout of the pane
        pane.setLayout(new FlowLayout(FlowLayout.LEFT));
        pane.setBounds(0,0,400,70);
        //Plate contents
        //Find
        lookFor = new JLabel("Find  content    ");
        lookFor_field = new JTextField(17);
        Document dt = lookFor_field.getDocument();
        dt.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                index=0;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                index=0;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                index=0;
            }
        });

        jb1 = new JButton("Find Next");

        //Replac
        replace = new JLabel("replace content");
        replace_field = new JTextField(17);
        jb2 = new JButton("Replace  ");

        //Add elements to the pane
        pane.add(lookFor);
        pane.add(lookFor_field);
        pane.add(jb1);
        pane.add(replace);
        pane.add(replace_field);
        pane.add(jb2);

        //Add pane2
        JPanel panel2 = new JPanel();
        jDialog.add(panel2);

        //Set the layout of pane2
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel2.setBounds(0,70,400,50);

        //Initialize two buttons
        JButton replaceAll = new JButton("Replace All");
        JButton exit = new JButton("Cancel");

        //Add these two buttons to panel2
        panel2.add(replaceAll);
        panel2.add(exit);


        //Add monitoring to the button
        jb1.addActionListener(e -> search_word());
        jb2.addActionListener(e -> replace_word());
        replaceAll.addActionListener(e -> replace_All());
        exit.addActionListener(e -> Cancel());
    }


    void replace_word(){
        //Get the text of two input boxes
        String new_word = replace_field.getText();
        String old_word = lookFor_field.getText();

        //Get fonts in workArea
        String demo = Window.workArea.getText();

        //replace
        StringBuilder sb = new StringBuilder(demo);
        sb.replace(index-old_word.length(),index,new_word);

        //Add string to workArea
        Window.workArea.setText(sb.toString());
    }

    void replace_All(){
        String new_word = replace_field.getText();
        String old_word = lookFor_field.getText();
        String demo = Window.workArea.getText();
        //replace all
        Window.workArea.setText(demo.replace(old_word,new_word));
    }

    void search_word(){
        String word = lookFor_field.getText();
        int x = Window.workArea.getText().indexOf(word,index);
        //if(not find) return
        if(x == -1) {
            JOptionPane.showMessageDialog(null,"No searching","Warning Message",JOptionPane.PLAIN_MESSAGE);
            return;
        }
        //if find index+length
        int len = word.length();
        index = x + len;
        //Set Shadows
        Window.workArea.setSelectionStart(x);
        Window.workArea.setSelectionEnd(x+len);
    }



    void Cancel(){
        jDialog.dispose();
    }
}
