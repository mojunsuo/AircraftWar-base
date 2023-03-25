package edu.hitsz.prop;

/**
 * @author liangshuang
 */
public class BloodFactory implements PropFactory{
    @Override
    public Abstractprop createprop(int locax,int locay){
        return new Blood(locax,locay,0,10);
    }
}
