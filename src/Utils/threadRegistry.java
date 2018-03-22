package Utils;

import MessageStubs.MessageStub;
import javafx.util.Pair;

import java.util.concurrent.ConcurrentHashMap;

public class threadRegistry {

    private ConcurrentHashMap<Pair<String,Integer>,MessageStub>threadRegistry;

    /**
     *
     */
    public threadRegistry() {
        this.threadRegistry = new ConcurrentHashMap<>();
    }


}
