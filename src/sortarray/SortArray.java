/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortarray;

/**
 *
 * @author brian
 */
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Class for sorting an array of Comparable objects from smallest to
 * largest.
 *
 * This code is from Chapter 8 of
 * Data Structures and Abstractions with Java 3/e by Carrano
 * @author Brian Radomski
 */
public class SortArray
{
    public static int trials = 1;
    public static int moves = 0;
    public static int comparisons = 0;
    public static final int SELECTION_SORT = 0;
    public static final int INSERTION_SORT = 1;
    public static final int SHELL_SORT = 2;
    public static final int IMPROVED_SHELL_SORT = 3;
    public static final int IMPROVED_BUBBLE_SORT = 4;
    public static final String SORT_NAMES[] = {"SELECTION", "INSERTION", "SHELL", "IMPROVED SHELL", "IMPROVED BUBBLE"};
    public static final int NUMBER_OF_SORTS = 5;
    public static final int NUMBER_OF_STATS = 2;
    public static long sortTotal[][];
    public static final int MOVES_INDEX = 0;
    public static final int COMP_INDEX = 1;

    /**************************************************************
     * ITERATIVE SELECTION SORT
     **************************************************************/


    /**
     * Task: Sorts the first n objects in an array into ascending order.
     *
     * @param a an array of Comparable objects
     * @param n an integer > 0
     */
    public static <T extends Comparable<? super T>>
    void selectionSort(T[] a, int n)
    {
        for (int index = 0; index < n - 1; index++)
        {
            int indexOfNextSmallest = getIndexOfSmallest(a, index, n - 1);
            swap(a, index, indexOfNextSmallest);
        } // end for
    } // end selectionSort


    /**
     * Task: Finds the index of the smallest value in an array.
     *
     * @param a     an array of Comparable objects
     * @param first an integer >= 0 that is the index of the first
     *              array element to consider
     * @param last  an integer >= 0 that is the index of the last
     *              array element to consider
     * @return the index of the smallest value among
     *         a[first], a[first+1], . . . , a[last]
     */
    public static <T extends Comparable<? super T>>
    int getIndexOfSmallest(T[] a, int first, int last)
    {
        T min = a[first];
        int indexOfMin = first;
        for (int index = first + 1; index <= last; index++)
        {
            comparisons++;
            if (a[index].compareTo(min) < 0)
            {
                min = a[index];
                indexOfMin = index;

            } // end if
        } // end for
        return indexOfMin;
    } // end getIndexOfSmallest


    /**
     * Task: Swaps the array elements a[i] and a[j].
     *
     * @param a an array of  objects
     * @param i an integer >= 0 and < a.length
     * @param j an integer >= 0 and < a.length
     */
    private static <T>
    void swap(T[] a, int i, int j)
    {

        if (i != j)
        {
            moves++;
            T temp = a[i];
            a[i] = a[j];
            a[j] = temp;
            moves++;
        }
    } // end swap


    /**************************************************************
     * ITERATIVE INSERTION SORT (WE ARE NOT USING THE RECURSIVE VERSION
     * TO AVOID STACK OVERFLOWS)
     **************************************************************/


    /**
     * Task: Sorts the first n objects in an array into ascending order.
     *
     * @param a an array of Comparable objects
     * @param n an integer > 0
     */
    public static <T extends Comparable<? super T>>
    void insertionSort(T[] a, int n)
    {
        insertionSort(a, 0, n - 1);
    } // end insertionSort


    /**
     * Task: Iterative insertion sort of the  objects in a range of locations in an array into ascending order.
     *
     * @param a     an array of Comparable objects
     * @param first an integer >= 0
     * @param last  an integer > first and < a.length
     */

    public static <T extends Comparable<? super T>>
    void insertionSort(T[] a, int first, int last)
    {
        T temp;
        boolean foundLocation;
        int loc;

        for (int i = first + 1; i <= last; i++)
        {
            temp = a[i];

            // move values over to make room
            loc = i - 1;  // start with value to the left
            foundLocation = false;
            while (loc >= first && !foundLocation)
            {
                comparisons++;
                if (a[loc].compareTo(temp) > 0)
                {
                    moves++;
                    a[loc + 1] = a[loc];
                    loc--;
                } else
                    foundLocation = true;  // found the spot
            }

            // put the value in the right place
            moves++;
            a[loc + 1] = temp;
        } // end for
    } // end insertionSort


    /**************************************************************
     * ITERATIVE SHELL SORT
     **************************************************************/

    /**
     * Task: Sorts the first n objects in an array into ascending order.
     *
     * @param a an array of Comparable objects
     * @param n an integer > 0
     */
    public static <T extends Comparable<? super T>>
    void shellSort(T[] a, int n)
    {
        shellSort(a, 0, n - 1);
    } // end shellSort


    /**
     * Task: Use incremental insertion sort with different increments to
     * sort a range of values in the array
     *
     * @param a     an array of Comparable objects
     * @param first an integer >= 0
     * @param last  an integer > first and < a.length
     */
    public static <T extends Comparable<? super T>>
    void shellSort(T[] a, int first, int last)
    {
        int n = last - first + 1; // number of array elements
        for (int space = n / 2; space > 0; space = space / 2)
        {
            for (int begin = first; begin < first + space; begin++)
                incrementalInsertionSort(a, begin, last, space);
        } // end for
    } // end shellSort


    /**
     * Task: Sorts equally spaced elements of an array into
     * ascending order.
     *
     * @param a     an array of Comparable objects
     * @param first an integer >= 0 that is the index of the first
     *              array element to consider
     * @param last  an integer >= first and < a.length that is the
     *              index of the last array element to consider
     * @param space the difference between the indices of the
     *              elements to sort
     */
    public static <T extends Comparable<? super T>>
    void incrementalInsertionSort(T[] a, int first, int last, int space)
    {
        int unsorted, index;
        for (unsorted = first + space; unsorted <= last; unsorted = unsorted + space)
        {
            T firstUnsorted = a[unsorted];

            for (index = unsorted - space; index >= first; index = index - space)
            {
                comparisons++;
                if (firstUnsorted.compareTo(a[index]) >= 0)
                    break;
                else
                {
                    moves++;
                    a[index + space] = a[index];
                }
            } // end for
            moves++;
            a[index + space] = firstUnsorted;
        } // end for
    } // end incrementalInsertionSort

    /**
     * Task: Sorts the first n objects in an array into ascending order.
     *        Improved efficiency by adding 1 to space any time it is even
     *
     * @param a an array of Comparable objects
     * @param n an integer > 0
     */
    public static <T extends Comparable<? super T>>
    void improvedShellSort(T[] a, int n)
    {
        shellSortNoEven(a, 0, n - 1);
    } // end shellSort

    public static <T extends Comparable<? super T>>
    void shellSortNoEven(T[] a, int first, int last)
    {
        int n = last - first + 1; // number of array elements
        for (int space = n / 2; space > 0; space = space / 2)
        {
            if( space % 2 == 0)
            {
                space++;
            }
            for (int begin = first; begin < first + space; begin++)
            {
                incrementalInsertionSort(a, begin, last, space);
            }
        } // end for


    } // end shellSortNoEven


    /**
     * Task: Sorts the first n objects in an array into ascending order.
     *       Improved efficiency by eliminating unnecessary passes
     *
     * @param a an array of Comparable objects
     * @param n an integer > 0
     */
    public static <T extends Comparable<? super T>>
    void improvedBubbleSort(T[] a, int n)
    {
        int firstPosition = 0;
        int lastSwap = -1;
        int lastPosition = n - 1;

        while( firstPosition < lastPosition)
        {
            lastSwap = firstPosition;
            for( int i = firstPosition; i < lastPosition; i++)
            {
                if( a[i].compareTo(a[i + 1]) > 0)
                {
                    swap(a, i , i + 1);
                    lastSwap = i;
                }
                comparisons++;
            }
            lastPosition = lastSwap;
        }

    }


    // ************ TEST DRIVER *****************

    public static void main(String args[])
    {
        Integer dataRandom[];

        System.out.println("What size arrays should be used?");
        int arraySize = getInt("   It should be an integer value greater than or equal to 1.");

        System.out.println("How many trials should be run?");
        trials = getInt("   It should be an integer value greater than or equal to 1.");

        sortTotal = new long[NUMBER_OF_SORTS][NUMBER_OF_STATS];

        for (int i = 0; i < trials; i++)
        {
            System.out.println("=============    TRIAL # " + (i + 1) + "    ===============");
            dataRandom = generateRandomArray(arraySize);
            runStatistics(dataRandom);
            System.out.println("============= END OF TRIAL # " + (i + 1) + " ==============\n");
        }
        displayAverages();
        System.out.println("************************************\n");
    }

    /**
     * Runs statistics using each of the supported sorts sorting the same array
     * first in the original random order,
     * next in ascending order, and finally in descending order.
     * Calculates total only for the sorts of arrays in random order
     */

    private static void runStatistics(Integer dataOriginal[])
    {
        Integer data[];

        for (int sort = 0; sort < NUMBER_OF_SORTS; sort++)
        {
            System.out.println("\n*** ANALYZING " + SORT_NAMES[sort] + " SORT ***");
            System.out.println("---> ARRAY IN RANDOM ORDER");
            data = Arrays.copyOf(dataOriginal, dataOriginal.length);
            display(data);
            startStatistics();
            switch (sort)
            {
                case SELECTION_SORT:
                    selectionSort(data, data.length);
                    break;
                case INSERTION_SORT:
                    insertionSort(data, data.length);
                    break;
                case SHELL_SORT:
                    shellSort(data, data.length);
                    break;
                case IMPROVED_SHELL_SORT:
                    improvedShellSort(data, data.length);
                    break;
                case IMPROVED_BUBBLE_SORT:
                    improvedBubbleSort(data, data.length);
                    break;
                default:
                    System.out.println("NOT SUPPORTED SORT");
            }
            displayStatistics();
            updateTotals(sort);
            System.out.println("The sorted array is: ");
            display(data);

            System.out.println("---> ARRAY SORTED IN ASCENDING ORDER");
            System.out.println("The array is: ");
            display(data);
            startStatistics();
            switch (sort)
            {
                case SELECTION_SORT:
                    selectionSort(data, data.length);
                    break;
                case INSERTION_SORT:
                    insertionSort(data, data.length);
                    break;
                case SHELL_SORT:
                    shellSort(data, data.length);
                    break;
                case IMPROVED_SHELL_SORT:
                    improvedShellSort(data, data.length);
                    break;
                case IMPROVED_BUBBLE_SORT:
                    improvedBubbleSort(data, data.length);
                    break;
                default:
                    System.out.println("NOT SUPPORTED SORT");
            }
            displayStatistics();
            System.out.println("The sorted array is: ");
            display(data);

            System.out.println("---> ARRAY SORTED IN DESCENDING ORDER");
            reverseOrder(data);
            System.out.println("The array is: ");
            display(data);
            startStatistics();
            switch (sort)
            {
                case SELECTION_SORT:
                    selectionSort(data, data.length);
                    break;
                case INSERTION_SORT:
                    insertionSort(data, data.length);
                    break;
                case SHELL_SORT:
                    shellSort(data, data.length);
                    break;
                case IMPROVED_SHELL_SORT:
                    improvedShellSort(data, data.length);
                    break;
                case IMPROVED_BUBBLE_SORT:
                    improvedBubbleSort(data, data.length);
                    break;
                default:
                    System.out.println("NOT SUPPORTED SORT");
            }
            displayStatistics();
            System.out.println("The sorted array is: ");
            display(data);
        }
    }

    /**
     * Initializes counters for the statistics of the single run of the analyzed sort
     */
    private static void startStatistics()
    {
        moves = 0;
        comparisons = 0;
    }

    /**
     * Displays statistics for the current run of the analyzed sort
     */
    private static void displayStatistics()
    {
        System.out.println("Number of comparisons --> " + comparisons);
        System.out.println("Number of moves --> " + moves);
    }

    /**
     * Updates totals for the current run of the analyzed sort
     */
    private static void updateTotals(int sort)
    {
        sortTotal[sort][MOVES_INDEX] += moves;
        sortTotal[sort][COMP_INDEX] += comparisons;
    }

    /**
     * Displays statistics for a analyzed sort
     */
    private static void displayAverages()
    {
        System.out.println("\nFINAL STATISTICS FROM " + trials + " TRIALS:");
        System.out.println();

        // FOR EACH SORT DISPLAY AVERAGES OF COMPARISONS, AND MOVES
        for( int i = 0; i < NUMBER_OF_SORTS; i++)
        {
            System.out.println();
            System.out.println("*** STATS FOR " + SORT_NAMES[i] + "SORT ***");


                    System.out.println("Averages number of comparisons --> " + sortTotal[i][COMP_INDEX] / trials);
                    System.out.println("Average number of moves --> " + sortTotal[i][MOVES_INDEX] / trials);
            }

        System.out.println();
    }

    /**
     * Reverses the order of elements in the given array
     *
     * @param data array that in this application is sorted in ascending order
     */

    private static void reverseOrder(Integer[] data)
    {
        int mid = data.length / 2;
        int last = data.length - 1;
        for ( int i = 0; i < mid; i++)
        {
            swap(data, i, last);
            last--;
        }
    }

    /**
     * Generate an array of random integer values.  The values will be between 0 and size.
     *
     * @param size the size of the array to generate
     * @return the array of integers
     */
    private static Integer[] generateRandomArray(int size)
    {
        Integer result[] = new Integer[size];
        Random generator = new Random(101); // for initial testing set value to 101
        final int MAX_NUMBER = 99;

       for (int i = 0; i < size; i ++)
       {
           result[i] = generator.nextInt(MAX_NUMBER + 1);
       }

        return result;
    }

    /**
     * Displays all elements of an array of Objects
     *
     * @param data the array to display
     */
    private static void display(Object[] data)
    {
        System.out.print("[ ");
        for (int i = 0; i < data.length; i++)
        {
            System.out.print(data[i] + " ");
        }
        System.out.println("]");
    }


    /**
     * Get an integer value
     *
     * @param rangePrompt String representing a message used to ask the user for input
     * @return an integer
     */
    private static int getInt(String rangePrompt)
    {
        Scanner input;
        int result = 10;        //default value is 10
        try
        {
            input = new Scanner(System.in);
            System.out.println(rangePrompt);
            result = input.nextInt();

        } catch (NumberFormatException e)
        {
            System.out.println("Could not convert input to an integer");
            System.out.println(e.getMessage());
            System.out.println("Will use 10 as the default value");
        } catch (Exception e)
        {
            System.out.println("There was an error with System.in");
            System.out.println(e.getMessage());
            System.out.println("Will use 10 as the default value");
        }
        return result;
    }
}// end SortArray

