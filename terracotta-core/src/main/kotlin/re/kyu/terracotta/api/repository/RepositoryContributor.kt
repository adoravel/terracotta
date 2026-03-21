package re.kyu.terracotta.api.repository

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

/**
 * A self-contained repository declaration.
 */
public data class RepositoryContributor(
    val url: String,
    /** Maven groups served exclusively by [url] */
    val groups: Set<String> = emptySet()
)

public fun RepositoryContributor.applyTo(handler: RepositoryHandler) {
    val alreadyAdded = handler.filterIsInstance<MavenArtifactRepository>()
        .any { it.url.toString().trimEnd('/') == url.trimEnd('/') }

    if (alreadyAdded) return

    handler.exclusiveContent {
        it.forRepository {
            handler.maven { m ->
                m.setUrl(url)
            }
        }
        it.filter { g ->
            groups.forEach { group -> g.includeGroup(group) }
        }
    }
}
