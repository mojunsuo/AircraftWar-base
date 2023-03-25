package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.*;

import java.util.List;
import java.util.Random;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected double maxHp;
    protected double hp;

    public AbstractAircraft(int locationX, int locationY, double speedX, double speedY, double hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
        else if(hp>maxHp)
        {
            hp=maxHp;
        }
    }

    public double getHp() {
        return hp;
    }


    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    //public abstract List<BaseBullet> shoot();

    public Abstractprop getprop(AbstractAircraft a) {
        Random r=new Random();
        int k=r.nextInt()%10;
        PropFactory pr;
        Abstractprop ab = null;
        if(k!=0)
        {
            int t=r.nextInt()%3;
            if(t==0)
            {
                pr=new BloodFactory();
                ab=pr.createprop(a.getLocationX(),a.getLocationY());
            }
            if(t==1)
            {
                pr=new BombFactory();
                ab=pr.createprop(a.getLocationX(),a.getLocationY());
            }
            if(t==2)
            {
                pr=new BulletFactory();
                ab=pr.createprop(a.getLocationX(),a.getLocationY());
            }
        }
        return ab;
    }
}


