package edu.hitsz.shuju;
/**
 * @author liangshuang
 * 每一条记录的对象，存储当前玩家的姓名，分数和当时的时间
 */
public class Record {
    private String time;
    private String score;
    private String id;
    Record(String id,String score,String time)
    {
        this.id=id;
        this.score=score;
        this.time=time;
    }

    public String getScore(){
        return score;
    }

    public String getTime(){
        return time;
    }

    public String getId(){return id;}

    public void setTime(String time){
        this.time=time;
    }

    public void setId(String id)
    {
        this.id=id;
    }

    public void setScore(String score){
        this.score=score;
    }
}
