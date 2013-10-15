package com.example.junit;

public class Troll {
	int firstNumber;
	int secondNumber;
	public int getFirstNumber() {
		return firstNumber;
	}

	public void setFirstNumber(int firstNumber) {
		this.firstNumber = firstNumber;
	}

	public int getSecondNumber() {
		return secondNumber;
	}

	public void setSecondNumber(int secondNumber) {
		this.secondNumber = secondNumber;
	}

	public  Troll(int firstNumber, int secondNumber) {
		this.firstNumber = firstNumber;
		this.secondNumber = secondNumber;
	}

	public  Troll() {
		firstNumber = 0;
		secondNumber = 0;
	}

	/**
	 * function add two number
	 * 
	 * @return total
	 */
	public int add() {
		return firstNumber + secondNumber;
	}

	/**
	 * function
	 * 
	 * @return
	 */
	public int multi() {
		return firstNumber * secondNumber;
	}

}
