#include <iostream>
#include <iomanip>
using namespace std;

//This program receives input from the user regarding the students scores. It calculates the average score of the class, finds the highest and lowest scores of the students and displays all the students scores at the end as well as the highest, lowest and average score of the class

int main() {
    //Declaration of array
    const int NUM_STUDENTS = 5;
    double studentScores[NUM_STUDENTS];
    //Declaration variables
    double avgScore, totalScore;
    int highestScore;

    //Display to the user to enter the score of the students
    cout << "Enter the score for " << NUM_STUDENTS << " students:\n";

    //Ask the user to add the scores of the number of students
    for (int i = 0; i < NUM_STUDENTS; i++) {
        cout << "Enter score for student " << i + 1 << ": ";
        cin >> studentScores[i];

        //Find the highest score
        if (studentScores[i] > highestScore) {
            highestScore = studentScores[i];
        }

        //Add the score to the total score of the class
        totalScore += studentScores[i];
    }

    //find the lowest score in the updated array
    int lowestScore = studentScores[0];
    for (int i = 0; i < NUM_STUDENTS; i++) {
        if (studentScores[i] <= lowestScore) {
            lowestScore = studentScores[i];
        }
    }

    //Display the scores of the students to the user
    cout << "\nScores entered: \n";

    for (int i = 0; i < NUM_STUDENTS; i++) {
        cout << "Student " << i + 1 << ": " << studentScores[i] << endl;
    }

    //calculate the average score of the class
    avgScore = totalScore / NUM_STUDENTS;
    
    //display the average score and round it to 2 decimal places, highest score, and lowest score of the class
    cout << "\nAverage score: " << fixed << setprecision(2) << avgScore << endl;
    cout << "Highest score: " << highestScore << endl;
    cout << "Lowest score: " << lowestScore << endl;
    //Display to the user that the program is finished and has exited
    cout << "\nProcess finished with exit code 0\n";

    //close the program
    return 0;
}