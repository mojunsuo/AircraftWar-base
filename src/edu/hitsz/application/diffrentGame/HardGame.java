package edu.hitsz.application.diffrentGame;

import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.Factory.BossEnemyFactory;
import edu.hitsz.aircraft.Factory.EliteEnemyFactory;
import edu.hitsz.aircraft.Factory.EnemyFactory;
import edu.hitsz.aircraft.Factory.MobEnemyFactory;
import edu.hitsz.application.Game;
import edu.hitsz.application.MusicThread;

import java.util.Random;

public class HardGame extends Game {
    /**
     * Boss机的产生阈值
     * Boss机同时存在的最大数量
     *产生精英敌机的概率
     * 敌机的各种属性
     * cycleDuration,即循环周期的倍率.
     */
    public int yuzhi=300;
    public int MaxBossNum=2;
    public double prob=0.5;
    public double shuXing=1.0;
    public double beiLv=1.0;
    public double first=cycleDuration;
    public void reset(){
        prob+=0.02;
        shuXing+=0.02;
        beiLv-=0.02;
        System.out.println("敌机属性已调整至"+String.format("%.2f",shuXing)+"倍"+'\n'+"精英敌机出现概率已调整至"+String.format("%.2f",prob/2.0)+"倍"+'\n'+"刷新周期已调整至原来的"+String.format("%.2f",beiLv)+"倍");
    }

    @Override
    public void createNewFly(){
        if(time2>4000)
        {
            time2=0;
            reset();
        }
        Random po=new Random();
        double r=po.nextDouble()%(2.0);
        EnemyFactory e;
        AbstractAircraft es;

        if(score2>=yuzhi)
        {
            int flag=0;
            for(AbstractAircraft emo:enemyAircrafts)
            {
                if("edu.hitsz.aircraft.BossEnemy".equals(emo.getClass().getName()))
                {
                    flag++;
                }
            }
            if(flag==0)
            {
                op=new MusicThread("src\\bgm\\videos\\bgm_boss.wav");
                if(MusicFlag==1)
                {
                    op.setflag(1);
                    op.start();
                }
            }
            if(flag<MaxBossNum)
            {
                e=new BossEnemyFactory();
                es =e.createenemy(shuXing);
                enemyAircrafts.add(es);
            }
            score2-=yuzhi;
        }
        if(r<prob)
        {
            e=new EliteEnemyFactory();
            es =e.createenemy(shuXing);
            enemyAircrafts.add(es);
        }
        else if(r>prob)
        {
            e=new MobEnemyFactory();
            es =e.createenemy(shuXing);
            enemyAircrafts.add(es);
        }
    }
    @Override
    public void setCycleDuration(){
        this.cycleDuration=first*beiLv;
    }

}
