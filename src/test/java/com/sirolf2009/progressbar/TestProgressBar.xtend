package com.sirolf2009.progressbar

import org.junit.Test

class TestProgressBar {

	@Test
	def void test() {
		val bar3 = new ProgressBar.Builder().name("Unit Test").action(new DumbAction()).style(Styles.ASCII).build()
		println(bar3.get())
		val bar2 = new ProgressBar.Builder().name("Unit Test").action(new DumbAction()).style(Styles.BAR).build()
		println(bar2.get())
		val bar = new ProgressBar.Builder().name("Unit Test").action(new DumbAction()).build()
		println(bar.get())
	}
	
	static class DumbAction extends Action<String> {
		
		override call() throws Exception {
			for(var i = 0; i < 100; i++) {
				message = '''I have slept «i+1» times!'''
				progress()
				Thread.sleep(100)
			}
			"Done"
		}
		
		override getWorkloadSize() {
			100
		}
		
	}

}