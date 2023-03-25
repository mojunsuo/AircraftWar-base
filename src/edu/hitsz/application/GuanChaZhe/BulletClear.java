package edu.hitsz.application.GuanChaZhe;

import edu.hitsz.bullet.BaseBullet;

import static edu.hitsz.application.Game.enemyBullets;

public class BulletClear implements FlyObject {
    @Override
    public void clearAll()
    {
        for(BaseBullet bullet:enemyBullets)
        {
            bullet.vanish();
        }
    }
}
