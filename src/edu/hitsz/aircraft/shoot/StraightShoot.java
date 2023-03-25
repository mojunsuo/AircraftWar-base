package edu.hitsz.aircraft.shoot;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.shoot.ShootStrategy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class StraightShoot implements ShootStrategy {
    private int shootNum = 1;
    private int power = 30;
    private int direction;

    @Override
    public List<BaseBullet> Doshoot(AbstractAircraft fly, int method){
        List<BaseBullet> res=new LinkedList<>();
        if(("edu.hitsz.aircraft.ElitEnemy").equals(fly.getClass().getName()))
        {
            direction=1;
            int x = fly.getLocationX();
            int y = fly.getLocationY() + direction*2;
            double speedX = 0;
            double speedY = fly.getSpeedY() + direction*5;
            BaseBullet baseBullet;
            for(int i=0; i<shootNum; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
                res.add(baseBullet);
            }
        }
        else if(("edu.hitsz.aircraft.HeroAircraft").equals(fly.getClass().getName()))
        {
            direction=-1;
            int x = fly.getLocationX();
            int y = fly.getLocationY() + direction*2;
            double speedX = 0;
            double speedY = fly.getSpeedY() + direction*5;
            BaseBullet baseBullet;
            for(int i=0; i<method; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power+10*(method-1));
                res.add(baseBullet);
            }
        }
        return res;
    }
}
