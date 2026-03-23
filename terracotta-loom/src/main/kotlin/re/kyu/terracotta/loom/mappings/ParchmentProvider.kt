package re.kyu.terracotta.loom.mappings

import net.fabricmc.loom.api.LoomGradleExtensionAPI
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import re.kyu.terracotta.api.loader.LoaderCapability
import re.kyu.terracotta.api.mappings.MappingsProvider
import re.kyu.terracotta.api.repository.RepositoryContributor
import re.kyu.terracotta.api.version.MinecraftVersion
import re.kyu.terracotta.loom.repository.KnownLoomRepositories

private val MAPPINGS_VERSION_TABLE = mapOf(
    "1.21.1" to "2025.01.15",
    "1.21" to "2024.12.20",
    "1.20.6" to "2024.08.30",
    "1.20.5" to "2024.07.03",
    "1.20.4" to "2023.12.21",
    "1.20.3" to "2023.11.28",
    "1.20.2" to "2023.09.21",
    "1.20.1" to "2023.07.19",
    "1.20" to "2023.06.20",
    "1.19.4" to "2023.03.30",
    "1.19.3" to "2022.12.12",
    "1.19.2" to "2022.09.20",
    "1.19.1" to "2022.08.15",
    "1.19" to "2022.06.28",
    "1.18.2" to "2022.05.18",
    "1.18.1" to "2022.02.08",
    "1.18" to "2021.11.30",
    "1.17.1" to "2021.10.19",
    "1.17" to "2021.07.13",
    "1.16.5" to "2021.01.15"
)

public object ParchmentProvider : MappingsProvider<LoomGradleExtensionAPI> {
    override val requiredCapability = LoaderCapability.PARCHMENT

    override val repositories: List<RepositoryContributor> = listOf(KnownLoomRepositories.Parchment)

    override fun Project.resolveDependency(context: LoomGradleExtensionAPI, version: MinecraftVersion): Dependency {
        val timestamp = requireNotNull(MAPPINGS_VERSION_TABLE[version.toString()]) {
            "Parchment mappings are not available for Minecraft version '${version}'."
        }
        return context.layered {
            it.officialMojangMappings()
            it.parchment("org.parchmentmc.data:parchment-${version}:${timestamp}@zip")
        }
    }
}
