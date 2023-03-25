package edu.hitsz.aircraft.shoot;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterShoot implements ShootStrategy {
    private int shootNum = 3;
    private int power = 30;
    private int direction;

    @Override
    public List<BaseBullet> Doshoot(AbstractAircraft fly, int method) {
        List<BaseBullet> res=new LinkedList<>();
        if(("edu.hitsz.aircraft.HeroAircraft").equals(fly.getClass().getName()))
        {
            direction=-1;
            int x = fly.getLocationX();
            int y = fly.getLocationY() + direction*2;
            double speedX = -2;
            double speedY = fly.getSpeedY() + direction*5;
            BaseBullet baseBullet;
            for(int i=0; i<shootNum; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power+10*(method-1));
                res.add(baseBullet);
                speedX+=2;
            }
        }
        else{
            direction=1;
            int x = fly.getLocationX();
            int y = fly.getLocationY() + direction*2;
            double speedX = -2;
            if(method==2)
            {
                speedX=-3;
            }
            double speedY = fly.getSpeedY() + direction*5;
            BaseBullet baseBullet;
            for(int i=0; i<method*shootNum; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
                res.add(baseBullet);
                if(method==2)
                {
                    speedX+=0.5;
                }
                else if(method==1){
                    speedX+=2;
                }
                else{
                    speedX+=1;
                }
            }
        }
        return res;
    }
}
