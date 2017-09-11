import com.mantono.autotag.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

class VersionTests
{
	@Test
	fun findHighestVersionTest()
	{
		val tags: List<String> = listOf("0.0.1", "0.0.2", "0.1.0", "0.0.9")
		val highestVersion: Version = highestGitVersion(asVersions(tags))
		assertEquals("0.1.0", highestVersion.toString())
	}

	@Test
	fun findHighestVersionWithSnapshotOnLowerVersionTest()
	{
		val tags: List<String> = listOf("0.0.1", "0.0.2", "0.1.0", "0.0.9", "0.0.8-SNAPSHOT")
		val highestVersion: Version = highestGitVersion(asVersions(tags))
		assertEquals("0.1.0", highestVersion.toString())
	}

	@Test
	fun findHighestVersionWithSnapshotOnSameVersionAsHighestTest()
	{
		val tags: List<String> = listOf("0.0.1", "0.0.2", "0.3.0", "0.0.9", "0.3.0-SNAPSHOT")
		val highestVersion: Version = highestGitVersion(asVersions(tags))
		assertEquals("0.3.0", highestVersion.toString())
	}

	@Test
	fun findHighestVersionWithSnapshotVersionAsHighestTest()
	{
		val tags: List<String> = listOf("0.0.1", "0.0.2", "0.3.0", "0.0.9", "0.3.1-SNAPSHOT")
		val highestVersion: Version = highestGitVersion(asVersions(tags))
		assertEquals("0.3.1-SNAPSHOT", highestVersion.toString())
	}

	@Test
	fun createSimpleVersionWithMajorMinorBuild()
	{
		val version: Version = asVersion("1.0.0")
		assertEquals(1, version.major)
		assertEquals(0, version.minor)
		assertEquals(0, version.build)
		assertFalse(version.snapshot)
	}

	@Test
	fun createVersionWithMajorMinorBuildSnapshot()
	{
		val version: Version = asVersion("1.0.0-SNAPSHOT")
		assertEquals(1, version.major)
		assertEquals(0, version.minor)
		assertEquals(0, version.build)
		assertTrue(version.snapshot)
	}

	@Test
	fun createVersionWithMajorMinorSnapshot()
	{
		val version: Version = asVersion("1.0-SNAPSHOT")
		assertEquals(1, version.major)
		assertEquals(0, version.minor)
		assertEquals(0, version.build)
		assertTrue(version.snapshot)
	}

	@Test
	fun isValidVersionTestMajorOnly()
	{
		assertTrue(isValidVersion("1"))
	}

	@Test
	fun isValidVersionTestMajorMinor()
	{
		assertTrue(isValidVersion("1.2"))
	}

	@Test
	fun isValidVersionTestMajorMinorBuild()
	{
		assertTrue(isValidVersion("1.2.3"))
	}

	@Test
	fun isValidVersionTestMajorMinorBuildSnapshot()
	{
		assertTrue(isValidVersion("1.2.3-SNAPSHOT"))
	}

	@Test
	fun isValidVersionTestOneNumberTooMuch()
	{
		assertFalse(isValidVersion("1.2.3.4"))
	}

	@Test
	fun isValidVersionTestNonsenseEnd()
	{
		assertFalse(isValidVersion("1.2.3-IMWITHSTUPID"))
	}

	@Test
	fun isValidVersionDoubleSnapshot()
	{
		assertFalse(isValidVersion("1.2-SNAPSHOT.3-SNAPSHOT"))
	}
}