package me.exzibyte.arrow;

import me.exzibyte.Quiver;
import me.exzibyte.arrow.hook.Hook;
import me.exzibyte.arrow.hook.LifecycleHook;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LoadedArrow {

    private final Arrow arrow;
    private final ArrowClassLoader loader;
    private final Class<?> boostrap;
    private final Map<String, Method> hooks; // TODO store in a better way
    private Object instance;

    public LoadedArrow(Arrow arrow, ArrowClassLoader loader, Class<?> boostrap) {
        this.arrow = arrow;
        this.loader = loader;
        this.boostrap = boostrap;
        this.hooks = findHooks();
    }

    public void enable(Quiver quiver) throws InstantiationException, IllegalAccessException {
        instance = boostrap.newInstance();
        triggerHook(new LifecycleHook.Load(quiver));
        System.out.printf("Enabled arrow %s version %s.%n", arrow.id(), arrow.version());
    }

    private void triggerHook(LifecycleHook hook) {
        var method = hooks.get(hook.getClass().getSimpleName());

        if (method == null) {
            return;
        }

        try {
            method.invoke(instance, hook);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private Map<String, Method> findHooks() {
        return Arrays.stream(boostrap.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Hook.class))
                .filter(method -> method.getParameterCount() != 0)
                .collect(Collectors.toMap(method -> {
                    return method.getParameterTypes()[0].getSimpleName();
                }, Function.identity()));
    }
}
