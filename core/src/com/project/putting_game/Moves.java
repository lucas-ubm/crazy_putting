package com.project.putting_game;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import com.badlogic.gdx.math.Vector3;

public class Moves
{  
	private Vector3[] data;
	
	public Moves(String textFile)
	{
		CalculateMoves(textFile);
	}
	
	public void setData(Vector3[] data)
	{
		this.data = data;
	}
	
	public Vector3[] getData()
	{
		return data;
	}
	
	public void CalculateMoves(String textFile)
	{
		//Scanner in = new Scanner(System.in);
		String file = textFile;
		//in.next();
		String input;

		Vector3[] data = new Vector3[10];
		int inputnr = 0;
		try
		{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while((input = br.readLine()) != null)
			{
				String[] numbers = input.split(" ");
				if(inputnr >= data.length)
				{
					Vector3[] newData = new Vector3[2*data.length];					
					data = copy(data, newData);
				}

				float x = Float.parseFloat(numbers[0]);
				float y = Float.parseFloat(numbers[1]);

				data[inputnr] = new Vector3(x, y, 0);
				inputnr++;
			}
		}
		
		catch(FileNotFoundException e)
		{
			System.out.println("The file can't be found");
		}
		
		catch(IOException e)
		{
			System.out.println("Error while reading");
		}
		
		setData(data);		
	}
	
	public Vector3[] copy(Vector3[] first, Vector3[] second)
	{
		for(int i=0; i<first.length; i++)
		{
			second[i] = first[i];
		}
		return second;
	}

}