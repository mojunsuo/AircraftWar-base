package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.Factory.BossEnemyFactory;
import edu.hitsz.aircraft.Factory.EliteEnemyFactory;
import edu.hitsz.aircraft.Factory.EnemyFactory;
import edu.hitsz.aircraft.Factory.MobEnemyFactory;
import edu.hitsz.aircraft.shoot.ScatterShoot;
import edu.hitsz.aircraft.shoot.ShootContext;
import edu.hitsz.aircraft.shoot.StraightShoot;
import edu.hitsz.application.GuanChaZhe.BombMethod;
import edu.hitsz.application.GuanChaZhe.BulletClear;
import edu.hitsz.application.GuanChaZhe.EnemyClear;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import static edu.hitsz.application.Main.obj2;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {

    protected int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    protected final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    protected int timeInterval = 40;
    public int MusicFlag;
    protected final HeroAircraft heroAircraft;
    public static List<AbstractAircraft> enemyAircrafts;

    protected final List<BaseBullet> heroBullets;
    public static List<BaseBullet> enemyBullets;
    protected final List<Abstractprop> manyProps;

    protected int enemyMaxNumber = 5;

    protected boolean gameOverFlag = false;
    public  static int score = 0;
    public static int score2= 0;
    protected int time = 0;
    protected int time2= 0;
    private int allProp=0;
    BombMethod bm;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected double cycleDuration = 600;
    private double cycleTime = 0;
    public int enemyZhuiLuo=0;

    public Game() {

        heroAircraft = HeroAircraft.getHeroAircraft();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        manyProps = new LinkedList<>();
        bm=new BombMethod();
        BulletClear bc=new BulletClear();
        EnemyClear ec=new EnemyClear();
        bm.addFlyObject(bc);
        bm.addFlyObject(ec);
        //Scheduled 线程池，用于定时任务调度
        executorService = new ScheduledThreadPoolExecutor(1);

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }
    public static MusicThread op;
    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void setMusicFlag(int m){
        this.MusicFlag=m;
    }
    public void action() {

        MusicThread ms=new MusicThread("src\\bgm\\videos\\bgm.wav");
        if(MusicFlag==1)
        {
            ms.setflag(1);
            ms.start();
        }
        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {
            time += timeInterval;
            time2+= timeInterval;
            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    createNewFly();
                }
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                if(MusicFlag==1)
                {
                    ms.setflag(0);
                    for(AbstractAircraft a:enemyAircrafts)
                    {
                        if("edu.hitsz.aircraft.BossEnemy".equals(a.getClass().getName()))
                        {
                            op.setflag(0);
                            break;
                        }
                    }
                }
                MusicThread some=new MusicThread("src\\bgm\\videos\\game_over.wav");
                if(MusicFlag==1)
                {
                    some.setflag(2);
                    some.start();
                }
                executorService.shutdown();
                gameOverFlag = true;
                synchronized (obj2)
                {
                    obj2.notify();
                }
            }
        };


        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    /**
     * 产生新飞机，以及设置循环周期，用于不同难度的实现
     */
    public void createNewFly(){}
    public void setCycleDuration(){

    }

    protected boolean timeCountAndNewCycleJudge() {
        setCycleDuration();
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 用于控制子弹道具
     */
    private int f=0;
    private int ps=0;

    void shootAction() {
        // TODO 敌机射击

        ShootContext SC=new ShootContext(new StraightShoot());
        for(AbstractAircraft a:enemyAircrafts)
        {
            if(!"edu.hitsz.aircraft.BossEnemy".equals(a.getClass().getName()))
            {
                SC.setStrategy(new StraightShoot());
                enemyBullets.addAll(SC.executeStrategy(a,0));
            }
            else{
                SC.setStrategy(new ScatterShoot());
                int now=0;
                if(GraphicsFlag==2)
                {
                    now=1;
                }
                else if(GraphicsFlag==3)
                {
                    now=2;
                    if(time>=50000)
                    {
                        now=3;
                    }
                }
                enemyBullets.addAll(SC.executeStrategy(a,now));
            }
        }

        // 英雄射击
        int now=1;
        if(GraphicsFlag==3)
        {
            if(allProp>=15)
            {
                now=2;
            }
        }
        else if(GraphicsFlag==2)
        {
            if(allProp>=15&&allProp<=25)
            {
                now=2;
            }
            else if(allProp>25)
            {
                now=3;
            }
        }
        if(f==0)
        {
            SC.setStrategy(new StraightShoot());
            heroBullets.addAll(SC.executeStrategy(heroAircraft,now));
        }
        else{
            ps++;
            SC.setStrategy(new ScatterShoot());
            heroBullets.addAll(SC.executeStrategy(heroAircraft,now));
            if(ps==10)
            {
                ps=0;
                f=0;
            }
        }
    }

    void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
        for(Abstractprop pro:manyProps)
        {
            pro.forward();
        }
    }

    void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for(BaseBullet bullet :enemyBullets)
        {
            if(bullet.notValid())
            {
                continue;
            }
            if(heroAircraft.crash(bullet))
            {
                bullet.vanish();
                heroAircraft.decreaseHp(bullet.getPower());
            }
        }//
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定

                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    MusicThread ppgod=new MusicThread("src\\bgm\\videos\\bullet_hit.wav");
                    if(MusicFlag==1)
                    {
                        ppgod.setflag(2);
                        ppgod.start();
                    }


                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        enemyZhuiLuo++;
                        if(enemyZhuiLuo%30==0)
                        {
                            if(GraphicsFlag==3)
                            {
                                if(enemyZhuiLuo==30)
                                {
                                    System.out.println("隐藏成就已经触发,获得隐藏成就:小试牛刀");
                                    System.out.println("生命值增加100");
                                    heroAircraft.decreaseHp(-100);
                                }
                                else if(enemyZhuiLuo==60)
                                {
                                    System.out.println("隐藏成就已经触发,获得隐藏成就:中规中矩");
                                    System.out.println("生命值增加150");
                                    heroAircraft.decreaseHp(-150);
                                }
                                else{
                                    System.out.println("隐藏成就已经触发,获得隐藏成就:无人可挡");
                                    System.out.println("生命值增加200");
                                    heroAircraft.decreaseHp(-200);
                                }
                            }

                        }
                        // TODO 获得分数，产生道具补给
                        if(("edu.hitsz.aircraft.ElitEnemy").equals(enemyAircraft.getClass().getName()))
                        {
                            Abstractprop p=enemyAircraft.getprop(enemyAircraft);
                            if(p!=null)
                            {
                                allProp+=1;
                                manyProps.add(p);
                            }
                        }
                        else if(("edu.hitsz.aircraft.BossEnemy").equals(enemyAircraft.getClass().getName()))
                        {
                            int flag=0;
                            for(AbstractAircraft a:enemyAircrafts)
                            {
                                if(("edu.hitsz.aircraft.BossEnemy").equals(a.getClass().getName()))
                                {
                                    flag++;
                                }
                            }
                            if(flag==1)
                            {
                                op.setflag(0);
                            }
                        }
                        score += 20;
                        score2+=20;
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for(Abstractprop pro:manyProps)
        {
            if(pro.notValid())
            {
                continue;
            }
            if(pro.crash(heroAircraft)||heroAircraft.crash(pro))
            {
                if(("edu.hitsz.prop.Blood").equals(pro.getClass().getName()))
                {
                    MusicThread ppgod=new MusicThread("src\\bgm\\videos\\get_supply.wav");
                    if(MusicFlag==1)
                    {
                        ppgod.setflag(2);
                        ppgod.start();
                    }
                    heroAircraft.decreaseHp(-30);
                }
                if(("edu.hitsz.prop.Bomb").equals(pro.getClass().getName()))
                {
                    MusicThread ppgod=new MusicThread("src\\bgm\\videos\\bomb_explosion.wav");
                    if(MusicFlag==1)
                    {
                        ppgod.setflag(2);
                        ppgod.start();
                    }
                    bm.Notify();
                }
                if(("edu.hitsz.prop.Bullet").equals(pro.getClass().getName()))
                {
                    MusicThread ppgod=new MusicThread("src\\bgm\\videos\\bullet.wav");
                    if(MusicFlag==1)
                    {
                        ppgod.setflag(2);
                        ppgod.start();
                    }
                    f=1;
                    ps=0;
                    System.out.println("Bullet Execute");
                }
                pro.vanish();
            }
        }
    }
    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        manyProps.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************
    private int GraphicsFlag;
    public void setGraphicsFlag(int f)
    {
        this.GraphicsFlag=f;
    }
    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(GraphicsFlag==1)
        {
            g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
            g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        }
        // 绘制背景,图片滚动
        if(GraphicsFlag==2)
        {
            g.drawImage(ImageManager.BG2_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
            g.drawImage(ImageManager.BG2_IMAGE, 0, this.backGroundTop, null);
        }
        if(GraphicsFlag==3)
        {
            g.drawImage(ImageManager.BG3_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
            g.drawImage(ImageManager.BG3_IMAGE, 0, this.backGroundTop, null);
        }
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, manyProps);
        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
