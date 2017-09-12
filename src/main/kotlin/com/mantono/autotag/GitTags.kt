package com.mantono.autotag

import java.io.File

fun listTagsForRepo(repo: String): List<String>
{
	val tagsDir = File("$repo/.git/refs/tags")
	if(!tagsDir.exists() || !tagsDir.isDirectory)
		throw IllegalArgumentException("$tagsDir could not be found, is $repo really a git directory?")

	return tagsDir.listFiles().asSequence()
		.map {it.name}
		.filterNotNull()
		.toList()
}

fun highestGitVersion(repo: String): Version
{
	val tags = listTagsForRepo(repo)
	val versions = asVersions(tags)
	return highestGitVersion(versions)
}

fun highestGitVersion(versions: List<Version>): Version
{
	return when(versions.isNotEmpty())
	{
		true -> versions.sortedDescending().first()
		else -> Version(0, 1, snapshot = true)
	}
}

fun tagGit(version: Version) = Runtime.getRuntime().exec("git tag $version").waitFor()