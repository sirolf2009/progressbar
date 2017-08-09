package com.sirolf2009.progressbar

import java.util.concurrent.Callable
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.atomic.AtomicInteger
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors abstract class Action<T> implements Callable<T> {

	val messageQueue = new LinkedBlockingQueue<String>()
	val progressQueue = new LinkedBlockingQueue<Integer>()

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
			progressQueue.offer(this.progress.get())
			progressCounter.set(0)
		}
	}

	def abstract int getWorkloadSize()

}
