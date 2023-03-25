package edu.hitsz.prop;
/**
 * @author liangshuang
 */
public class BulletFactory implements PropFactory{
    @Override
    public Abstractprop createprop(int locax,int locay)
    {
        return new Bullet(locax,locay,0,10);
    }
}
