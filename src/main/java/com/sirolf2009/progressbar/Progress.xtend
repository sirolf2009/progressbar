package com.sirolf2009.progressbar

import java.util.Date
import java.util.Optional
import org.eclipse.xtend.lib.annotations.Data

@Data class Progress {
	
	val Date started
	val String actionName
	val Optional<String> message
	val int progress
	val int workload
	
	
}