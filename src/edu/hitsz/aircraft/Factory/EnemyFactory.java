package edu.hitsz.aircraft.Factory;

import edu.hitsz.aircraft.AbstractAircraft;

/**
 * @author liangshuang
 */
public interface EnemyFactory {
    public AbstractAircraft createenemy(double beishu);
}
