package com.mantono.autotag

data class Version(val major: Int = 0,
                   val minor: Int = 0,
                   val build: Int = 0,
                   val snapshot: Boolean = false): Comparable<Version>
{

	fun incrementMajor(): Version = copy(major = major + 1)
	fun incrementMinor(): Version = copy(minor = minor + 1)
	fun incrementBuild(): Version = copy(build = build + 1)
	fun removeSnapshot(): Version = copy(snapshot = false)

	fun increment(): Version
	{
		if(snapshot)
			return removeSnapshot()
		return incrementBuild()
	}

	override fun compareTo(other: Version): Int
	{
		if(this.major != other.major)
			return this.major - other.major
		if(this.minor != other.minor)
			return this.minor - other.minor
		if(this.build != other.build)
			return this.build - other.build

		if(this.snapshot && !other.snapshot)
			return -1
		if(!this.snapshot && other.snapshot)
			return 1

		return 0
	}

	override fun toString(): String = "$major.$minor.$build${snapshot()}"
	private fun snapshot(): String = if(snapshot) "-SNAPSHOT" else ""
}

val VERSION_REGEX: Regex = Regex("(((\\d+)(\\.\\d+){0,2})(-SNAPSHOT)?)")
val VERSION_SPLIT_REGEX: Regex = Regex("\\.")
val SNAPSHOT_REGEX: Regex = Regex("\\d+-SNAPSHOT")

fun isValidVersion(tag: String): Boolean = tag.matches(VERSION_REGEX)

fun asVersion(tag: String): Version
{
	val snapshot: Boolean = tag.contains(SNAPSHOT_REGEX)
	val tagWithoutSnapshot: String = tag.removeSuffix("-SNAPSHOT")

	val numbers = tagWithoutSnapshot.split(VERSION_SPLIT_REGEX)
		.map { Integer.parseInt(it) }
		.plus(0)
		.plus(0)
		.take(3)

	return Version(numbers[0], numbers[1], numbers[2], snapshot)
}

fun asVersions(tags: Collection<String>): List<Version> = tags.filter { isValidVersion(it) }.map { asVersion(it) }

fun highestProjectVersion(repo: String): Version = maxOf(highestGitVersion(repo), gradleVersion(repo))