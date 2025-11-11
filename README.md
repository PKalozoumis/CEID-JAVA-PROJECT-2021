# 🌐 CEID-JAVA-PROJECT-2021

This project was made for the **2nd semester** undergraduate course **"Object Oriented Programming"** of the **Computer Engineering & Informatics Department (CEID), University of Patras**.

The goal of the project was to gain basic experience with object-oriented programming in Java. It simulates a simplified **volunteer coordination platform** for the support of vulnerable groups during the COVID-19 pandemic.

Can you tell this project was made in 2021?

What? The title...?

Users can register to the system as **donators** or **beneficiaries**. Donators can donate any amount of the registered **products** (milk, sugar, rice) or offer their **services** (medical support, nursery support, babysitting). Beneficiaries are eligible, based on their family size, to request a certain amount of products or services. The organization is managed by an **admin** who can monitor all available goods and registered users.

💣 **NOTE**: Due to the simplicity of the project, there is no actual database. All data is either hard-coded in the Main class or kept in memory.

# Features
- Three types of users can access the system, each with their own interface. All operations are performed through nested menus in the **command line**.
- All users can log into the system using their **phone number**. If the phone number does not exist, the user can **register** with it.
- The **admin** can do the following:
  - View **inventory**
  - **List and delete** donators and beneficiaries
  - See the resources a donator is about to offer
  - See the resources a beneficiary has received so far
  - **Clear beneficiary received lists** (globally or per user), so that they can receive more items in the future
 
- Beneficiaries can **request resources**, as long as there is available **stock**. Additionally, a set of values is associated with each resource which determine how much a person can receive in total based on their family size. Once beneficiaries select the resources they want, they **commit** their request and the items get transferred to their **received lists**.
- Donators can **offer any amount of resources**. Once they select the items they want, they **commit** their offer and the items get transferred to the organization.

# How to Run
Due to the simplicity of the program, you most likely won't need an IDE to run it. Everything can easily be done through the command line.

First, clone this repository and enter the directory:
```bash
git clone git@github.com:PKalozoumis/CEID-JAVA-PROJECT-2021.git

cd CEID-JAVA-PROJECT-2021
```

## Installing Java
To build the application, Java is required. To check if Java is installed, run the following:
```bash
java -version
```
You should see something like the following:
```
openjdk version "17.0.17" 2025-10-21
OpenJDK Runtime Environment Temurin-17.0.17+10 (build 17.0.17+10)
OpenJDK 64-Bit Server VM Temurin-17.0.17+10 (build 17.0.17+10, mixed mode, sharing)
```

If it's not installed and you're on Windows, download Java from either one of these links:
- [Adoptium OpenJDK](https://adoptium.net/)
- [Oracle JDK](https://www.oracle.com/java/technologies/downloads)


On Debian-based distributions (e.g. Ubuntu), run the following:
```bash
# Update package lists
sudo apt update

# Install OpenJDK 21 (any version can work)
sudo apt install openjdk-21-jdk
```

In both cases, verify the installation with ```java --version```

## Build and Run the App
To compile the program, first create a folder with the name ```build```:
```bash
mkdir build
```
Then, use the following command to compile all ```.java``` files in the ```src``` folder into ```.class``` files that will be stored in the ```build``` folder:
```bash
javac -d build/ src/*
```

Finally, run the application with the following command. This puts the ```build``` directory into the class path, where Java will try to find the starting point (```Main``` file) of the application:
```
java -cp build Main
```

You should now be seeing the login menu.

Below are all the currently registered users, along with their roles. Use their numbers to connect to the system. Yes, those are phone numbers. They're on speed dial.

<table>
  <tr>
    <th>Role</th>
    <th>User</th>
    <th>Phone Number</th>
  </tr>
  <tr><td>Admin</td><td>Bob</td><td>1</td></tr>
  <tr><td>Beneficiary</td><td>Mike</td><td>2</td></tr>
  <tr><td>Beneficiary</td><td>Kyle</td><td>3</td></tr>
  <tr><td>Donator</td><td>Karen</td><td>4</td></tr>
</table>
