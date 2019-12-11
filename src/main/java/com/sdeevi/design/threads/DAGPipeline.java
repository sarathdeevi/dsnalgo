package com.sdeevi.design.threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.Arrays.asList;

interface Task {
    void addDependency(Task task);

    void execute();

    String taskId();

    List<Task> dependencies();
}

class DemoTask implements Task {
    private List<Task> dependencies;
    private String taskId;

    public DemoTask(String taskId) {
        this.taskId = taskId;
        this.dependencies = new ArrayList<>();
    }

    @Override
    public void addDependency(Task task) {
        dependencies.add(task);
    }

    @Override
    public void execute() {
        System.out.println("Executing Task :" + taskId() + " in thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String taskId() {
        return taskId;
    }

    @Override
    public List<Task> dependencies() {
        return dependencies;
    }
}

public class DAGPipeline {
    private Map<String, CompletableFuture<Void>> completableFutureMap = new HashMap<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Task a = new DemoTask("a");
        Task b = new DemoTask("b");
        Task c = new DemoTask("c");
        Task d = new DemoTask("d");
        Task x = new DemoTask("x");
        Task y = new DemoTask("y");
        Task z = new DemoTask("z");
        d.addDependency(b);
        d.addDependency(c);
        c.addDependency(a);
        b.addDependency(a);
        x.addDependency(y);
        x.addDependency(z);
        y.addDependency(a);
        new DAGPipeline().execute(asList(a, b, c, d, x, y, z));
    }

    public void execute(List<Task> tasks) throws InterruptedException, ExecutionException {
        CompletableFuture<Void> completableFuture = CompletableFuture.completedFuture(null);
        for (Task task : tasks) {
            completableFuture = completableFuture.thenAcceptBothAsync(buildCompletableFuture(task), (a, b) -> {
            });
        }
        completableFuture.get();
        System.out.println("All tasks done. Closing executor");
    }

    private CompletableFuture<Void> buildCompletableFuture(Task task) {
        CompletableFuture<Void> completableFuture = completableFutureMap.get(task.taskId());
        if (completableFuture != null) {
            return completableFuture;
        }
        if (task.dependencies().isEmpty()) {
            CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(task::execute);
            completableFutureMap.put(task.taskId(), completableFuture1);
            return completableFuture1;
        }
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        for (Task task1 : task.dependencies()) {
            future = future.thenAcceptBothAsync(buildCompletableFuture(task1), (a, b) -> {
            });
        }
        future.thenRunAsync(task::execute);
        completableFutureMap.put(task.taskId(), future);
        return future;
    }
}
