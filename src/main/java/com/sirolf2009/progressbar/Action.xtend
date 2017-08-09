package com.sirolf2009.progressbar

import java.util.concurrent.Callable
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.atomic.AtomicInteger
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.concurrent.Future
import java.util.concurrent.atomic.AtomicReference

@Accessors abstract class Action<T> implements Callable<T> {

	val updateQueue = new LinkedBlockingQueue<Pair<String, Integer>>()

	val message = new AtomicReference<String>()
	val progress = new AtomicInteger(0)
	var int workload = getWorkloadSize()

	def void setMessage(String message) {
		this.message.set(message)
		update()
	}

	def void progress() {
		progress(1)
	}

	def void progress(int progress) {
		this.progress.addAndGet(progress)
		update()
	}
	
	def update() {
		updateQueue.add(message.get() -> progress.get())
	}

	def abstract int getWorkloadSize()
	
	def void onSubmitted(Future<T> future) {
	}

}
