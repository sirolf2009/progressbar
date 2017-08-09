# progressbar

Add progress bars. Not really much to explain about it. Take a look at the only unit test!
```xtend
package com.sirolf2009.progressbar

import org.junit.Test

class TestProgressBar {

	@Test
	def void test() {
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
```

will output

```bash
Running com.sirolf2009.progressbar.TestProgressBar
ASCII
Unit Test
I have slept 100 times! 100% ┃◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼▶┃ (00:00:09)
Done

Bar
Unit Test
I have slept 100 times! 100% [=========================================================>] (00:00:09))
Done

Simple
Unit Test
I have slept 54 times! 54%                            
```
