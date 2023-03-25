package edu.hitsz.aircraft.Factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
/**
 * @author liangshuang
 */
public class BossEnemyFactory implements EnemyFactory {
    public int baseSpeedX=5;
    public int baseSpeedY=0;
    public int baseHp=400;
    @Override
    public AbstractAircraft createenemy(double beishu){


        return new BossEnemy(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1,
                (int)(Main.WINDOW_HEIGHT*0.1)*1,
                baseSpeedX*beishu,
                0,
                baseHp*beishu);
    }
}
