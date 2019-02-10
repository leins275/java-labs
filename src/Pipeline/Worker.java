package Pipeline;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.EnumMap;

import Algoritms.Xor;
import Source.Grammar;
import Source.keys;

public class Worker implements Executor {

    Executor consumer;
    DataInputStream input;
    DataOutputStream output;

    Xor alg;

    boolean isFirst, isLast;
    byte[] buffer;

    public Worker(String params, boolean first, boolean last) throws IOException {
        input = null;
        output = null;
        consumer = null;
        isFirst = first;
        isLast = last;

        Grammar g = new Grammar(params);
        EnumMap<keys, String> m = g.parser();

        alg = new Xor(m);
    }

    public int setInput(DataInputStream stream) {
        if (isFirst)
            input = stream;
        return 0;
    }

    public int setOutput(DataOutputStream stream) {

        if (isLast)
            output = stream;
        return 0;
    }

    public int setConsumer(Executor next) {
        consumer = next;
        return 0;
    }

    public int put(Object obj) throws IOException{
        buffer = alg.selectTask(obj.getClass());

        if (isLast) {
            output.write(res);
        } else {
            consumer.put(res);
        }

        return 0;
    }

    public int run() {

        return 0;
    }
}
