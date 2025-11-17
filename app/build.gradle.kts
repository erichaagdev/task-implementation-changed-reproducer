import dev.erichaag.reproducer.MyAction

plugins {
  id("java")
}

MyAction.addToTask(tasks.compileJava) { }
