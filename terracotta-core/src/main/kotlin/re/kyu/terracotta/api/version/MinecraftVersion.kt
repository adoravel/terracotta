package re.kyu.terracotta.api.version

public interface SemanticallyVersioned {
    public val major: Int
    public val minor: Int
    public val patch: Int
}

/**
 * A typed representation of a Minecraft version string.
 *
 * @property precedence release progression stage for cross-variant comparison:
 * 						- alpha < beta < snapshot < pre-release < rc < release
 */
public sealed class MinecraftVersion(
    public val precedence: Int
) : Comparable<MinecraftVersion> {

    /**
     * A standard release. Two structurally distinct forms:
     *
     * - [Legacy]: the classic `1.x.x` scheme used through for versions ≤26.1
     * - [Modern]: the new `YY.R` scheme introduced in 2026 (e.g. 26.1, 26.2)
     */
    public sealed class Release(precedence: Int) : MinecraftVersion(precedence) {

        /**
         * A legacy release (e.g. `1.21.11`, `1.7.10`, `1.8.9`, `1.2.5`).
         *
         * [patch] defaults to 0 for versions without a patch component.
         */
        public data class Legacy(
            override val major: Int = 1,
            override val minor: Int,
            override val patch: Int = 0,
        ) : Release(7), SemanticallyVersioned {
            override fun toString(): String =
                if (patch == 0) "$major.$minor" else "$major.$minor.$patch"
        }

        /**
         * A modern release (e.g. `26.1`, `26.2`)
         * 
         * [year] is the two-digit calendar year (e.g. 20'26', 20'27').
         * [index] is the release index within that year, starting at 1.
         *
         * Only valid if [year] ≥ 26.
         */
        public data class Modern(
            val year: Int,
            val index: Int,
        ) : Release(8) {
            init {
                require(year >= 26) {
                    "Modern release year must be >= 26, got $year. " +
                            "Use Release.Legacy for 1.x.x versions."
                }
            }

            override fun toString(): String = "$year.$index"
        }
    }

    /**
     * A pre-release (e.g. `1.21.4-pre3`, `26.1-pre2`).
     * 
     * Pre-releases come after all snapshots for a given release cycle
     * but before the final release.
     */
    public data class PreRelease(
        val base: Release,
        val index: Int,
    ) : MinecraftVersion(5) {
        override fun toString(): String = "$base-pre$index"
    }

    /**
     * A release candidate (e.g. `1.21.4-rc2`).
     *
     * Release candidates come after pre-releases but before the final release.
     */
    public data class ReleaseCandidate(
        val base: Release,
        val index: Int,
    ) : MinecraftVersion(6) {
        override fun toString(): String = "$base-rc$index"
    }

    /**
     * A snapshot build. Two structurally distinct forms:
     *
     * - [Legacy]: the classic `YYwWWx` scheme (e.g. `24w14a`)
     * - [Modern]: the new `YY.R-snapshot-N` scheme (e.g. `26.1-snapshot-10`)
     */
    public sealed class Snapshot(precedence: Int) : MinecraftVersion(precedence) {
        /**
         * A legacy snapshot (e.g. `24w14a`).
         *
         * [year] and [week] are the two-digit calendar year and week number.
         * [revision] is the letter suffix for snapshots within the same period.
         */
        public data class Legacy(
            val year: Int,
            val week: Int,
            val revision: Char,
        ) : Snapshot(4) {
            override fun toString(): String =
                "${year.toString().padStart(2, '0')}w${week.toString().padStart(2, '0')}$revision"
        }

        /**
         * A modern snapshot (e.g. `26.1-snapshot-10`).
         *
         * [base] is the release this snapshot is building toward.
         * [index] is the snapshot number within that release cycle.
         */
        public data class Modern(
            val base: Release.Modern,
            val index: Int,
        ) : Snapshot(5) {
            override fun toString(): String = "$base-snapshot-$index"
        }
    }

    /**
     * A Beta version (e.g. `b1.7.3`).
     *
     * [patch] defaults to 0 for versions without a patch component.
     */
    public data class Beta(
        override val major: Int,
        override val minor: Int,
        override val patch: Int = 0,
    ) : MinecraftVersion(3), SemanticallyVersioned {
        override fun toString(): String =
            if (patch == 0) "b$major.$minor" else "b$major.$minor.$patch"
    }

    /**
     * An Alpha version (e.g. `a1.2.6`).
     * 
     * [patch] defaults to 0 for versions without a patch component.
     */
    public data class Alpha(
        override val major: Int,
        override val minor: Int,
        override val patch: Int = 0,
    ) : MinecraftVersion(2), SemanticallyVersioned {
        override fun toString(): String =
            if (patch == 0) "a$major.$minor" else "a$major.$minor.$patch"
    }

    override fun compareTo(other: MinecraftVersion): Int =
        compareBy<MinecraftVersion> { it.precedence }
            .thenComparator { a, b -> a.compareWithinPrecedence(b) }
            .compare(this, other)

    /**
     * Compares two versions that share the same precedence.
     *
     * This method is only responsible for ordering within a single
     * variant type, using its own version-specific rules.
     */
    private fun compareWithinPrecedence(other: MinecraftVersion): Int = when (this) {
        is SemanticallyVersioned if other is SemanticallyVersioned ->
            compareValuesBy(this, other, { it.minor }, { it.patch })

        is Release.Modern if other is Release.Modern ->
            compareValuesBy(this, other, { it.year }, { it.index })

        is PreRelease if other is PreRelease -> {
            val base = this.base.compareTo(other.base)
            if (base != 0) base else this.index.compareTo(other.index)
        }

        is ReleaseCandidate if other is ReleaseCandidate -> {
            val base = this.base.compareTo(other.base)
            if (base != 0) base else this.index.compareTo(other.index)
        }

        is Snapshot.Legacy if other is Snapshot.Legacy ->
            compareValuesBy(this, other, { it.year }, { it.week }, { it.revision })

        is Snapshot.Modern if other is Snapshot.Modern -> {
            val base = this.base.compareTo(other.base)
            if (base != 0) base else this.index.compareTo(other.index)
        }
    }
}
