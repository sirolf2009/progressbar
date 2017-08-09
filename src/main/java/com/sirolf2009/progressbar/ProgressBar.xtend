package com.sirolf2009.progressbar

import java.util.concurrent.ExecutorService
import org.eclipse.xtend.lib.annotations.Data
import java.util.concurrent.Executors
import java.util.Date
import java.util.concurrent.atomic.AtomicReference
import java.util.Optional
import java.util.function.Supplier

@Data class ProgressBar<T> {
	
	val String name
	val Action<T> action
	val Style style
	val ExecutorService executor
	val int terminalWidth
	
	def get() {
		val started = new Date()
		val message = new AtomicReference<String>()
		val progress = new AtomicReference<Integer>(0)
		val Supplier<Progress> createProgress = [new Progress(started, name, Optional.ofNullable(message.get), progress.get(), action.workloadSize)]
		style.setup(terminalWidth, createProgress.get())
		val future = executor.submit(action)
		new Thread[
			while(!future.done) {
				val update = action.updateQueue.take()
				message.set(update.key)
				progress.set(update.value)
				style.draw(terminalWidth, createProgress.get())
			}
		].start()
		action.onSubmitted(future)
		val result = future.get()
		style.completed(terminalWidth, createProgress.get())
		return result
	}
	
	public static class Builder<T> {
		
		var String name
		var Action<T> action
		var Style style = Styles.SIMPLE
		var ExecutorService executor = Executors.newFixedThreadPool(1)
		var int terminalWidth = 100
		
		def build() {
			if(name === null) {
				throw new IllegalStateException("name must be given!")
			}
			if(action === null) {
				throw new IllegalStateException("action must be given!")
			}
			return new ProgressBar(name, action, style, executor, terminalWidth)
		}
		
		def name(String name) {
			this.name = name
			return this
		}
		
		def action(Action<T> action) {
			this.action = action
			return this
		}
		
		def style(Style style) {
			this.style = style
			return this
		}
		
		def executor(ExecutorService executor) {
			this.executor = executor
			return this
		}
		
		def terminalWidth(int terminalWidth) {
			this.terminalWidth = terminalWidth
			return this
		}
		
	}
	
}
