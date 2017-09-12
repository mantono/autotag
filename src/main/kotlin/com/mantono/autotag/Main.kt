package com.mantono.autotag

fun main(args: Array<String>)
{
	val (repo, givenVersion, arguments) = filterArgs(args)
	val currentVersion = highestProjectVersion("/home/anton/code/autotag")
	val newVersion: Version = computeNewVersion(currentVersion, givenVersion, arguments)

	print("Changing version from '$currentVersion' -> ")
	println("'$newVersion'")
	writeGradle(repo, newVersion)
	tagGit(newVersion)
}

private fun computeNewVersion(currentVersion: Version, givenVersion: Version?, arguments: List<Flag>): Version
{
	val incremented: Version = incrementCurrentVersion(currentVersion, arguments)
	return givenVersion?.let {
		if(it > currentVersion)
			it
		else
			throw IllegalArgumentException("Given version $it is not greater than the current version $currentVersion")
	} ?: incremented
}

private fun incrementCurrentVersion(currentVersion: Version, arguments: List<Flag>): Version
{
	if(Flag.MAJOR in arguments)
		return currentVersion.incrementMajor()
	if(Flag.MINOR in arguments)
		return currentVersion.incrementMinor()
	if(Flag.BUILD in arguments)
		return currentVersion.incrementBuild()
	return currentVersion.increment()
}

fun filterArgs(args: Array<String>): Triple<String, Version?, List<Flag>>
{
	val repo: String = args
		.filter { !isValidVersion(it) }
		.filter { !it.startsWith("--") }
		.firstOrNull() ?: System.getProperty("user.dir")

	val arguments: List<Flag> = args
		.filter { it.startsWith("--") }
		.map { asFlag(it) }

	val version = args
		.filter { isValidVersion(it) }
		.map { asVersion(it) }
		.firstOrNull()

	return Triple(repo, version, arguments)
}


