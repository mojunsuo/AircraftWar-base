package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**精英飞机
 * @author liangshuang
 */
public class ElitEnemy extends AbstractAircraft{
    /** 攻击方式 */
    private int shootNum = 1;
    private int power = 30;
    private int direction = 1;

    public ElitEnemy(int locationX, int locationY, double speedX, double speedY, double hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }


}
