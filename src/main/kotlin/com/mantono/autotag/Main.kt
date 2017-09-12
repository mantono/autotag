package com.mantono.autotag

fun main(args: Array<String>)
{
	val (repo, givenVersion, arguments) = filterArgs(args)

	val version = highestProjectVersion("/home/anton/code/autotag")
	//val version = getCorrectVersionOf(currentVersion, givenVersion)
	print("Changing version from '$version' -> ")
	val updatedVersion = version.increment()
	println("'$updatedVersion'")
	writeGradle(repo, updatedVersion)
	tagGit(updatedVersion)
}

fun getCorrectVersionOf(currentVersion: Version, givenVersion: Version?): Version
{
	givenVersion?.let { if(it > currentVersion) return it }
	return currentVersion
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


