package contact.test;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class ContactTest {
    public static void main(String[] args) throws DocumentException, IOException {


        boolean a = true;
        while(a) {
            System.out.println("1.添加");
            System.out.println("2.删除");
            System.out.println("3.修改");
            System.out.println("4.退出");
            Scanner in = new Scanner(System.in);
            System.out.println("请输入：");
            int n = in.nextInt();
            switch (n) {
                case 1:
                    addDoc();
                    break;
                case 2:
                    delDoc();
                    break;
                case 3:
                    editDoc();
                    break;
                case 4:
                    a = false;
                    break;
                default:
                    System.out.println("输入错误，请重新输入：");
            }
        }
    }


    private static void editDoc() throws DocumentException, IOException {
        Document doc = new SAXReader().read(new File("E:"+File.separator+"contact.xml"));
        Element rootElem = doc.getRootElement();
        List<Element> conlist = rootElem.elements("contact");
        Scanner in = new Scanner(System.in);
        System.out.print("请输入要修改的信息id:");
        String s = in.nextLine();

        for(Element i :conlist){
            if(s.equals(i.attributeValue("id"))){
                System.out.print("是否修改id(y/n):");
                if(in.nextLine().equals("y")){
                    System.out.print("修改后的id:");
                    String s1 = in.nextLine();
                    i.attribute("id").setValue(s1);
                }

                boolean a = true;
                while(a){
                    System.out.print("想要修改什么信息(name/gender/phone/email/address):");
                    String str1 = in.nextLine();
                    System.out.print("修改为:");
                    String str2 = in.nextLine();
                    i.element(str1).setText(str2);
                    System.out.print("是否继续修改(y/n):");
                    if(in.nextLine().equals("n")){
                        a = false;
                    }
                }

            }
        }
        outPut(doc);
    }

    private static void delDoc( ) throws DocumentException, IOException {
        Document doc = new SAXReader().read(new File("E:"+File.separator+"contact.xml"));
        Element rootElem = doc.getRootElement();
        List<Element> conlist = rootElem.elements("contact");
        Scanner in = new Scanner(System.in);
        System.out.print("请输入要删除的id:");
        String s = in.nextLine();
        for(Element i :conlist){
            if(s.equals(i.attributeValue("id"))){
                i.detach();
                outPut(doc);
                return;
            }
        }
        System.out.println("id不存在，请重新输入");

    }

    private static void addDoc() throws DocumentException, IOException {
        Document doc = new SAXReader().read(new File("E:"+File.separator+"contact.xml"));
        Element rootElem = doc.getRootElement();
        List<Element> conlist = rootElem.elements("contact");
        Scanner in = new Scanner(System.in);
        String[] message = new String[5];
        String[] str = {"name","gender","phone","email","address"};
        System.out.println("依次输入信息：");
        System.out.print("id:");
        String id = in.nextLine();
        for(int i =0;i<str.length;i++){
            System.out.print(str[i]+":");
            message[i] = in.nextLine();
        }
        Element conElem = DocumentHelper.createElement("contact");
        conElem.addAttribute("id",id);
        for(int i = 0;i<str.length;i++){
            Element a = conElem.addElement(str[i]);
            a.addText(message[i]);
        }
        conlist.add(conElem);
        outPut(doc);
    }


    private static void outPut(Document doc) throws IOException {
        OutputStream out = new FileOutputStream("E:/contact.xml");


        OutputFormat format = OutputFormat.createPrettyPrint();

        XMLWriter writer = new XMLWriter(out,format);

        writer.write(doc);

        writer.close();
    }
}
