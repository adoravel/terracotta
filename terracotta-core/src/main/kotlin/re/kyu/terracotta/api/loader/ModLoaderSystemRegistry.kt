package re.kyu.terracotta.api.loader

import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters

/**
 * Build-scoped registry of mod loader systems.
 *
 * This is a [BuildService] so it can be shared across all subprojects
 * within a build without requiring project-level coordination
 */
public abstract class ModLoaderSystemRegistry : BuildService<BuildServiceParameters.None> {
    private val _loaders: MutableMap<String, ModLoaderSystem> =
        ModLoaderSystem.builtIn.associateBy { it.id }.toMutableMap()

    public val loaders: Map<String, ModLoaderSystem>
        get() = _loaders

    public fun register(loader: ModLoaderSystem) {
        check(!loaders.containsKey(loader.id)) {
            "A loader with id `${loader.id}` is already registered."
        }
        _loaders[loader.id] = loader
    }

    public fun register(
        id: String,
        displayName: String,
        capabilities: Set<LoaderCapability>,
    ): ModLoaderSystem = ModLoaderSystem(id, displayName, capabilities).also(::register)

    public operator fun get(id: String): ModLoaderSystem = loaders[id]
        ?: throw IllegalArgumentException(
            "Unknown loader '$id'. Registered loaders: ${loaders.keys.sorted()}"
        )

    public fun getOrNull(id: String): ModLoaderSystem? = loaders[id]

    public fun all(): List<ModLoaderSystem> = loaders.values.toList()
}
