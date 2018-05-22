package com.project.putting_game;

import java.util.ArrayList;

public class FunctionAnalyser {

	public static ArrayList<String> ShuntingYard(String function){

		ArrayList<String> reverse= new ArrayList<String>();
		MyStack<Character> operators = new MyStack<Character>();
		int i = 0;
		while (i<function.length()) {
			char token = function.charAt(i);
			if (token == '0' || token == '1' || token == '2' || token == '3' || token == '4' || token == '5' || token == '6' || token == '7' || token == '8' || token == '9'||token=='.'||token=='x'||token=='y'){//number
				if(i>=1&&(function.charAt(i-1)=='0'||function.charAt(i-1)=='1'||function.charAt(i-1)=='2'||function.charAt(i-1)=='3'||function.charAt(i-1)=='4'||function.charAt(i-1)=='5'||function.charAt(i-1)=='6'||function.charAt(i-1)=='7'||function.charAt(i-1)=='8'||function.charAt(i-1)=='9'||function.charAt(i-1)=='.'))//number
					reverse.set(reverse.size()-1,reverse.get(reverse.size()-1)+Character.toString(token));
				else
					reverse.add(Character.toString(token));
				i++;
			}
			else if(token=='s'||token=='c'||token=='t'){//function
				operators.push(token);
				//operators.push(function.charAt(i+1));
				//operators.push(function.charAt(i+2));
				i+=3;
			}
			else if(token=='^') {//operator
				while (!operators.isEmpty()&&(operators.top()=='s'||operators.top()=='c'||operators.top()=='t') && operators.top()!='(') {
					reverse.add(Character.toString(operators.pop()));
				}
				operators.push(token);
				i++;
			}
			else if(token=='*'||token=='/'){//operator
				while(!operators.isEmpty()&&(operators.top()=='s'||operators.top()=='c'||operators.top()=='t'||operators.top()=='^'||operators.top()=='/'||operators.top()=='*')&&operators.top()!='('){//function, operator with greater precedence or operator with equal precedence and left associativity
					reverse.add(Character.toString(operators.pop()));
				}
				operators.push(token);
				i++;
			}
			else if(token=='-'||token=='+'){//operator
				while(!operators.isEmpty()&&(operators.top()=='s'||operators.top()=='c'||operators.top()=='t'||operators.top()=='^'||operators.top()=='/'||operators.top()=='*'||operators.top()=='+'||operators.top()=='-')&&operators.top()!='('){
					reverse.add(Character.toString(operators.pop()));
				}
				operators.push(token);
				i++;
			}
			else if(token=='(') {
				operators.push(token);
				i++;
			}
			else if(token==')') {
				while (operators.top() != '(')//TODO not function?
					reverse.add(Character.toString(operators.pop()));
				char popped = operators.pop();//left bracket
				i++;
			}
		}
		while(!operators.isEmpty())
			reverse.add(Character.toString(operators.pop()));
		return reverse;
	}

	public static Double reversePolish(ArrayList<String> reverse,double x,double y){
		String token;
		Double operand2;
		Double operand1;
		Double result;
		MyStack<Double> stack = new MyStack<Double>();
		for(int i=0; i<reverse.size(); i++) {
			token = reverse.get(i);
			if (token.equals("s")) {
				operand1 = stack.pop();
				result = Math.sin(operand1);
				stack.push(result);
			}
			else if(token.equals("c")) {
				operand1 = stack.pop();
				result = Math.cos(operand1);
				stack.push(result);
			}
			else if(token.equals("t")) {
				operand1 = stack.pop();
				result = Math.tan(operand1);
				stack.push(result);
			}
			else if (token.equals("^")) {
				operand2 = stack.pop();
				operand1 = stack.pop();
				result = Math.pow(operand1,operand2);
				stack.push(result);
			}
			else if(token.equals("/")) {
				operand2 = stack.pop();
				operand1 = stack.pop();
				result = operand1/operand2;
				stack.push(result);
			}
			else if(token.equals("*")) {
				operand2 = stack.pop();
				operand1 = stack.pop();
				result = operand1*operand2;
				stack.push(result);
			}
			else if(token.equals("+")) {
				operand2 = stack.pop();
				operand1 = stack.pop();
				result = operand1+operand2;
				stack.push(result);
			}
			else if(token.equals("-")) {
				operand2 = stack.pop();
				operand1 = stack.pop();
				result = operand1-operand2;
				stack.push(result);
			}
			else if (token.equals("x")){
				stack.push(x);
			}
			else if (token.equals("y")){
				stack.push(y);
			}
			else //token is number
				stack.push(Double.parseDouble(token));
		}
		return stack.pop();
	}

	/**
	 *
	 * @param reverse the formula in reverse polish notation
	 * @param t0 the initial time
	 * @param t the value that you want to calculate
	 * @param h the timestep so the precision which you want to calculate it
	 * @param y0 the value of the formula at the initial time
	 * @return the value of the function at the desired point
	 */
	public static double runge_kutta (ArrayList<String> reverse, double t0, double t, double h, double y0){
		double k1,k2,k3,k4;
		System.out.println(reverse);
		while (t0 < t){
			k1 = h*FunctionAnalyser.reversePolish(reverse, t0, y0);
			k2 = h*FunctionAnalyser.reversePolish(reverse, t0+1/3.0*h, y0+1/3.0*k1);
			k3 = h*FunctionAnalyser.reversePolish(reverse, t0+2/3.0*h, y0-1/3.0*k1+k2);
			k4 = h*FunctionAnalyser.reversePolish(reverse, t0+h, y0+k1-k2+k3);
			y0 = y0+ 1/8.0*(k1 + 3*k2 + 3*k3 + k4);

			t0+=h;
		}
		return y0;

	}

	public static double derivative(ArrayList<String> reverse, double x, String respect) {
		if(respect.equalsIgnoreCase("x")) {
			return (FunctionAnalyser.reversePolish(reverse,x+1e-10,0)-FunctionAnalyser.reversePolish(reverse,x,0))/1e-10;
		}
		else {
			return (FunctionAnalyser.reversePolish(reverse,0,x+1e-10)-FunctionAnalyser.reversePolish(reverse,0,x))/1e-10;
		}
	}

}
