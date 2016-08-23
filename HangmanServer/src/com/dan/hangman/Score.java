package com.dan.hangman;

public class Score {
	int score = 0;

	public int getFreeScore (int attempt)
	{
		score = 100 - attempt*10;
		return score;
	}
}
