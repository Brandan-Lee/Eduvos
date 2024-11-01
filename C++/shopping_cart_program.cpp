#include <iostream>
#include <iomanip>
#include <fstream>
using namespace std;

int main() {
    //declaration variables
    string name, selection;
    string txtfile = "shoppingCart.txt";
    int quantity, arrLength, index;
    double totalCost;
    const double DISCOUNT_THRESHOLD = 200.0;
    const double DISCOUNT_AMOUNT = 0.05; // 5%
    ofstream ofile;
    //parallel arrays to store the list of items and their matching prices
    string storeItems[] = {"Book"};
    double itemPrices[] = {19.90};

    //Receive user input
    cout << "Please enter your full name: ";
    getline(cin, name);

    //find the length of the array
    arrLength = sizeof(storeItems) / sizeof(storeItems[0]);
    cout << "\n--- Online Store Items ---\n";

    //display the list of items to the user
    for (int i = 0; i < arrLength; i++) {
        cout << storeItems[i] << "\t\tR" << fixed << setprecision(2) << itemPrices[i] << endl;
    }

    //Ask the user to select the item that they would like to add to their shopping cart
    cout << "Please enter the item that you would like to add to your shopping cart: ";
    getline(cin, selection);

    //loop through array to find the selected item
    for (int i = 0; i < arrLength; i++) {
        if (selection == storeItems[i]) {
            //Ask the user for the quantity of the item
            cout << "How much items would you like: ";
            cin >> quantity;
            index = i;
        } else {
            cout << "Invalid Input: Please try again\n";
            return 1;
        }
    }

    //calculate the total cost
    totalCost = quantity * itemPrices[index];

    //check to see if the customer qualifies for the discount and update their total bill
    if (totalCost > DISCOUNT_THRESHOLD) {
        totalCost = totalCost - (totalCost * DISCOUNT_AMOUNT);
    }

    //textfile operations
    ofile.open(txtfile, ofstream::app);
    if (ofile.fail()) {
        cout << "There was an error opening this file. Please try again.\n";
        return 1;
    } else {
        ofile << "Customer full name: " << name << endl;
        ofile << "They purchased the item: " << selection << endl;
        ofile << "The quantity of items they purchased was: " << quantity << endl;
        ofile << "Their final bill was: R" << fixed << setprecision(2) << totalCost << endl;
        ofile.close();
        
        cout << "The customers final bill has been written to: " << txtfile << endl;
        return 0;
    }
}