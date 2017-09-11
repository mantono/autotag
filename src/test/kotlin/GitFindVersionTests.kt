package com.mantono.autotag

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class GitFindVersionTests
{
	@Test
	fun gitFindTagsForEmptyRepo()
	{
		val workingDir = System.getProperty("user.dir")
		val tags = listTagsForDir(workingDir)
		assertTrue(tags.isEmpty())
	}
}