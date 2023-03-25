package edu.hitsz.aircraft.Factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.ElitEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
/**
 * @author liangshuang
 */
public class EliteEnemyFactory implements EnemyFactory {
    public int baseSpeedX=5;
    public int baseSpeedY=10;
    public int baseHp=30;
    @Override
    public AbstractAircraft createenemy(double beishu){
        return new ElitEnemy(
                (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                baseSpeedX*beishu,
                baseSpeedY*beishu,
                baseHp*beishu);
    }
}
