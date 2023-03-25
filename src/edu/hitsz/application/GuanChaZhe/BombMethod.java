package edu.hitsz.application.GuanChaZhe;

import java.util.ArrayList;
import java.util.List;

public class BombMethod {
    private List<FlyObject> FlyObjectList=new ArrayList<>();
    public void addFlyObject(FlyObject flyObject){
        FlyObjectList.add(flyObject);
    }
    public void Notify(){
        for(FlyObject flyObject:FlyObjectList)
        {
            flyObject.clearAll();
        }
    }
}
