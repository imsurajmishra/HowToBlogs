package com.asyncq.runtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Pipeline {

    List<String> pipelineOptions = new ArrayList<>();

    public Pipeline with(Class<? extends Options> runtimeOptions) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Method[] declaredMethods = runtimeOptions.getDeclaredMethods();
        for(Method method: declaredMethods){
            Constructor<? extends Options> declaredConstructor = runtimeOptions.getDeclaredConstructor();
            Options Instance = declaredConstructor.newInstance();
            pipelineOptions.add((String)method.invoke(Instance));
        }
        return this;
    }

    public Pipeline execute(){
        System.out.println("executing with pipeline options %s"
            .formatted(pipelineOptions));
        return this;
    }
}
