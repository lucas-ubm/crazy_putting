package com.project.putting_game;

import java.util.Stack;

public class Queue<T> {
  Stack<T> enqueueStack = new Stack<T>();
  Stack<T> dequeueStack = new Stack<T>();

  //O(1) complexity
  public void enqueue(T element) {
    enqueueStack.push(element);
  }

  //O(n) complexity
  public T dequeue() {
    checkDequeue();
    return dequeueStack.pop();
  }

  public boolean isEmpty() {
    return enqueueStack.empty()&&dequeueStack.empty();
  }

  public T first() {
    checkDequeue();
    return dequeueStack.peek();
  }

  public int getSize(){
      return enqueueStack.size()+dequeueStack.size();
  }

  private void checkDequeue() {
    if (dequeueStack.empty()) {
      while(!enqueueStack.empty()) {
        dequeueStack.push(enqueueStack.pop());
      }
    }
  }
}
