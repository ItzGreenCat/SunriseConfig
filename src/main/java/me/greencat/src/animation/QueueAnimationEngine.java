package me.greencat.src.animation;

import java.util.LinkedList;
import java.util.Queue;

public class QueueAnimationEngine extends AnimationEngine{
    public Queue<PositionWrapper> animationQueue = new LinkedList<>();
    public QueueAnimationEngine(){
        super();
    }
    public QueueAnimationEngine(int x,int y){
        super(x,y);
    }
    public boolean isWorking(){
        return AnimationManager.animations.containsKey(this);
    }
    public void QueueMoveTo(int x,int y,double second,int type){
        if(!isWorking()){
            super.moveTo(x,y,second,type);
        } else {
            animationQueue.offer(new PositionWrapper(x,y,second,type));
        }
    }
    @Override
    public void callback(){
        if(!isWorking() && !animationQueue.isEmpty()){
            PositionWrapper positionWrapper = animationQueue.poll();
            super.moveTo(positionWrapper.x,positionWrapper.y,positionWrapper.speed,positionWrapper.type);
        }
    }
    static class PositionWrapper{
        public int x;
        public int y;
        public double speed;
        public int type;
        public PositionWrapper(int x,int y,double speed,int type){
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.type = type;
        }
    }
}
