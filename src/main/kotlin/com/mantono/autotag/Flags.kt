package com.mantono.autotag

enum class Flag(val arg: String)
{
	SNAPSHOT("snapshot"),
	NO_SNAPSHOT("no-snapshot"),
	MAJOR("major"),
	MINOR("minor"),
	BUILD("build"),
	DRY("dry")
}

fun asFlag(arg: String): Flag
{
	for(v in Flag.values())
	{
		val fullArg = "--${v.arg}"
		if(fullArg == arg)
			return v
	}
	throw IllegalArgumentException("Unrecognized command line argument: $arg")
}