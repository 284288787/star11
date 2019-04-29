package com.star.truffle.core.async;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.star.truffle.core.StarServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadPoolHelper {

  private ExecutorService executorService;

  public ThreadPoolHelper(ExecutorService executorService) {
    this.executorService = executorService;
  }

  /**
   * 开多个线程执行任务，一直等到任务执行完成，如果任务有异常，会抛出异常到主线程，没有返回值
   */
  public void run(boolean throwException, Runnable... tasks) {
    run(throwException, Integer.MAX_VALUE, tasks);
  }

  /**
   * 开多个线程执行任务，一直等到任务执行完成或者等待millisecond ms时间之后，如果任务有异常，会抛出异常到主线程，没有返回值
   */
  public void run(boolean throwException, long millisecond, Runnable... tasks) {
    if (null == tasks || tasks.length == 0) {
      return;
    }

    List<CompletableFuture<Void>> completableFutureList;
    if (!throwException) {
      completableFutureList = Stream.of(tasks).map(task -> CompletableFuture.runAsync(task, executorService).exceptionally(e -> {
        log.warn(e.getMessage(), e);
        return null;
      })).collect(Collectors.toList());
    } else {
      completableFutureList = Stream.of(tasks).map(task -> CompletableFuture.runAsync(task, executorService)).collect(Collectors.toList());
    }

    completeAll(millisecond, completableFutureList);
  }

  /**
   * 开多个线程执行任务，一直等到任务执行完成，如果任务有异常，不会抛到主线程，有返回值 如果线程有异常，对应任务结果将返回null，可以根据返回值来判断是否继续处理
   */
  @SafeVarargs
  public final <T> List<T> run(boolean throwException, Supplier<T>... suppliers) {
    return run(throwException, Integer.MAX_VALUE, suppliers);
  }

  /**
   * 开多个线程执行任务，一直等到任务执行完成或者等待millisecond ms时间之后，如果任务有异常，不会抛到主线程，有返回值 如果线程有异常，或者超时，对应任务结果将返回null，可以根据返回值来判断是否继续处理
   */
  @SafeVarargs
  public final <T> List<T> run(boolean throwException, long millisecond, Supplier<T>... suppliers) {
    if (null == suppliers || suppliers.length == 0) {
      return Lists.newArrayList();
    }

    List<CompletableFuture<T>> completableFutureList;
    if (!throwException) {
      completableFutureList = Stream.of(suppliers).map(supplier -> CompletableFuture.supplyAsync(supplier, executorService).exceptionally(e -> {
        log.warn(e.getMessage(), e);
        return null;
      })).collect(Collectors.toList());
    } else {
      completableFutureList = Stream.of(suppliers).map(supplier -> CompletableFuture.supplyAsync(supplier, executorService)).collect(Collectors.toList());
    }

    completeAll(millisecond, completableFutureList);

    List<T> list = Lists.newArrayList();
    try {
      for (CompletableFuture<T> future : completableFutureList) {
        list.add(future.getNow(null));
      }
    } catch (Exception e) {
      log.warn("CompletableFuture ExecutionException {}", e.getCause().getMessage());
      if (e.getCause() instanceof StarServiceException) {
        throw (StarServiceException) e.getCause();
      } else {
        throw new RuntimeException(e.getCause());
      }
    }
    return list;
  }

  private <T> void completeAll(long millisecond, List<CompletableFuture<T>> completableFutureList) {
    CompletableFuture<?>[] completableFutureArray = new CompletableFuture[completableFutureList.size()];

    try {
      CompletableFuture.allOf(completableFutureList.toArray(completableFutureArray)).get(millisecond, TimeUnit.MILLISECONDS);
    } catch (TimeoutException | InterruptedException e) {
      log.warn("CompletableFuture TimeoutException or InterruptedException, millisecond is {}, future list size is {}", millisecond, completableFutureList.size());
    } catch (ExecutionException e) {
      log.warn("CompletableFuture ExecutionException {}", e.getCause().getMessage());
      if (e.getCause() instanceof StarServiceException) {
        throw (StarServiceException) e.getCause();
      } else {
        throw new RuntimeException(e.getCause());
      }
    }
  }
}
