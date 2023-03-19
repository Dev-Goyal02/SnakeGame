package com.company;
import javax.swing.*;

public class Snake extends JFrame{
    Snake(){
        add(new GameExecution());
        pack();
        setTitle("Snakes are Here!!!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setTitle("Snake Game");
        setResizable(false);

    }
    public static void main(String[] args) {
	new Snake().setVisible(true);

    }
}
