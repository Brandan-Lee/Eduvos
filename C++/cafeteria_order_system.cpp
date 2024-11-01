#include <iostream>
#include <fstream>
#include <iomanip>
using namespace std;

//This program receives input from the user, displays a cafeteria menu, calculates a total bill and applies a discount if applicable. The customers total bill and details will be written to a textfile

int main() {
    //Declaration variables
    string name, surname;
    string txtFile = "CafeteriaBill.txt";
    int numItems, selectedItem;
    double totalBill = 0.00;
    ofstream ofile;
    //Constants
    const double DISCOUNT_RATE = 0.10;
    const double DISCOUNT_THRESHOLD = 100.00;
    //Menu Prices
    const double COFFEE_PRICE = 15.00;
    const double SANDWICH_PRICE = 30.00;
    const double SALAD_PRICE = 25.00;
    const double JUICE_PRICE = 10.00;
    const double MUFFIN_PRICE = 20.00;
    const double PIZZA_PRICE = 35.00;
    const double SOUP_PRICE = 18.00;
    const double BURGER_PRICE = 40.00;

    //Prompt the user to enter their name, surname and the number of items that they would like to order
    cout << "Enter your name: ";
    cin >> name;
    cout << "Enter your surname: ";
    cin >> surname;
    cout << "How many items would you like to order (up to 8)? ";
    cin >> numItems;
    //Display the cafeteria menu
    cout << "\n";
    cout << "Cafeteria Menu:\n";
    cout << "1. Coffee - R" << COFFEE_PRICE << ".00\n";
    cout << "2. Sandwich - R" << SANDWICH_PRICE << ".00\n";
    cout << "3. Salad - R" << SALAD_PRICE << ".00\n";
    cout << "4. Juice - R" << JUICE_PRICE << ".00\n";
    cout << "5. Muffin - R" << MUFFIN_PRICE << ".00\n";
    cout << "6. Pizza Slice - R" << PIZZA_PRICE << ".00\n";
    cout << "7. Soup - R" << SOUP_PRICE << ".00\n";
    cout << "8. Burger - R" << BURGER_PRICE << ".00\n";
    cout << "\n";

    //Ask the user to select their item and add it to the total bill with input validation
    for (int i = 1; i <= numItems; i++) {
        cout << "Select item " << i << " (1-8): ";
        cin >> selectedItem;

        switch(selectedItem) {
            case 1:
                totalBill += COFFEE_PRICE;
                break;
            case 2:
                totalBill += SANDWICH_PRICE;
                break;
            case 3:
                totalBill += SALAD_PRICE;
                break;
            case 4:
                totalBill += JUICE_PRICE;
                break;
            case 5:
                totalBill += MUFFIN_PRICE;
                break;
            case 6:
                totalBill += PIZZA_PRICE;
                break;
            case 7:
                totalBill += SOUP_PRICE;
                break;
            case 8:
                totalBill += BURGER_PRICE;
                break;
            default:
                cout << "You have entered an incorrect number. Please try again.\n";
                i--;
        }
    }

    //Calculate if the user will receive a discount or not
    if (totalBill > DISCOUNT_THRESHOLD) {
        totalBill = totalBill - (totalBill * DISCOUNT_RATE);
    } else {
        cout << "No discount applied.\n";
    }

    //Display the final bill to the user
    cout << "Final Bill: R" << fixed << setprecision(2) << totalBill << endl;

    //Textfile operations
    ofile.open(txtFile, ofstream::app);
    //check to see if the file exists. Write to the textfile if it exists, else display an error message
    if (ofile.fail()) {
        cout << "There was an error writing to " << txtFile << endl;
        return 1;
    } else {
        //Write the customers name, surname and final bill to the text file
        ofile << "Customers name: " << name << endl;
        ofile << "Customer surname: " << surname << endl;
        ofile << "Customer Final Bill: R" << fixed << setprecision(2) << totalBill << endl;
        ofile << "\n";
        //Close the textfile and display to the user that the bill has been written to the text file
        ofile.close();
        cout << "The bill has been written to " << txtFile << endl;
    }
    
    //close the application.
    return 0;
}