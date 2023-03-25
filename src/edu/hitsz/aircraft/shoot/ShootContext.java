package edu.hitsz.aircraft.shoot;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class ShootContext {
    private ShootStrategy sst;
    public ShootContext(ShootStrategy shootstrategy)
    {
        this.sst=shootstrategy;
    }
    public void setStrategy(ShootStrategy shootstrategy)
    {
        this.sst=shootstrategy;
    }
    public List<BaseBullet> executeStrategy(AbstractAircraft aircraft, int method)
    {
        return sst.Doshoot(aircraft,method);
    }
}
