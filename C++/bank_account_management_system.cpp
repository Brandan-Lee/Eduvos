#include <iostream>
#include <string>
#include <iomanip>
using namespace std;

//this is a bank account management system. The account uses 5 functions, namely creating an account with the bank, depositing, and withdrawing money from their bank account, as well as checking their account balance and to display their account details to the console

//function declarations
void createAccount(string &name, int &accountNumber, double &balance);
double depositMoney(double &balance);
double withdrawMoney(double &balance);
void CheckBalance(const double &balance);
void displayAccountDetails(string &name, int &accountNumber, const double &balance);

//This function allows the user to create an account with the bank
void createAccount(string &name, int &accountNumber, double &balance) {
    cout << "\n--- Creation of Account ---\n";
    cout << "Enter your name: ";
    cin.ignore();   
    getline(cin, name); //This allows text to be entered that includes spaces
    cout << "Enter your account number: ";
    cin >> accountNumber;

    //Display to the user atleast once that they have to enter a deposit. input validation to ensure that the user enters a deposit that is greater than 0
    do {
        cout << "Enter your initial deposit (must be greater than 0) R";
        cin >> balance;

        if (balance > 0) {
            cout << "Account created successfully!\n\n";
            break;
        } 
        else {
            cout << "Invalid input. The deposit must be greater than 0. Please try again.\n";
        }
    } while (balance <= 0);
}

//This function allows the user to deposit money into their bank account
double depositMoney(double &balance) {
    double depositAmount;
    cout << "\n--- Deposit money into Account ---\n";
    
    //Show to the user atleast once that they should deposit money into their bank account
    while (depositAmount >= 0) {
        cout << "Please enter the amount of funds that you want to deposit (greater than 0): ";
        cin >> depositAmount;

        if (depositAmount > 0) {
            balance += depositAmount;
            cout << "Deposit has been successful!\n\n";
            break;
        } else {
            cout << "Invalid input. The deposit must be greater than 0. Please try again.\n";
        }
    }

    return balance;
}

double withdrawMoney(double &balance) {
    double withdrawAmount;
    char choice;
    cout << "\n--- Withdraw Money from Account ---\n";

    //Show to the user atleast once that they should deposit money into their bank account
    while (balance > 0) {
        cout << "Please enter the amount of funds to withdraw from your account: ";
        cin >> withdrawAmount;
        
        //check to see if the withdraw amount is greater than the current balance or if the user did not enter a valid amount
        if (withdrawAmount >= balance) {
            cout << "Insufficient funds. Would you like to view your current balance (Y, N): ";
            cin >> choice;
            //display the current balance of the account
            if (choice == 'Y') {
                CheckBalance(balance);
            }
        } else if (withdrawAmount == 0) {
            cout << "The withdraw amount must be greater than 0\n";
        } else {
            balance -= withdrawAmount;
            cout << "The funds have been withdrawn from your account:\n\n";
            break;
        }
    }

    return balance;
}

void CheckBalance(const double &balance) {
    cout << "\n--- Account Balance ---\n";
    cout << "Your current balance is: R" << fixed << setprecision(2) << balance << endl;
    cout << endl;
}

void displayAccountDetails(string &name, int &accountNumber, const double &balance) {
    cout << "\n--- Account Details ---\n";
    cout << "Account Holder: " << name << endl;
    cout << "Account Number: " << accountNumber << endl;
    cout << "Current Balance: R" << fixed << setprecision(2) << balance << endl;
    cout << endl;
}

int main() {
    string accName;
    int accNumber, choice;
    double accBalance;

    //This loop must run up until the user decides to exit the program
    while (choice != 6) {
        //Print the bank menu to the user
        cout << "--- Bank Account Management System ---\n";
        cout << "1. Create account\n";
        cout << "2. Deposit Money\n";
        cout << "3. Withdraw Money\n";
        cout << "4. Check balance\n";
        cout << "5. Display Account Details\n";
        cout << "Enter your choice (1 - 6): ";
        cin >> choice;

        switch (choice) {
            case 1:
                createAccount(accName, accNumber, accBalance);
                break;
            case 2:
                accBalance = depositMoney(accBalance);
                break;
            case 3:
                accBalance = withdrawMoney(accBalance);
                break;
            case 4:
                CheckBalance(accBalance);
                break;
            case 5: 
                displayAccountDetails(accName, accNumber, accBalance);
                break;
            case 6:
                cout << "\nExiting the system. Goodbye!\n\n";
                cout << "Process finished with exit code 0\n";
                return 0;
                break;
            default:
                cout << "Invalid input. Please try again!\n\n";
                break;
        }
    }
}