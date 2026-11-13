#include <iostream>
#include <limits>
#include "CantorSet.h"
#include "CantorMultiset.h"

using namespace std;

void runSetMenu() {
    CantorSet mySet;
    int choice;

    while (true) {
        cout << "\n--- SET MENU ---\n";
        cout << "1. Add element\n";
        cout << "2. Show set\n";
        cout << "3. Load from string\n";
        cout << "4. Test copy\n";
        cout << "5. Back to main menu\n";
        cout << "Choice: ";

        if (!(cin >> choice)) {
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            cout << "Invalid input. Enter a number.\n";
            continue;
        }

        cin.ignore(numeric_limits<streamsize>::max(), '\n');

        if (choice == 1) {
            string item;
            cout << "Enter item: ";
            getline(cin, item);
            mySet.add(item);
        }
        else if (choice == 2) {
            cout << "Set: " << mySet << endl;
        }
        else if (choice == 3) {
            cout << "Enter set string: ";
            cin >> mySet;
            cout << "String loaded successfully!\n";
        }
        else if (choice == 4) {
            CantorSet copySet = mySet;

            if (mySet == copySet) {
                cout << "Copy successful!\n";
            }
        }
        else if (choice == 5) {
            break;
        }
        else {
            cout << "Invalid choice. Try again.\n";
        }
    }
}
void runMultisetMenu() {
    CantorMultiset myMulti;
    int choice;

    while (true) {
        cout << "\n--- MULTISET MENU ---\n";
        cout << "1. Add element\n";
        cout << "2. Remove element\n";
        cout << "3. Show multiset\n";
        cout << "4. Load from string\n";
        cout << "5. Check if empty\n";
        cout << "6. Show cardinality\n";
        cout << "7. Check membership\n";
        cout << "8. Union\n";
        cout << "9. Intersection\n";
        cout << "10. Difference\n";
        cout << "11. Generate power set\n";
        cout << "12. Test copy\n";
        cout << "13. Back to main menu\n";
        cout << "Choice: ";

        if (!(cin >> choice)) {
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            cout << "Invalid input. Enter a number.\n";
            continue;
        }

        cin.ignore(numeric_limits<streamsize>::max(), '\n');

        if (choice == 1) {
            string item;
            cout << "Enter element: ";
            getline(cin, item);
            myMulti.add(item);
            cout << "Element added successfully.\n";
        }
        else if (choice == 2) {
            string item;
            cout << "Enter element to remove: ";
            getline(cin, item);

            if (myMulti.remove(item)) {
                cout << "One occurrence removed successfully.\n";
            } else {
                cout << "Element not found.\n";
            }
        }
        else if (choice == 3) {
            cout << "Multiset: " << myMulti << endl;
        }
        else if (choice == 4) {
            string input;
            cout << "Enter multiset (example: {a, a, b}): ";
            getline(cin, input);
            myMulti.parseFromString(input);
            cout << "Multiset loaded: " << myMulti << endl;
        }
        else if (choice == 5) {
            if (myMulti.isEmpty()) {
                cout << "The multiset is empty.\n";
            } else {
                cout << "The multiset is not empty.\n";
            }
        }
        else if (choice == 6) {
            cout << "Cardinality: " << myMulti.cardinality() << endl;
        }
        else if (choice == 7) {
            string item;
            cout << "Enter element to search for: ";
            getline(cin, item);

            if (myMulti.contains(item)) {
                cout << "Element exists in the multiset.\n";
            } else {
                cout << "Element does not exist.\n";
            }
        }
        else if (choice >= 8 && choice <= 10) {
            string input;
            CantorMultiset other;

            cout << "Enter the second multiset (example: {a, b, b}): ";
            getline(cin, input);
            other.parseFromString(input);

            if (choice == 8) {
                cout << "Union: " << (myMulti + other) << endl;
            }
            else if (choice == 9) {
                cout << "Intersection: " << (myMulti * other) << endl;
            }
            else {
                cout << "Difference: " << (myMulti - other) << endl;
            }
        }
        else if (choice == 11) {
            vector<CantorMultiset> subsets = myMulti.powerSet();

            cout << "Power set:\n";

            for (const auto& subset : subsets) {
                cout << subset << endl;
            }

            cout << "Total sub-multisets: " << subsets.size() << endl;
        }
        else if (choice == 12) {
            CantorMultiset copyMulti = myMulti;

            if (myMulti == copyMulti) {
                cout << "Copy successful!\n";
            } else {
                cout << "Copy failed.\n";
            }
        }
        else if (choice == 13) {
            break;
        }
        else {
            cout << "Invalid choice. Try again.\n";
        }
    }
}

int main() {
    int mainChoice;

    while (true) {
        cout << "\n=== MAIN MENU ===\n";
        cout << "1. Work with Set (No Duplicates)\n";
        cout << "2. Work with Multiset (Allows Duplicates)\n";
        cout << "3. Quit\n";
        cout << "Choice: ";

        if (!(cin >> mainChoice)) {
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            cout << "Invalid input. Enter a number.\n";
            continue;
        }

        cin.ignore(numeric_limits<streamsize>::max(), '\n');

        if (mainChoice == 1) {
            runSetMenu();
        }
        else if (mainChoice == 2) {
            runMultisetMenu();
        }
        else if (mainChoice == 3) {
            cout << "Program closed successfully.\n";
            break;
        }
        else {
            cout << "Invalid choice. Try again.\n";
        }
    }

    return 0;
}