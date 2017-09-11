package com.mantono.autotag

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class GitFindVersionTests
{
	@Test
	fun gitFindTagsForNonEmptyRepo()
	{
		val workingDir = System.getProperty("user.dir")!!
		println(workingDir)
		val tags = listTagsForRepo(workingDir)
		println(tags)
		assertTrue(tags.isNotEmpty())
	}

	@Test
	fun gitFindTagsForNonGitRepo()
	{
		assertThrows(IllegalArgumentException::class.java)
		{
			listTagsForRepo("/dev/null")
		}
	}
}