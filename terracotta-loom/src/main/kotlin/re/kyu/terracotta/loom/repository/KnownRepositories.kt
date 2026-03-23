package re.kyu.terracotta.loom.repository

import re.kyu.terracotta.api.repository.RepositoryContributor

public object KnownLoomRepositories {
    public val Fabric: RepositoryContributor = RepositoryContributor(
        url = "https://maven.fabricmc.net/",
        groups = setOf("net.fabricmc", "net.fabricmc.fabric-api", "fabric-loom")
    )

    public val Parchment: RepositoryContributor = RepositoryContributor(
        url = "https://maven.parchmentmc.org/",
        groups = setOf(
            "org.parchmentmc",
            "org.parchmentmc.data",
            "org.parchmentmc.feather",
            "org.parchmentmc.librarian"
        )
    )

    public val Ornithe: RepositoryContributor = RepositoryContributor(
        url = "https://maven.ornithemc.net/releases/",
        groups = setOf("net.ornithemc", "keratin", "ploceus", "ornithe-loom")
    )

    public val Babric: RepositoryContributor = RepositoryContributor(
        url = "https://maven.glass-launcher.net/babric/",
        groups = setOf("babric", "babric-loom", "org.lwjgl.lwjgl")
    )
}
