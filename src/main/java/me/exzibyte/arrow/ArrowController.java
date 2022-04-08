package me.exzibyte.arrow;

import me.exzibyte.Quiver;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.jar.JarFile;

public class ArrowController {

    private static final File ARROWS_DIRECTORY = new File("arrows");
    private final Quiver quiver;

    public ArrowController(Quiver quiver) {
        this.quiver = quiver;
    }

    public void loadArrows() {
        if (!ARROWS_DIRECTORY.exists()) {
            return;
        }

        for (var file : ARROWS_DIRECTORY.listFiles()) {
            try {
                var jarFile = new JarFile(file);
                var arrow = loadArrowFromJar(file.toPath(), jarFile);

                if (arrow == null) {
                    continue;
                }

                arrow.enable(quiver);
            } catch (IOException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    private LoadedArrow loadArrowFromJar(Path path, JarFile jarFile) throws IOException {
        var classLoader = new ArrowClassLoader(path, jarFile);
        var bootstrap = classLoader.findBootstrapClass();

        if (bootstrap == null) {
            System.out.println("Could not find main class for arrow " + jarFile.getName() + ", skipping.");
            jarFile.close();
            return null;
        }

        var arrow = bootstrap.getDeclaredAnnotation(Arrow.class);
        return new LoadedArrow(Objects.requireNonNull(arrow), classLoader, bootstrap);
    }
}
