package io.binghe.rpc.spi.loader;

import io.binghe.rpc.spi.annotation.SPI;
import io.binghe.rpc.spi.annotation.SPIClass;
import io.binghe.rpc.spi.factory.ExtensionFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author You Chuande
 */
public class ExtensionLoader<T> {
    private static final String SERVICE_DIRECTORY = "META-INF/services/";
    public static final String BINGHE_DIRECTORY = "META-INF/binghe/";
    public static final String BINGHE_DIRECTORY_EXTERNAL = "META-INF/binghe/external/";
    public static final String BINGHE_DIRECTORY_INTERNAL = "META-INF/binghe/internal/";

    public static final String[] SPI_DIRECTORIES = new String[]{
            SERVICE_DIRECTORY,
            BINGHE_DIRECTORY,
            BINGHE_DIRECTORY_EXTERNAL, BINGHE_DIRECTORY_INTERNAL};

    private static final Map<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(ExtensionLoader.class);
    private final Class<T> clazz;
    private final ClassLoader classLoader;
    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();
    private final Map<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();
    private final Map<Class<?>, Object> spiClassInstances = new ConcurrentHashMap<>();
    private String cachedDefaultName;

    private ExtensionLoader(final Class<T> clazz, final ClassLoader cl) {
        this.clazz = clazz;
        this.classLoader = cl;
        if (!Objects.equals(clazz, ExtensionFactory.class)) {
            ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getExtensionClasses();
        }
    }

    public static <T> ExtensionLoader<T> getExtensionLoader(final Class<T> clazz, final ClassLoader cl) {

        Objects.requireNonNull(clazz, "extension clazz is null");

        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("extension clazz (" + clazz + ") is not interface!");
        }
        if (!clazz.isAnnotationPresent(SPI.class)) {
            throw new IllegalArgumentException("extension clazz (" + clazz + ") without @" + SPI.class + " Annotation");
        }
        ExtensionLoader<T> extensionLoader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(clazz);
        if (Objects.nonNull(extensionLoader)) {
            return extensionLoader;
        }
        EXTENSION_LOADERS.putIfAbsent(clazz, new ExtensionLoader<>(clazz, cl));
        return (ExtensionLoader<T>) EXTENSION_LOADERS.get(clazz);
    }


    public static <T> T getExtension(final Class<T> clazz, final String name) {
        return StringUtils.isEmpty(name) ? getExtensionLoader(clazz).getDefaultSpiClassInstance() :
                getExtensionLoader(clazz).getSpiClassInstance(name);
    }

    public static <T> ExtensionLoader<T> getExtensionLoader(final Class<T> clazz) {
        return getExtensionLoader(clazz, ExtensionLoader.class.getClassLoader());
    }

    public T getDefaultSpiClassInstance() {
        getExtensionClasses();
        if (StringUtils.isBlank(cachedDefaultName)) {
            return null;
        }
        return getSpiClassInstance(cachedDefaultName);
    }

    public T getSpiClassInstance(final String name) {
        if (StringUtils.isBlank(name)) {
            throw new NullPointerException("get spi class name is null");
        }
        Holder<Object> holder = cachedInstances.get(name);
        if (Objects.isNull(holder)) {
            cachedInstances.putIfAbsent(name, new Holder<>());
            holder = cachedInstances.get(name);
        }
        Object value = holder.getValue();
        if (Objects.isNull(value)) {
            synchronized (holder) {
                value = holder.getValue();
                if (Objects.isNull(value)) {
                    value = createExtension(name);
                    holder.setValue(value);
                }
            }
        }
        return (T) value;
    }

    public List<T> getSpiClassInstance() {
        Map<String, Class<?>> extensionClasses = this.getExtensionClasses();
        if (extensionClasses.isEmpty()) {
            return Collections.emptyList();
        }
        if (Objects.equals(extensionClasses.size(), cachedInstances.size())) {
            return (List<T>) this.cachedInstances.values()
                    .stream().map(Holder::getValue).collect(Collectors.toList());
        }
        List<T> instances = new ArrayList<>();
        extensionClasses.forEach((name, v) -> {
            T instance = this.getSpiClassInstance(name);
            instances.add(instance);
        });
        return instances;
    }

    @SuppressWarnings("unchecked")
    private T createExtension(final String name) {
        Class<?> aClass = getExtensionClasses().get(name);
        if (Objects.isNull(aClass)) {
            throw new IllegalArgumentException("name is error");
        }
        Object o = spiClassInstances.get(aClass);
        if (Objects.isNull(o)) {
            try {
                spiClassInstances.putIfAbsent(aClass, aClass.newInstance());
                o = spiClassInstances.get(aClass);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException("Extension instance(name: " + name + ", class: " + aClass + ")  could not be instantiated: " + e.getMessage(), e);
            }
        }
        return (T) o;
    }

    public Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classes = cachedClasses.getValue();
        if (Objects.isNull(classes)) {
            synchronized (cachedClasses) {
                classes = cachedClasses.getValue();
                if (Objects.isNull(classes)) {
                    classes = loadExtensionClasses();
                    cachedClasses.setValue(classes);
                }
            }
        }
        return classes;
    }


    private Map<String, Class<?>> loadExtensionClasses() {
        SPI spi = clazz.getAnnotation(SPI.class);
        if (Objects.nonNull(spi)) {
            String value = spi.value();
            if (StringUtils.isNotBlank(value)) {
                cachedDefaultName = value;
            }
        }
        Map<String, Class<?>> classes = new HashMap<>();
        loadDirectory(classes);
        return classes;
    }

    private void loadDirectory(final Map<String, Class<?>> classes) {
        for (String directory : SPI_DIRECTORIES) {
            String fileName = directory + clazz.getName();
            try {
                Enumeration<URL> urls = Objects.nonNull(this.classLoader) ? classLoader.getResources(fileName)
                        : ClassLoader.getSystemResources(fileName);
                if (Objects.nonNull(urls)) {
                    while (urls.hasMoreElements()) {
                        URL url = urls.nextElement();
                        loadResources(classes, url);
                    }
                }
            } catch (IOException t) {
                log.error("load extension class error {}", fileName, t);
            }
        }
    }

    private void loadResources(final Map<String, Class<?>> classes, final URL url) throws IOException {
        try (InputStream inputStream = url.openStream()) {
            Properties properties = new Properties();
            properties.load(inputStream);
            properties.forEach((k, v) -> {
                String name = (String) k;
                String classPath = (String) v;
                if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(classPath)) {
                    try {
                        loadClass(classes, name, classPath);
                    } catch (ClassNotFoundException e) {
                        throw new IllegalStateException("load extension resources error", e);
                    }
                }
            });
        } catch (IOException e) {
            throw new IllegalStateException("load extension resources error", e);
        }
    }

    private void loadClass(final Map<String, Class<?>> classes,
                           final String name, final String classPath) throws ClassNotFoundException {
        Class<?> subClass = Objects.nonNull(this.classLoader) ? Class.forName(classPath, true, this.classLoader) : Class.forName(classPath);
        if (!clazz.isAssignableFrom(subClass)) {
            throw new IllegalStateException("load extension resources error," + subClass + " subtype is not of " + clazz);
        }
        if (!subClass.isAnnotationPresent(SPIClass.class)) {
            throw new IllegalStateException("load extension resources error," + subClass + " without @" + SPIClass.class + " annotation");
        }
        Class<?> oldClass = classes.get(name);
        if (Objects.isNull(oldClass)) {
            classes.put(name, subClass);
        } else if (!Objects.equals(oldClass, subClass)) {
            throw new IllegalStateException("load extension resources error,Duplicate class " + clazz.getName() + " name " + name + " on " + oldClass.getName() + " or " + subClass.getName());
        }
    }


    public static class Holder<T> {
        private volatile T value;

        public T getValue() {
            return value;
        }

        public Holder<T> setValue(T value) {
            this.value = value;
            return this;
        }
    }
}
