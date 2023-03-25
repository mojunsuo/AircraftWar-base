package edu.hitsz.shuju;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author liangshuang
 * getAll方法，返回所有的Record对象
 * addRecord方法，向Record链表中添加新的对象
 * sortbyScore()按分数排序
 * writeToFile()将所有数据写入文件中
 */
public interface RecordDao {
    void getnow() throws IOException;
    List<Record> getAll() throws IOException;
    void addRecord(String id,String score,String time);
    void sortbyScore() throws IOException;
    void writeToFile(List<Record> Records) throws IOException;
    void deletebyrow(int row);
}
