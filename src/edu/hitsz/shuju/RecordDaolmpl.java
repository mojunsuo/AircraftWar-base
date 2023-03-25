package edu.hitsz.shuju;

import java.io.*;
import java.util.*;

/**
 * @author liangshuang
 * getAll方法，返回所有的Record对象
 * addRecord方法，向Record链表中添加新的对象
 * sortbyScore()按分数排序
 * writeToFile()将所有数据写入文件中
 */
public class RecordDaolmpl implements RecordDao{
    private  List<Record>Records;
    private String pathName;
    public void setPathName(int level)
    {
        if(level==1)
        {
            pathName="src\\edu\\hitsz\\shuju\\a.txt";
        }
        if(level==2)
        {
            pathName="src\\edu\\hitsz\\shuju\\b.txt";
        }
        if(level==3)
        {
            pathName="src\\edu\\hitsz\\shuju\\c.txt";
        }
    }
    @Override
    public void getnow() throws IOException {
        Records=new ArrayList<Record>();
        File f=new File(pathName);
        BufferedReader in = new BufferedReader(new FileReader(f));
        int i=0;
        String str;
        String a = null;
        String b = null;
        while ((str = in.readLine()) != null)
        {
            /*if("".equals(str))
            {
                continue;
            }*/
            if(i%3==0)
            {
                a=str;
            }
            else if(i%3==1)
            {
                b=str;
            }
            else{
                Record r=new Record(a,b,str);
                Records.add(r);
            }
            i++;
        }
        in.close();
    }

    @Override
    public void addRecord(String id,String score,String time){
        Record r=new Record(id, score, time);
        Records.add(r);
    }
    @Override
    public void sortbyScore() throws IOException {

        Records.sort(new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return Integer.parseInt(o2.getScore()) - Integer.parseInt(o1.getScore());
            }
        });
        /*int i=1;
        for(Record r:Records)
        {
            System.out.println("第"+i+"名: "+r.getId()+","+r.getScore()+","+r.getTime());
            i++;
        }*/
    }
    @Override
    public void deletebyrow(int row)
    {
        Records.remove(row);
    }
    @Override
    public List<Record> getAll() throws IOException {
        return Records;
    }
    @Override
    public void writeToFile(List<Record> records) throws IOException {
        File f = new File(pathName);
        FileWriter fw = null;
        try {
            fw = new FileWriter(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Record r:records)
        {
            String i=r.getId();
            String s=r.getScore();
            String t=r.getTime();
            fw.write(i);
            fw.write('\n');
            fw.write(s);
            fw.write('\n');
            fw.write(t);
            fw.write('\n');
        }
        fw.close();
    }
}
