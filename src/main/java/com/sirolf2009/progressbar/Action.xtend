package com.sirolf2009.progressbar

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Callable
import org.eclipse.xtend.lib.annotations.Accessors
import java.util.concurrent.atomic.AtomicInteger

@Accessors abstract class Action<T> implements Callable<T> {

	val messageQueue = new ArrayBlockingQueue<String>(100, true)
	val progressQueue = new ArrayBlockingQueue<Integer>(100, true)

	val AtomicInteger progress = new AtomicInteger(0)
	var int workload = getWorkloadSize()
	var int progressBatch = 1
	private val AtomicInteger progressCounter = new AtomicInteger(0)

	def void setMessage(String message) {
		messageQueue.offer(message)
	}

	def void progress() {
		progress(1)
	}

	def void progress(int progress) {
		this.progress.addAndGet(progress)
		if(progressCounter.addAndGet(1) % progressBatch == 0) {
			progressQueue.add(this.progress.get())
			progressCounter.set(0)
		}
	}

	def abstract int getWorkloadSize()

}
