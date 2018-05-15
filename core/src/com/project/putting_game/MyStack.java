package com.project.putting_game;
import java.util.ArrayList;
public class MyStack<E> {
	private ArrayList<E> list;
	public MyStack(){//constructor
		list = new ArrayList<E>();
	}
	public void push(E data){
		list.add(size(),data);
	}
	public E pop(){
		return list.remove(size()-1);
	}
	public boolean isEmpty(){
		return size()==0;
	}
	public E top(){
		return list.get(size()-1);
	}
	public int size(){
		return list.size();
	}
	public void print(){
		for(int i=list.size()-1; i>=0; i--)
			System.out.print(list.get(i)/*pop().toString()+" "*/);
		System.out.println();
	}
}
