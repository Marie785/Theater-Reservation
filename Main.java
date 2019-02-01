package Tickets;

//Marie-Claire Salha
//mjs170530
//

import Tickets.Auditorium;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main 
{
    public static void main(String[] args) throws IOException
    {
        boolean valid = false,
                available = false;
        int menuNum = 0,
            rowNum = 0,
            adult = 0,
            child = 0,
            senior = 0,
            totalTickets = 0,
            colNums = 0;
        char startingSeat = ' ';
        String seatLetter = "";
        File file = new File("A1.txt");
        Scanner input = new Scanner(file);
        Scanner input2 = new Scanner(System.in);
        
        if(file.exists())
        {
            Auditorium A = new Auditorium(input);
            colNums = A.getNumCols() + 64;
            
            //shows the user the menu, and asks them to enter in a menu number
            //it will loop until they enter in a valid number
            do
            {
                do
                {
                    try
                    {
                        System.out.println("1. Reserve Seats");
                        System.out.println("2. Exit");
                        menuNum = input2.nextInt();

                        if(menuNum == 1 || menuNum == 2)
                        {
                            valid = true;
                        }
                        else
                        {
                            System.out.println("Please enter in a valid menu number.\n");
                            valid = false;
                        }
                    }
                    catch(InputMismatchException E)
                    {
                        System.out.println("That was an invalid menu number.");
                        input2.next();
                        valid = false;
                    }
                }while(valid == false);
            
                //if it equals 1, then we want to go through the whole reservation
                //process
                if(menuNum == 1)
                {
                    //prints out the auditorium for the user
                    System.out.println(A.toString());

                    //asks the user what row they would like to be in
                    //loops until they enter in a valid row
                    valid = false;
                    do
                    {
                        try
                        {
                           System.out.println("What row would you like to be in?");
                           rowNum = input2.nextInt();

                           if(rowNum >= 1 && rowNum <= A.getNumRows())
                           {
                               valid = true;
                           }
                           else
                           {
                               System.out.println("That was not a valid row number.");
                               valid = false;
                           }

                        }
                        catch(InputMismatchException E)
                        {
                            System.out.println("That was not a valid row number.");
                            input2.next();
                            valid = false;
                        }
                    }while(valid  == false);
                
                    //asks the user what starting seat they want
                    //loops until they've entered in a valid starting seat
                    valid = false;
                    do
                    {
                        try
                        {
                            System.out.println("Please enter in your starting seat:");
                            seatLetter = input2.next();

                            if(seatLetter.length() == 1)
                            {
                                startingSeat = seatLetter.charAt(0);

                                if(startingSeat >= 65 && startingSeat <= colNums)
                                    valid = true;
                            }
                            else
                            {
                                System.out.println("This was not a valid starting seat.");
                                valid = false;
                            }

                        }
                        catch(InputMismatchException E)
                        {
                            System.out.println("This was not a valid starting seat.");
                            input2.next();
                            valid = false;
                        }
                    }while(valid == false);

                    //asks the user the number of adult tickets the user wants
                    //loops until the user enters a valid value
                    valid = false;
                    do
                    {
                        try
                        {
                            System.out.println("Please enter in the number of adult tickets you want:");
                            adult = input2.nextInt();

                            if(adult >= 0)
                                valid = true;
                            else
                            {
                                System.out.println("You can't have a negative number of tickets.");
                                valid = false;
                            }
                        }
                        catch(InputMismatchException E)
                        {
                            System.out.println("This is an invalid number of adult tickets.");
                            input2.next();
                            valid = false;
                        } 
                    }while(valid == false);

                    //asks the user the number of child tickets the user wants
                    //loops until valid data is inputted
                    valid = false;
                    do
                    {
                        try
                        {
                            System.out.println("Please enter in the number of child tickets you want:");
                            child = input2.nextInt();

                            if(child >= 0)
                                valid = true;
                            else
                            {
                                System.out.println("You can't have a negative number of tickets.");
                                valid = false;
                            }
                        }
                        catch(InputMismatchException E)
                        {
                            System.out.println("This is an invalid number of child tickets.");
                            input2.next();
                            valid = false;
                        }
                    }while(valid == false);

                    //asks the user the number of senior tickets the user wants
                    //loops until valid data is inputted
                    valid = false;
                    do
                    {
                        try
                        {
                            System.out.println("Please enter in the number of senior tickets you want:");
                            senior = input2.nextInt();

                            if(senior >= 0)
                                valid = true;
                            else
                            {
                                System.out.println("You can't have a negative number of tickets.");
                                valid = false;
                            }
                        }
                        catch(InputMismatchException E)
                        {
                            System.out.println("This is an invalid number of senior tickets.");
                            input2.next();
                            valid = false;
                        }
                    }while(valid == false);

                    totalTickets = adult + child + senior;

                    //returns a boolean value to see if what the user wanted is 
                    //available for them in the theater
                    available = A.isAvailable(startingSeat, rowNum, totalTickets);

                    //if what they want is available, then update your theater
                    if(available == true)
                    {
                        A.updateTheater(startingSeat, rowNum, adult, child, senior);
                        System.out.println("Your seat has been reserved.");
                    }
                    else if(available == false)
                    {
                        A.bestAvailable(totalTickets, adult, child, senior); 
                    }

                }
            }while(menuNum != 2);
            
            //if it equals 2, then we want to print out the updated theater 
            //seating, with the number of tickets reserved and all the prices
            //included
            if(menuNum == 2)
            {
                input.close();
                PrintWriter output = new PrintWriter(new File("A1.txt"));
                
                //prints the auditorium to the file
                output.print(A.toFile());
                output.close();
                //System.out.println(A.toFile());
                
                System.out.println("Total Seats in Auditorium:   " + A.totalSeats());
                System.out.println("Total Tickets Sold:          " + A.totalTicketsSold());
                System.out.println("Adult Tickets Sold:          " + A.totalAdultSold());
                System.out.println("Child Tickets Sold:          " + A.totalChildSold());
                System.out.println("Senior Tickets Sold:         " + A.totalSeniorSold());
                System.out.println("Total Ticket Sales:          $" + A.totalSpent());
            }
        }
        else
        {
            System.out.println("Error: File could not be found.");
            input.close();
        }
    }
    
}
