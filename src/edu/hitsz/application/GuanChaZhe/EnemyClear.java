package edu.hitsz.application.GuanChaZhe;

import edu.hitsz.aircraft.AbstractAircraft;

import static edu.hitsz.application.Game.enemyAircrafts;

import static edu.hitsz.application.Game.score;

import static edu.hitsz.application.Game.score2;

public class EnemyClear implements FlyObject {
    @Override
    public void clearAll(){
        for(AbstractAircraft enemy:enemyAircrafts)
        {
            if(!"edu.hitsz.aircraft.BossEnemy".equals(enemy.getClass().getName()))
            {
                enemy.vanish();
                score+=20;
                score2+=20;
            }

        }
    }
}
