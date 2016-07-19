package com.Data.DataDrivenExcel.Trello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Example {

	public static void main(String [] args){
		ArrayList array = new ArrayList();
		array.add(1);
		array.add(2);
		array.add(3);
		System.out.println("ArrayList Before reverse : "+array);
		Collections.reverse(array);
		System.out.println("ArrayList after reverse : "+array);
		Integer[] numbers ={9,7,3,10,5,6};
		int min = Collections.min(Arrays.asList(numbers));
		int max = Collections.max(Arrays.asList(numbers));
		System.out.println("Min number : "+min);
		System.out.println("Max number : "+max);
		List list = Arrays.asList("one two three four five six one two one".split(" "));
		System.out.println("List : "+list);
		Collections.rotate(list, 5);
		System.out.println("rotate : "+list);
		Collections.replaceAll(list, "one", "seven");
		System.out.println("Replace : "+list);
	}	
}