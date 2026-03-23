package re.kyu.terracotta.api.mappings

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import re.kyu.terracotta.api.loader.LoaderCapability
import re.kyu.terracotta.api.repository.RepositoryContributor
import re.kyu.terracotta.api.version.MinecraftVersion

/**
 * Minecraft mapping source.
 *
 * [C] is the toolchain context the provider needs to resolve its dependency (e.g.
 * [LoomGradleExtensionAPI] for Loom-based providers)
 */
public interface MappingsProvider<C> {
    /**
     * The loader capability this mappings provider requires.
     */
    public val requiredCapability: LoaderCapability

    /**
     * Repositories this set of mappings requires.
     */
    public val repositories: List<RepositoryContributor>
        get() = emptyList()

    /**
     * Resolves the Gradle dependency to pass to the `mappings` configuration.
     */
    public fun Project.resolveDependency(
        context: C,
        version: MinecraftVersion
    ): Dependency
}
