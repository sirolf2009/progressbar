package com.sirolf2009.progressbar

import org.junit.Test

class TestProgressBar {

	@Test
	def void test() {
		println("Indeterminate simple")
		val bar5 = new ProgressBar.Builder().name("Unit Test").action(new DumbAction()).style(Styles.INDETERMINATE_SIMPLE).build()
		println(bar5.get())
		println("ASCII Fast")
		val bar4 = new ProgressBar.Builder().name("Unit Test").action(new FastAction()).style(Styles.ASCII).build()
		println(bar4.get())
		println("ASCII")
		val bar3 = new ProgressBar.Builder().name("Unit Test").action(new DumbAction()).style(Styles.ASCII).build()
		println(bar3.get())
		println()
		println("Bar")
		val bar2 = new ProgressBar.Builder().name("Unit Test").action(new DumbAction()).style(Styles.BAR).build()
		println(bar2.get())
		println()
		println("Simple")
		val bar = new ProgressBar.Builder().name("Unit Test").action(new DumbAction()).build()
		println(bar.get())
	}

	static class DumbAction extends Action<String> {

		override call() throws Exception {
			for (var i = 0; i < 100; i++) {
				message = '''I have slept «i+1» times!'''
				progress()
				Thread.sleep(50)
			}
			"Done"
		}

		override getWorkloadSize() {
			100
		}

	}

	static class FastAction extends ActionTimed<String> {

		override call() throws Exception {
			for (var i = 0; i < 10000; i++) {
				message = '''I have slept «i+1» times!'''
				progress()
				Thread.sleep(1)
			}
			"Done"
		}

		override getWorkloadSize() {
			10000
		}

	}

}
