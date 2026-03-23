package re.kyu.terracotta.loom.mappings

import net.fabricmc.loom.api.LoomGradleExtensionAPI
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import re.kyu.terracotta.api.loader.LoaderCapability
import re.kyu.terracotta.api.mappings.MappingsProvider
import re.kyu.terracotta.api.version.MinecraftVersion

public object MojmapProvider : MappingsProvider<LoomGradleExtensionAPI> {
    override val requiredCapability: LoaderCapability = LoaderCapability.MOJMAP

    override fun Project.resolveDependency(context: LoomGradleExtensionAPI, version: MinecraftVersion): Dependency {
        return context.officialMojangMappings()
    }
}
