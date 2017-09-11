package com.mantono.autotag

fun main(args: Array<String>)
{
	val version = highestProjectVersion("/home/anton/code/autotag")
	println(version)
	println(version.increment())
}