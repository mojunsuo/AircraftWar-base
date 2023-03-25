package edu.hitsz.application.diffrentGame;

import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.Factory.EliteEnemyFactory;
import edu.hitsz.aircraft.Factory.EnemyFactory;
import edu.hitsz.aircraft.Factory.MobEnemyFactory;
import edu.hitsz.application.Game;

import java.util.Random;

public class EasyGame extends Game {
    @Override
    public void createNewFly(){
        Random po=new Random();
        int r=po.nextInt()%2;
        EnemyFactory e;
        AbstractAircraft es;
        if(r==0)
        {
            e=new EliteEnemyFactory();
            es =e.createenemy(1);
            enemyAircrafts.add(es);
        }
        else if(r==1)
        {
            e=new MobEnemyFactory();
            es =e.createenemy(1);
            enemyAircrafts.add(es);
        }
    }

}
