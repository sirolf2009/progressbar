package com.sirolf2009.progressbar

import java.time.Duration
import java.util.concurrent.Future
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors abstract class ActionTimed<T> extends Action<T> {

	val Duration duration

	new() {
		this(Duration.ofMillis(16)) //60 fps as default
	}

	new(Duration duration) {
		this.duration = duration
	}

	override onSubmitted(Future<T> future) {
		new Thread [
			while(!future.done) {
				Thread.sleep(duration.toMillis())
				update()
			}
		].start()
	}

	override progress(int progress) {
		getProgress().addAndGet(progress)
	}
	
	override setMessage(String message) {
		this.message.set(message)
	}

}
