package com.mantono.autotag

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File

fun listTagsForDir(dir: String): List<Ref>
{
	val builder = FileRepositoryBuilder()
	val repository: Repository = builder.setGitDir(File(dir))
		.readEnvironment()
		.findGitDir()
		.build()
	val git = Git(repository)
	return git.tagList().call() ?: emptyList()
}