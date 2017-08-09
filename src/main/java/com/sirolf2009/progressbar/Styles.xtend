package com.sirolf2009.progressbar

import java.text.DecimalFormat
import java.time.Duration
import java.util.concurrent.DelayQueue
import java.util.concurrent.Delayed
import java.util.concurrent.TimeUnit
import org.eclipse.xtend.lib.annotations.Data

class Styles {

	public static val SIMPLE = new Style() {

		val percentageFormat = new DecimalFormat("#0")

		override setup(int terminalWidth, extension Progress progressObject) {
			println(actionName)
		}

		override synchronized draw(int terminalWidth, extension Progress progressObject) {
			val percentage = percentageFormat.format((progress as double) / (workload as double) * 100d) + "%"
			terminalWidth.clearLine()
			print(message.orElse("") + " " + percentage)
		}

		override completed(int terminalWidth, Progress progress) {
			println()
		}

	}

	public static val BAR = new Style() {

		val percentageFormat = new DecimalFormat("#0")
		val timeFormat = new DecimalFormat("00")

		override setup(int terminalWidth, extension Progress progressObject) {
			println(actionName)
		}

		override synchronized draw(int terminalWidth, extension Progress progressObject) {
			val percentage = percentageFormat.format((progress as double) / (workload as double) * 100d) + "%"
			terminalWidth.clearLine()
			val message = message.orElse("")
			val duration = Duration.ofMillis(System.currentTimeMillis - started.time)
			val time = '''(«timeFormat.format(duration.toHours)»:«timeFormat.format(duration.toMinutes)»:«timeFormat.format((duration.toMillis)/1000)»)'''
			val prefix = message + " " + percentage + " ["
			val suffix = "] " + time
			val barLength = terminalWidth - prefix.length - suffix.length
			val actualBarLength = Math.floor((progress as double) / (workload as double) * (barLength as double)) as int
			val filledInSpace = (0 ..< actualBarLength - 1).map["="].reduce[a, b|a + b].orNothing()
			val actualBar = filledInSpace.length + 1
			val emptySpace = (actualBar ..< barLength).map[" "].reduce[a, b|a + b].orNothing()
			val bar = filledInSpace + ">" + emptySpace
			print(prefix + bar + suffix)
		}

		def orNothing(String string) {
			if(string === null) {
				return ""
			}
			return string
		}

		override completed(int terminalWidth, Progress progress) {
			println()
		}

	}

	public static val ASCII = new Style() {

		val percentageFormat = new DecimalFormat("#0")
		val timeFormat = new DecimalFormat("00")

		override setup(int terminalWidth, extension Progress progressObject) {
			println(actionName)
		}

		override synchronized draw(int terminalWidth, extension Progress progressObject) {
			val percentage = percentageFormat.format((progress as double) / (workload as double) * 100d) + "%"
			terminalWidth.clearLine()
			val message = message.orElse("")
			val duration = Duration.ofMillis(System.currentTimeMillis - started.time)
			val time = '''(«timeFormat.format(duration.toHours)»:«timeFormat.format(duration.toMinutes)»:«timeFormat.format((duration.toMillis)/1000)»)'''
			val prefix = message + " " + percentage + " ┃"
			val suffix = "┃ " + time

			val barLength = terminalWidth - prefix.length - suffix.length
			val actualBarLength = (progress as double) / (workload as double) * (barLength as double)
			val fullBarsLength = Math.floor(actualBarLength) as int
			val filledInSpace = (0 ..< fullBarsLength).map["◼"].reduce[a, b|a + b].orNothing()
			val halfBar = if(actualBarLength - fullBarsLength >= 0.5) "◼" else "▶"
			val actualBar = filledInSpace.length + halfBar.length
			val emptySpace = (actualBar ..< barLength).map[" "].reduce[a, b|a + b].orNothing()
			val bar = filledInSpace + halfBar + emptySpace
			print(prefix + bar + suffix)
		}

		def orNothing(String string) {
			if(string === null) {
				return ""
			}
			return string
		}

		override completed(int terminalWidth, Progress progress) {
			println()
		}

	}

	public static val INDETERMINATE_SIMPLE = new Style() {

		val animation = new DelayQueue() => [
			add(new AnimationFrame("│", 0))
			add(new AnimationFrame("╱", 100))
			add(new AnimationFrame("─", 200))
			add(new AnimationFrame("╲", 300))
		]
		var String currentKey = "│"

		override setup(int terminalWidth, extension Progress progressObject) {
		}

		override synchronized draw(int terminalWidth, extension Progress progressObject) {
			terminalWidth.clearLine()
			val newKey = animation.poll()
			val frame = if(newKey !== null) {
					animation.add(new AnimationFrame(currentKey, 100))
					currentKey = newKey.keyFrame
					currentKey
				} else
					currentKey
			print(message.orElse("") + " " + frame)
		}

		override completed(int terminalWidth, extension Progress progress) {
			terminalWidth.clearLine()
			println(message.orElse(""))
		}

	}

	@Data private static class AnimationFrame implements Delayed {

		val String keyFrame
		val long startTime

		new(String keyFrame, long delay) {
			this.keyFrame = keyFrame
			this.startTime = System.currentTimeMillis + delay
		}

		override getDelay(TimeUnit unit) {
			unit.convert(startTime - System.currentTimeMillis, TimeUnit.MILLISECONDS)
		}

		override compareTo(Delayed o) {
			startTime.compareTo((o as AnimationFrame).startTime)
		}

	}

	def static void clearLine(int terminalWidth) {
		print("\r" + (0 ..< terminalWidth).map[" "].reduce[a, b|a + b] + "\r")
	}

}
