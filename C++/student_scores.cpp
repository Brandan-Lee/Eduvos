#include <iostream>
using namespace std;

//This program receives input from the user regarding the students scores. It calculates the average score of the class, finds the highest and lowest scores of the students and displays all the students scores at the end as well as the highest, lowest and average score of the class

int main() {
    //Declaration variables
    double avgScore, totalScore;
    int numStudents = 5;
    int highestScore, score, studentScoresLength;
    //Declaration of array
    double studentScores[numStudents];
    //Display to the user to enter the score of the students
    cout << "Enter the score for " << numStudents << " students: \n";
    //Ask the user to add the scores of the number of students
    for (int i = 0; i < numStudents; i++) {
        cout << "Enter score for student " << i + 1 << ": ";
        cin >> score;
        //Find the highest score
        if (score > highestScore) {
            highestScore = score;
        }
        //Add the score to the total score of the class
        totalScore += score;
        //Add the score to the array
        studentScores[i] = score;
    }
    //Find the length of the student scores array
    studentScoresLength = sizeof(studentScores) / sizeof(studentScores[0]);
    //Display the scores of the students to the user
    cout << "\n";
    cout << "Scores entered: \n";
    for (int i = 0; i < studentScoresLength; i++) {
        cout << "Student " << i + 1 << ": " << studentScores[i] << endl;
    }
    //calculate the average score of the class
    avgScore = totalScore / numStudents;
    //find the lowest score in the arrray
    int lowestScore = studentScores[0];
    for (int i = 0; i < numStudents; i++) {
        if (studentScores[i] < lowestScore) {
            lowestScore = studentScores[i];
        }
    }
    //display the average score, highest score, and lowest score of the class
    cout << "\n";
    cout << "Average score: " << avgScore << ".00\n";
    cout << "Highest score: " << highestScore << endl;
    cout << "Lowest score: " << lowestScore << endl;
    //Display to the user that the program is finished and has exited
    cout << "\n";
    cout << "Process finished with exit code 0\n";
    return 0;
}