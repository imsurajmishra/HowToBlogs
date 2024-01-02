package com.asyncq.runtime;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.reflections.Reflections;

/**
 * java code that reads runtime argument from user,
 * use reflection and choose the appropriate class based on passed argument
 * and initialize logic with it.
 */
public class RunPipelineClient {

    private static Map<String, String> argsLookup = new HashMap<>();

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        initArgs(args);
        String options = argsLookup.get("options");
        Class<? extends Options> optionsClass = mapToClass(options).get();
        new Pipeline()
            .with(optionsClass)
            .execute();
    }

    private static Optional<Class<? extends Options>> mapToClass(String optionsClassName) throws ClassNotFoundException {
        Set<Class<? extends Options>> interfaceImplementations = findInterfaceImplementations(Options.class);
        Optional<Class<? extends Options>> optionsClass = interfaceImplementations.stream()
            .filter(i -> i.getName()
                .contains(optionsClassName))
            .findAny();
        if(optionsClass.isEmpty()) throw new IllegalArgumentException("could not find options, please make sure the name is correct and there is an implementation for it.");
        return optionsClass;
    }

    private static void initArgs(String[] args) {
        Arrays.stream(args).forEach(a -> {
            String[] keyVal = a.split("=");
            argsLookup.put(keyVal[0], keyVal[1]);
        });
    }

    public static Set<Class<? extends Options>> findInterfaceImplementations(Class<Options> optionsClass) {
        Reflections reflections = new Reflections(RunPipelineClient.class.getPackageName());
        return reflections.getSubTypesOf(Options.class);
    }
}
