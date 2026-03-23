package re.kyu.terracotta.api.version

import org.gradle.api.JavaVersion

public object JavaVersionResolver {
    /*
    * Maps a minimum [MinecraftVersion] to the [JavaVersion] it requires.
    */
    private val thresholds: Map<MinecraftVersion, JavaVersion> = linkedMapOf(
        MinecraftVersion.Release.Legacy(21, 0) to JavaVersion.VERSION_21,
        MinecraftVersion.Release.Legacy(18, 0) to JavaVersion.VERSION_17,
        MinecraftVersion.Release.Legacy(17, 0) to JavaVersion.VERSION_16,
        MinecraftVersion.Release.Legacy(12, 0) to JavaVersion.VERSION_1_8,
    )

    /**
     * Resolves the minimum Java version required for an arbitrary Minecraft
     * [version].
     */
    public fun resolve(version: MinecraftVersion): JavaVersion =
        thresholds.entries.firstOrNull { (threshold, _) -> version >= threshold }
            ?.value
            ?: JavaVersion.VERSION_1_6
}
