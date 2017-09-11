package com.mantono.autotag

import java.io.File
import java.nio.file.Files
import kotlin.streams.asSequence

val GRADLE_VERSION_REGEX = Regex("^version\\s?=\\s?[\"']")

fun gradleVersion(repo: String): Version
{
	val file = File("$repo/build.gradle")
	return Files.lines(file.toPath())
		.asSequence()
		.filter { it.contains(GRADLE_VERSION_REGEX) }
		.map { it.split("=").last() }
		.map { it.trim() }
		.map { it.subSequence(1, it.length-1) }
		.map { asVersion(it.toString()) }
		.sortedDescending()
		.first()
}