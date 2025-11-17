package dev.erichaag.reproducer;

import org.gradle.api.Action;
import org.gradle.api.Task;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.compile.JavaCompile;

import javax.inject.Inject;

public abstract class MyAction implements Action<Task> {

    private final JavaCompile compileTask;

    @Inject
    public MyAction(JavaCompile compileTask) {
        this.compileTask = compileTask;
        initialize();
    }

    public static void addToTask(TaskProvider<JavaCompile> taskProvider, Action<? super MyAction> configurationAction) {
        taskProvider.configure(task -> {
            ObjectFactory objectFactory = task.getProject().getObjects();
            MyAction action = objectFactory.newInstance(MyAction.class, task);
            configurationAction.execute(action);
        });
    }

    public void initialize() {
        compileTask.doLast("DoLastFromMyAction", this);
    }

    @Override
    public void execute(Task task) {
        System.out.println("Hello from " + task.getName());
    }

}
