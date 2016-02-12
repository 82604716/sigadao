package com.siganid.annotation;

/**
 * Created by Administrator on 2016/2/9.
 */
public class ProcessorManager {

    Processor processtor;

    public Processor getProcessor() {
        return processtor;
    }

    public void setProcessor(Processor processtor) {
        this.processtor = processtor;
    }


    static ProcessorManager processorManager;

    private ProcessorManager() {

    }

    public static ProcessorManager getInstance() {
        if (processorManager == null) {
            processorManager = new ProcessorManager();
        }
        return processorManager;

    }


}
