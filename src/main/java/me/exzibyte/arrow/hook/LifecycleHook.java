package me.exzibyte.arrow.hook;

import me.exzibyte.Quiver;
import org.jetbrains.annotations.NotNull;

public class LifecycleHook {

    public static class Load extends LifecycleHook {

        private final Quiver quiver;

        public Load(Quiver quiver) {
            this.quiver = quiver;
        }

        @NotNull
        public Quiver getQuiver() {
            return quiver;
        }
    }

    public static class Unload extends LifecycleHook {

        private final Quiver quiver;

        public Unload(Quiver quiver) {
            this.quiver = quiver;
        }

        @NotNull
        public Quiver getQuiver() {
            return quiver;
        }
    }
}
