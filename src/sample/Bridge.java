package sample;

import java.util.ArrayList;
import java.util.Iterator;

public class Bridge {
    private ArrayList<BridgeSupport> supports;

    Bridge(){
        //constuctor to be implemented
    }

    public void draw(){
        Iterator<BridgeSupport> supportIterator;
        for(supportIterator = supports.iterator();supportIterator.hasNext();){
            supportIterator.next().draw();
        }
    }

    public void addSupport(BridgeSupport inSupport){
        supports.add(inSupport);
    }

    public ArrayList<BridgeSupport> getSupports() {
        return supports;
    }

}
