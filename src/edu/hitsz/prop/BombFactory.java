package edu.hitsz.prop;
/**
 * @author liangshuang
 */
public class BombFactory implements PropFactory{
    @Override
    public Abstractprop createprop(int locax,int locay){
        return new Bomb(locax,locay,0,10);
    }
}
