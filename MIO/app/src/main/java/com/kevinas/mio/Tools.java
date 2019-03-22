package com.kevinas.mio;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
/*
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
*/
import android.os.Environment;
public class Tools extends MainCore {
    public void writeFileOnInternalStorage(String sFileName, byte towrite[]){
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(sFileName, Context.MODE_APPEND);
            outputStream.write(towrite);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static class ServerStart extends Thread
    {
        int port = 3113;
        ServerSocket server;
        public void ClientHandler(Socket client, DataInputStream in,  DataOutputStream out) {

        }
        public void listen()
        {
            try{
                this.server = new ServerSocket(this.port);
            }
            catch(IOException e) {
                this.listen();
            }
        }
        public void run()
        {
            listen();
            while (true)
            {
                try{
                    Socket client = server.accept();
                    DataOutputStream out = new DataOutputStream(client.getOutputStream());
                    DataInputStream in = new DataInputStream(client.getInputStream());
                    ClientHandler(client, in, out);
                }catch (IOException e)
                {

                }
            }
        }
    }

    public static class toArrayList<E>
    {
        public ArrayList<E> toArrayList(E any[])
        {
            ArrayList<E> to_return = new ArrayList<>();
            for (int i = 0; i < any.length;i++)
            {
                to_return.add(any[i]);
            }
            return to_return;
        }
    }

    public Tools(Context cont)
    {
        this.context = cont;
    }
    public Tools()
    {
    }
    public boolean writeFilee(String file, byte data[])
    {
        File path = context.getFilesDir();
        if (!checkFile(file)){
            createFile(file);
        }
        File yay = new File(path, file);
        try{
            FileOutputStream wow = new FileOutputStream(yay);
            wow.write((data));
            return true;
        }
        catch(Exception err)
        {
            return false;
        }
    }

    public boolean writeFilee(String file, String data)
    {
        return writeFilee(file, (data.getBytes()));
    }

    public void delete(String file)
    {
        File yay = new File(file);
        yay.delete();
        try{
            deleteFile(file);
        }catch(Exception err){}

    }
    public String getFileAddress(String file)
    {
        File path0 = context.getFilesDir();
        File path1 = new File(path0, file);
        return path1.getPath();
    }
    public boolean checkFile(String the_file)
    {
        File path = context.getFilesDir();
        File test = new File(path, the_file);
        return test.exists();
    }
    public String test()
    {
        return String.valueOf(context.getFilesDir());
    }

    public byte[] charToByte(char convert[])
    {
        CharBuffer cb = CharBuffer.wrap(convert);
        ByteBuffer bb = Charset.forName("UTF-8").encode(cb);
        byte[] bytes = Arrays.copyOfRange(bb.array(), bb.position(), bb.limit());
        Arrays.fill(cb.array(), '\u0000');
        Arrays.fill(bb.array(), (byte) 0);
        return bytes;
    }
    public byte[] Bytetobyte(Byte convert[])
    {
        byte yay[] = new byte[convert.length];
        int i = 0;
        for (Byte a: convert)
        {
            yay[i++] = a;
        }
        return yay;
    }

    public Byte[] bytetoByte(byte convert[])
    {
        Byte yay[] = new Byte[convert.length];
        int i = 0;
        for (byte a: convert)
        {
            yay[i++] = a;
        }
        return yay;
    }
    public boolean writeFile(String file, String to_input)
    {
        File path = context.getFilesDir();
        if (!checkFile(file)){createFile(file);}
        File filee = new File(path, file);
        try {
            FileOutputStream out = new FileOutputStream(filee);
            out.write((to_input.getBytes()));
            out.close();
            return true;
        }catch (Exception err)
        {
            return false;
        }
    }
    public boolean writeFile(String file, byte to_input[])
    {
        File path = context.getFilesDir();
        if (!checkFile(file)){createFile(file);}
        File filee = new File(path, file);
        try {
            FileOutputStream out = new FileOutputStream(filee);
            out.write((to_input));
            out.close();
            return true;
        }catch (Exception err)
        {
            return false;
        }
    }

    public void createFile(String file)
    {
        try {
            File path = context.getFilesDir();
            Formatter yay = new Formatter(path, file);
        }catch(Exception yay){}
    }
    public byte[] readFileByte(String file)
    {
        File path = context.getFilesDir();
        if (!checkFile(file)){createFile(file);}
        File filee = new File(path, file);
        int length = (int) filee.length();
        byte bytes[] = new byte[length];
        try{
            FileInputStream in = new FileInputStream(filee);
            in.read(bytes);

            return (bytes);
        }
        catch(Exception err){
            return "Error ReadFileByte".getBytes();
        }
    }
    public String readFile(String file)
    {
        File path = context.getFilesDir();
        if (!checkFile(file)){createFile(file);}
        File filee = new File(path, file);
        int length = (int) filee.length();
        byte bytes[] = new byte[length];
        try{
            FileInputStream in = new FileInputStream(filee);
            in.read(bytes);
            String to_return = new String((bytes));
            return to_return;
        }
        catch(Exception err){
            return "Error ReadFile";
        }
    }
    public  void setloading(boolean loader)
    {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        if (loader)

        {
            dialog.show();
        }
        if (!loader)

        {
            dialog.hide();
        }
    }
public static int countchr(String s, char c)
{
    int a = 0;
    for (int i = 0;i < s.length();i++)
    {
        if (s.charAt(i)==c)
        {
            ++a;
        }
    }
    return a;
    }
    public String mergeString(String any[])
    {
        String to_return = "";
        for (int i = 0; i < any.length;i++)
        {
            to_return += any[i];
        }
        return to_return;
    }

    public static String[] splitstr(String s, char tosplit)
    {
        s+=tosplit;
        String to_return[] = new String[countchr(s, tosplit)], temp="";
        int a = 0;

        for (int i =0 ;i < s.length(); i++)
        {
            char periksa = s.charAt(i);
            if (periksa==tosplit)
            {
                to_return[a] = temp;
                temp="";
                a+=1;
            }
            else{
                temp += String.valueOf(periksa);
            }
        }
        return to_return;
    }


    public void wait(int sec)
    {
        sec *= 1000;
        try{
            Thread.sleep(sec);
        }
        catch (Exception err)
        {}
    }
    byte[] trim(byte[] bytes)
    {
        int i = bytes.length - 1;
        while (i >= 0 && bytes[i] == 0)
        {
            --i;
        }
        return Arrays.copyOf(bytes, i + 1);
    }
    public class SenderByte extends AsyncTask<String, Void, Byte[]> {
        String lets_send = "", lets_receive="";
        String ip = "192.168.43.171";
        int port = 7777, mode = 0;
        public int buffer = 1000000;
        //mode 0: send,
        public String onPostExcecute(String ... s)
        {
            //mana saia tau, saia kan c++
            return "oke";
        }
        public void onProgressUpdate(String ... s)
        {
            //mana saia tau, saia kan c++
        }
        public Byte[] doInBackground(String ... str) {
            byte[] to_return = new byte[buffer];
            Socket connection;
            try {
                connection = new Socket(ip, port);
                connection.setReuseAddress(true);
                DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
                writer.write(str[0].getBytes());
                DataInputStream reader = new DataInputStream(connection.getInputStream());
                //reader.read(to_return);
                int x, tanda = 0;
                while (true)
                {
                    x = reader.read();
                    if ((x==-1))
                    {
                        break;
                    }
                    to_return[tanda] = (byte)x;
                    tanda++;
                }
                connection.close();
            } catch (UnknownHostException error) {
                to_return = "Error1".getBytes();
            } catch (IOException err) {
                to_return = ("Error2").getBytes();
            } catch (Exception err) {
                to_return = ("Error_class :" + (String.valueOf(err))).getBytes();
            }
            //print(to_return);
            return bytetoByte(to_return);
        }
    }

    public class Sender extends AsyncTask<String, Void, String> {
        String lets_send = "", lets_receive="";
        String ip = "192.168.43.171";
        int port = 7777, mode = 0;
        //mode 0: send,
        public String onPostExcecute(String ... s)
        {
            //mana saia tau, saia kan c++
            return "oke";
        }
        public void onProgressUpdate(String ... s)
        {
            //mana saia tau, saia kan c++
        }
        public String doInBackground(String ... str) {
            String to_return = " Error Connection Error. Please Check Your Internet";
            try {
                Socket connection = new Socket(this.ip, this.port);
                connection.setReuseAddress(true);
                DataOutputStream s = new DataOutputStream(connection.getOutputStream());
                //SocketAddress addr = connection.getRemoteSocketAddress();
                s.writeUTF(str[0]);
                BufferedReader fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                to_return = (fromServer.readLine());
                s.flush();
                s.close();
                connection.close();
            } catch (UnknownHostException error) {
                to_return = "Error1";
            } catch (IOException err) {
                to_return = ("Error2");
            } catch (Exception err) {
                to_return += "Error_class :" + (String.valueOf(err));
            }
            //print(to_return);
            return to_return;
        }
    }

    public String send(String to_send)
    {

        Sender sendd = new Sender();
        AsyncTask<String, Void, String> yoi = (sendd.execute(to_send, ""));
        try {
            return yoi.get();
        }catch (Exception err)
        {
            return "Error_send: " + (err.toString());
        }
    }
    public byte[] send(String to_send, int buffer) {
        SenderByte sendd = new SenderByte();
        //sendd.buffer = buffer;
        AsyncTask<String, Void, Byte[]> yoi = (sendd.execute(to_send, ""));
        //sendd.buffer = 1024;
        try {
            return Bytetobyte(yoi.get());
        } catch (Exception err) {
            return (("Error_send: " + (err.toString())).getBytes());
        }
    }


    public int countInStr (String a, String b)
    {
        String temp = "";
        int x = 0;
        for (int i = 0; i < b.length(); i++)
        {
            if (b.charAt(i)==a.charAt(0))
            {
                //possibility
                for (int c = i; c < (i+a.length()); c++)
                {
                    try{
                        temp += b.charAt(c);
                    }catch (Exception err)
                    {

                    }
                }
                //check. pake == kok false ya\
                int benar = 0;
                for (int e = 0; e < a.length();e++)
                {
                    if (a.charAt(e)==temp.charAt(e))
                    {
                        benar++;
                    }
                }
                if (benar==a.length()){
                    x++;
                    i+=a.length()-1;
                }
            }
        }
        return x;
    }
}
