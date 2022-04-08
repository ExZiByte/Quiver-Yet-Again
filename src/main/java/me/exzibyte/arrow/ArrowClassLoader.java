package me.exzibyte.arrow;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.jar.JarFile;

public class ArrowClassLoader extends URLClassLoader {

    private final JarFile jarFile;

    public ArrowClassLoader(Path path, JarFile jarFile) throws MalformedURLException {
        super(new URL[]{path.toUri().toURL()});
        this.jarFile = jarFile;
    }

    @Nullable
    public Class<?> findBootstrapClass() {
        for (var clazz : loadAvailableClassFiles()) {
            var arrow = clazz.getDeclaredAnnotation(Arrow.class);

            if (arrow == null) {
                continue;
            }

            return clazz;
        }

        return null;
    }

    @NotNull
    private Collection<Class<?>> loadAvailableClassFiles() {
        var classes = new HashSet<Class<?>>();
        var entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            var name = entries.nextElement().getName();

            if (!name.endsWith(".class")) {
                continue;
            }

            name = name.replaceAll("/", "\\.").substring(0, name.length() - 6);

            try {
                classes.add(loadClass(name));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return classes;
    }
}
