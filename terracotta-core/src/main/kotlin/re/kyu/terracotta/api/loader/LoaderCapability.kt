package re.kyu.terracotta.api.loader

/**
 * Feature flags a loader may or may not support.
 *
 * Used to guard feature configuration and emit clear errors
 * rather than silently misconfiguring a build.
 */
public enum class LoaderCapability {
    /** Can consume Mojang official mappings (≥1.17) */
    MOJMAP,

    /** Can layer ParchmentMC parameter names on top of Mojmap */
    PARCHMENT,

    /** Can use Yarn-family mappings (e.g. Yarn, Feather, BINY) */
    YARN,

    /** Has a Mixin subsystem available at runtime */
    MIXIN,

    /** Has an access-widener system (Fabric) */
    ACCESS_WIDENER,

    /** Has an access-transformer system (Forge/NeoForge) */
    ACCESS_TRANSFORMER,

    /** Produces a remapped jar (Loom-based loaders) */
    REMAPPED_JAR,
}
