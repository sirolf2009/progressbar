package com.sirolf2009.progressbar

interface Style {
	
	def void setup(int terminalWidth, Progress progress)
	def void draw(int terminalWidth, Progress progress)
	def void completed(int terminalWidth, Progress progress)
	
}