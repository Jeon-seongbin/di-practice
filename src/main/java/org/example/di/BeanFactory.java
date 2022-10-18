package org.example.di;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BeanFactory {
    private final Set<Class<?>> preInstantiatedClazz;

    private Map<Class<?>, Object> beans = new HashMap<>();

    public BeanFactory(Set<Class<?>> preInstantiatedClazz) {
        this.preInstantiatedClazz = preInstantiatedClazz;
        initialize();
    }

    public <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }

    private void initialize() {
        for (Class<?> clazz : preInstantiatedClazz) {
            Object instance = createdInstance(clazz);
            beans.put(clazz, instance);
        }
    }

    private Object createdInstance(Class<?> clazz) {
        Constructor<?> constructor = findConstructor(clazz);

        List<Object> parameters = new ArrayList<>();
        for (Class<?> typeClass : constructor.getParameterTypes()) {
            parameters.add(getParameterByClass(typeClass));
        }
        try {
            return constructor.newInstance(parameters.toArray());

        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    private Constructor<?> findConstructor(Class<?> clazz) {
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);

        if (Objects.nonNull(injectedConstructor)) {
            return injectedConstructor;
        }
        return clazz.getConstructors()[0];
    }

    private Object getParameterByClass(Class<?> typeClass) {
        Object instance = getBean(typeClass);

        if (Objects.nonNull(instance)) {
            return instance;
        }
        return createdInstance(typeClass);
    }


}
