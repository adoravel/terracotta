package re.kyu.terracotta.api.loader

/**
 * Information about a Minecraft mod loader.
 *
 * Built-in loaders are declared as objects on the companion, whereas
 * third-party loaders can be created and registered via [ModLoaderSystemRegistry.register].
 */
public data class ModLoaderSystem(
    val id: String,
    val displayName: String = id.replaceFirstChar { it.uppercase() },
    val capabilities: Set<LoaderCapability>,
) {
    public fun supports(capability: LoaderCapability): Boolean =
        capability in capabilities

    public fun requireCapability(capability: LoaderCapability): Unit =
        check(supports(capability)) {
            "Loader `$id` does not support ${capability.name}. " +
                    "Supported capabilities: ${capabilities.joinToString { it.name }}"
        }

    public companion object {
        // capabilities shared by all modern loaders
        private val Modern =
            setOf(LoaderCapability.MOJMAP, LoaderCapability.PARCHMENT, LoaderCapability.MIXIN);

        // capabilities shared by all Loom-based loaders
        private val Loom =
            setOf(LoaderCapability.YARN, LoaderCapability.ACCESS_WIDENER, LoaderCapability.REMAPPED_JAR);

        /** The standard Fabric loader for modern Minecraft (≥1.14). */
        public val Fabric: ModLoaderSystem = ModLoaderSystem(
            id = "fabric",
            capabilities = Modern + Loom
        )

        /** The modern Forge fork for Minecraft ≥1.20.2. */
        public val NeoForge: ModLoaderSystem = ModLoaderSystem(
            id = "neoforge",
            capabilities = Modern + setOf(LoaderCapability.ACCESS_TRANSFORMER)
        )

        /** A Fabric fork targeting Minecraft versions between c0.0.12a_03 and 1.14.4. */
        public val Ornithe: ModLoaderSystem = ModLoaderSystem(
            id = "ornithe",
            capabilities = Loom + LoaderCapability.MIXIN
        )

        /** A Fabric fork targetting Minecraft Beta 1.7.3. */
        public val Babric: ModLoaderSystem = ModLoaderSystem(
            id = "babric",
            capabilities = Loom + LoaderCapability.MIXIN
        )

        /** All (first-party) supported loaders built into terracotta. */
        public val builtIn: List<ModLoaderSystem> = listOf(Fabric, NeoForge, Ornithe, Babric)
    }
}
