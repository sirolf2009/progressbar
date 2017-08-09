package com.sirolf2009.progressbar

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Callable
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors abstract class Action<T> implements Callable<T> {

	val messageQueue = new ArrayBlockingQueue<String>(100, true)
	val progressQueue = new ArrayBlockingQueue<Integer>(100, true)

	var int progress = 0
	var int workload = getWorkloadSize()
	var int progressBatch = 1
	private var int progressCounter = 0

	def void setMessage(String message) {
		messageQueue.offer(message)
	}

	def void progress() {
		progress(1)
	}

	def void progress(int progress) {
		this.progress += progress
		progressCounter++
		if(progressCounter % progressBatch == 0) {
			progressQueue.add(this.progress)
			progressCounter = 0
		}
	}

	def abstract int getWorkloadSize()

}
