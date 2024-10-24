#include <iostream>
#include <array>
using namespace std;

//global variables
string currentAccName = "";
int currentAccNum;
double currentAccBalance;

//This function allows the user to create an account with the bank
void createAccount(string name, int accountNumber, double balance) {
    cout << "Enter your name: ";
    cin >> name;
    cout << "Enter your account number: ";
    cin >> accountNumber;
    cout << "Enter your initial deposit (must be greater than 0): R";
    cin >> balance;

    if (balance > 0) {
        currentAccName = name;
        currentAccNum = accountNumber;
        currentAccBalance = balance;
        cout << "Account created successfully!\n";
        cout << "\n";
    } else {
        cout << "Enter your initial deposit (must be greater than 0): R";
        cin >> balance;
        cout << "Account created successfully!\n";
        cout << "\n";
    }
}

double DepositMoney(double balance) {
    double deposit;

    cout << "Enter the amount of funds to deposit (must be greater than 0): R";;
    cin >> deposit;

    if (deposit > 0) {
        currentAccBalance += deposit;
        cout << "Deposit was successfull. Your balance has been updated.\n";
        cout << "\n";
    } else {
        cout << "Enter the amount of funds to deposit (must be greater than 0): R";;
        cin >> deposit;
        cout << "\n";
    }

    return currentAccBalance;
}

double withdrawMoney(double balance) {
    double withdrawAmount;
    char choice;

    cout << "Enter the amount of funds to withdraw: R";
    cin >> withdrawAmount;

    if (withdrawAmount > balance) {
        cout << "Insufficient funds.\n";
        cout << "Enter the amount of funds to withdraw: R";
        cin >> withdrawAmount;
        cout << "The funds have been successfully withdrawn from your account\n";
        cout << "\n";
        currentAccBalance -= withdrawAmount;
    } else {
        cout << "The funds have been successfully withdrawn from your account\n";
        cout << "\n";
        currentAccBalance -= withdrawAmount;
    }

    return currentAccBalance;
}

void CheckBalance(double balance) {
    cout << "The current balance in the account is: R" << balance;
}

void displayAccountDetails(string name, int accountNumber, double balance) {
    cout << "Account Name: " << name << endl;
    cout << "Account Number: " << accountNumber << endl;
    cout << "Account Balance: " << currentAccBalance << endl;
    cout << "\n";
}

int main() {
    int choice, accNum;
    string accName = "";
    double accBalance;
    
    while (true) {
        cout << "--- Bank Account Management System ---\n";
        cout << "1. Create Account\n";
        cout << "2. Deposit Money\n";
        cout << "3. Withdraw Money\n";
        cout << "5. Display Account Details\n";
        cout << "6. Exit\n";
        cout << "Enter your choice (1 - 6): ";
        cin >> choice;

        switch (choice) {
            case 1:
                createAccount(accName, accNum, accBalance);
                break;
            case 2:
                currentAccBalance = DepositMoney(accBalance);
                break;
            case 3:
                currentAccBalance = withdrawMoney(currentAccBalance);
                break;
            case 4:
                CheckBalance(currentAccBalance);
                break;
            case 5:
                displayAccountDetails(currentAccName, currentAccNum, currentAccBalance);
                break;
            case 6:
                cout << "Exiting the system. Goodbye!\n";
                cout << "\n";
                cout << "Process finished with exit code 0";
                return 0;
                break;
            default:
                cout << "Invalid input. Please try again\n";
                cout << "\n";
                cout << "--- Bank Account Management System ---\n";
                cout << "1. Create Account\n";
                cout << "2. Deposit Money\n";
                cout << "3. Withdraw Money\n";
                cout << "5. Display Account Details\n";
                cout << "6. Display Account Details\n";
                cout << "Enter your choice (1 - 6): ";
                cin >> choice;
        }
    }  
}