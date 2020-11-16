import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.IllegalFormatException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		/* While loop to keep the program running */
		System.out.println("Welcome to the Poised Project Registration Interface!\n");
		boolean runningProgram = true;
		while (runningProgram) {
			mainMenu();
			Scanner userMainMenuInput = new Scanner(System.in);
			int userMainMenuChoice = userMainMenuInput.nextInt();
			/* This section deals with the creation of the project object */
			if (userMainMenuChoice == 1) {

				System.out.println("To create a new project file, please enter the following details: \n");
				String projectName = projectNameCapture();
				int projectNumber = projectNumberCapture();
				String buildingType = buildingTypeCapture();
				String physicalAddressOfProject = physicalAddressCapture();
				int erfNumberForProject = erfNumberCapture();
				float projectFee = projectFeeCapture();
				float outstandingBalance = outstandingBalance();
				String projectDeadline = projectDeadline();
				Project newProjectObject = new Project(projectName, projectNumber, buildingType,
						physicalAddressOfProject, erfNumberForProject, projectFee, outstandingBalance, projectDeadline);
				System.out.println("Your project has been successfully registered");
				System.out.println(newProjectObject.displayProject() + "\n");

				/* Finalizing the project */
				System.out.println(
						"Finalize the project by generating an invoice for the client. 1 - Proceed\n 2 - Return to main menu");
				Scanner userFinalisationInput = new Scanner(System.in);
				int userFinalisationChoice = userFinalisationInput.nextInt();
				if (userFinalisationChoice == 1) {

					System.out.println(
							"An invoice will be generated by the system. Please enter the client's details: \n");

					/* Capturing client details to create client object */
					Client newClientObject = clientObjectCreation();

					/* Invoice generation */
					float amountToPay = newProjectObject.getProjectFee() - newProjectObject.getCurrBalance();
					if (amountToPay > 0) {
						invoiceGeneration(newClientObject, amountToPay);
					}

					/* File Creation & input into the file */
					String fileName = "CompletedProjects.txt";
					writeToFile(projectName, projectNumber, buildingType, physicalAddressOfProject, erfNumberForProject,
							projectFee, outstandingBalance, projectDeadline, fileName);

				} else if (userMainMenuChoice == 2) {
					/* This section will deal with the creation of the contractor object */
					Contractor newContractor = contractorObjectCreation();
					System.out.println(newContractor.displayPerson());

					/* Updating the contractor's telephone number */
					System.out.println("This is the telephone number of contractor: " + " "
							+ newContractor.getTelephoneNumber() + " .Would you like to update it? 1 - Yes\n 2 - No");

					Scanner contractorTelephoneNumChange = new Scanner(System.in);
					int contractorChoiceTelephone = contractorTelephoneNumChange.nextInt();
					if (contractorChoiceTelephone == 1) {
						contractorTelephoneChange(newContractor);
					}

					/* Updating the contractors email address */
					System.out.println("This is the email address of contractor: " + " "
							+ newContractor.getEmailAddress() + " . Would you like to update it? 1 - Yes\n 2 - No");

					Scanner contractorEmailAddressChange = new Scanner(System.in);
					int contractorChoiceEmail = contractorEmailAddressChange.nextInt();
					if (contractorChoiceEmail == 1) {
						contractorEmailAddressChange(newContractor);
					}
				}
			} else if (userMainMenuChoice == 3) {
				/* This section of code deals with reading the file contents */
				System.out.println("The projects captured into the system: \n");
				fileReaderMethod();

				/*
				 * INTENTION Prompt the user of the would like to update the details of the
				 * project ie. deadline & outstanding balance Once the user has updated those
				 * details, write it directly to the file
				 */
//				System.out.println("Would you like to update the current projects?\n 1 - Yes\n 2- No");
//				Scanner updateProjectPrompt = new Scanner(System.in);
//				int updateProjectChoice = updateProjectPrompt.nextInt();
//				if (updateProjectChoice == 1) {
//					System.out.println("Would you like to change the deadline?\n 1 - Yes\n 2 - No");
//					Scanner updateDeadlinePrompt = new Scanner(System.in);
//					int updateDeadlineChoice = updateDeadlinePrompt.nextInt();
//					if (updateDeadlineChoice == 1) {
//						deadlineChange(projects);
//						System.out.println(projects);
//					}

//				FileWriter writer = new FileWriter(fileName, true);
//				BufferedWriter output = new BufferedWriter(writer);
//				int arrayListSize = projectObjects.size();
//				for (int i = 0; i < arrayListSize; i++) {
//					output.write("\n" + projectObjects.get(i) + "\n");
//				}
//				output.close();

			} else if (userMainMenuChoice == 4) {
				// Descriptive message here
				String fileName = "CompletedProjects.txt";
				try {
					/*
					 * projectsObjects is the list of projects imported from the "CompletedProjects"
					 * file. The data importer method scans the file and place the projects into
					 * projectObjects
					 */
					List<String> projectObjects = new ArrayList<String>();
					dataImporter(fileName, projectObjects);
					// The projects are displayed here using an iterator
					Iterator<String> iterator1 = projectObjects.iterator();
					System.out.println("Here is a list of exisiting projects: ");
					while (iterator1.hasNext()) {
						System.out.println(iterator1.next());
					}
					/*
					 * The following piece of code deals with adding a new project to the list of
					 * projects that have already been imported. The user is prompted to input the
					 * details of the project. Once the details have been captured, it is stored in
					 * the list of project objects, namely projectObjects, as one string.
					 */
					System.out.println("Would you like to perfrom the following operations?"
							+ "\n 1 - Add a project to the list\n 2 - Edit a project on the list?");
					Scanner projectModifierPrompt = new Scanner(System.in);
					int projectModifierChoice = projectModifierPrompt.nextInt();
					if (projectModifierChoice == 1) {
						String projectName = projectNameCapture();
						int projectNumber = projectNumberCapture();
						String buildingType = buildingTypeCapture();
						String physicalAddressOfProject = physicalAddressCapture();
						int erfNumberForProject = erfNumberCapture();
						float projectFee = projectFeeCapture();
						float outstandingBalance = outstandingBalance();
						String projectDeadline = projectDeadline();

						projectObjects
								.add(stringFormatter(projectName, projectNumber, buildingType, physicalAddressOfProject,
										erfNumberForProject, projectFee, outstandingBalance, projectDeadline));
						System.out.println(projectObjects);
					} else if (projectModifierChoice == 2) {
						// Display projects that are currently in the list
						Iterator<String> iterator2 = projectObjects.iterator();
						System.out.println("Here is a list of exisiting projects: ");
						while (iterator2.hasNext()) {
							System.out.println(iterator2.next());
						}
						/*
						 * In order to edit an individual element, the projects must be split. The user
						 * is prompted to input which project they would like to edit from the
						 * "projectObjects" ArrayList and their choice is stored in the variable
						 * "userSelection". I have taken into account the difference between the index
						 * of a project object and the user's selection of the object. The
						 * stringSplitter object splits the string object from the commas into separate
						 * substrings. The substrings are stored in the splitString Array and then added
						 * to an ArrayList called "projectsToEdit".
						 */
						System.out.println("You are able to change the deadline and the outstanding balance!");
						System.out.println("Select a project you would like to edit: ");
						Scanner projectSelect = new Scanner(System.in);
						int userSelection = projectSelect.nextInt() - 1;
						List<String> projectToEdit = new ArrayList<String>();
						String[] splitString = new String[7];
						splitString = stringSplitter(projectObjects, splitString, userSelection);
						substringsToList(splitString, projectToEdit);
						System.out.println("Would you like to change the deadline?\n 1 - Yes\n 2 - No");
						Scanner updateDeadlinePrompt = new Scanner(System.in);
						/*
						 * The deadline of the project is changed. The deadlineChange method is called.
						 * The respective elements in the ArrayList are updated. The same logic is
						 * applied below with the outstanding balance.
						 */
						try {
							int updateDeadlineChoice = updateDeadlinePrompt.nextInt();
							if (updateDeadlineChoice == 1) {
								deadlineChange(projectToEdit);
								System.out.println(projectToEdit);
							}

						} catch (InputMismatchException e) {
							System.out.println("Invalid entry. Please enter a number");
						}

						System.out.println("Would you like to change the deadline?\n 1 - Yes\n 2 - No");
						Scanner outstandingBalancePrompt = new Scanner(System.in);
						try {
							int outstandingBalanceChoice = outstandingBalancePrompt.nextInt();
							if (outstandingBalanceChoice == 1) {
								outstandingBalanceChange(projectToEdit);
//								System.out.println(projectToEdit);
							}

						} catch (InputMismatchException e) {
							System.out.println("Invalid entry");
						}
						/*
						 * Here the project which the user has selected from the projectObject list is
						 * removed and replaced by the substrings,joined together, using the
						 * stringJoiner method.
						 */
						projectObjects.remove(userSelection);
						String joinedString = stringJoiner(projectToEdit.get(0), projectToEdit.get(1),
								projectToEdit.get(2), projectToEdit.get(3), projectToEdit.get(4), projectToEdit.get(5),
								projectToEdit.get(6), projectToEdit.get(7));
						projectObjects.add(userSelection, joinedString);

					}

				} catch (Exception e) {
					System.out.println("An error has occured during this operation");
				}
//				} catch (IndexOutOfBoundsException e) {
//					System.out.println("Index is out of bounds! Verify the size of the array");
//				} catch (NoSuchElementException e) {
//					System.out.println("No string was found!");
//				}

			}

		}

	}

//"%s, " + "%s, " + "%s, " + "%s, " + "%s,  " + "%s, " + "%s, " + "%s"
	// ;
	private static String stringJoiner(String name, String number, String building, String address, String erf,
			String fee, String balance, String deadline) {
		String stringJoined = String.join(", ", name, number, building, address, erf, fee, balance, deadline);

		return stringJoined;

	}

	public static String stringFormatter(String projectName, int projectNumber, String buildingType,
			String physicalAddressOfProject, int erfNumberForProject, float projectFee, float outstandingBalance,
			String projectDeadline) {
		String newProj = String.format("%s, " + "%d, " + "%s, " + "%s, " + "%d,  " + "%f, " + "%f, " + "%s",
				projectName, projectNumber, buildingType, physicalAddressOfProject, erfNumberForProject, projectFee,
				outstandingBalance, projectDeadline);

		return newProj;
	}

	public static void substringsToList(String[] array, List<String> list) {
		for (String x : array) {
			list.add(x);
		}
	}

	public static String[] stringSplitter(List<String> list, String[] arrayOfStrings, int userChoice) {
		arrayOfStrings = list.get(userChoice).split(", ");

		return arrayOfStrings;
	}

	public static void dataImporter(String fileName, List<String> list) throws FileNotFoundException {
		try {
			Scanner importer = new Scanner(new File(fileName));
			while (importer.hasNext()) {
				list.add(importer.nextLine() + "\n");
			}
		} catch (FileNotFoundException e) {
			System.out.println("File could not be found");
		} catch (IllegalStateException e) {
			System.out.println("The Scanner object is closed");
		} catch (NullPointerException e) {
			System.out.println("File name not specified!");
		}

	}

	public static <E> void deadlineChange(List<String> list) {
		System.out.println("Enter the new deadline (dd/mm/yyyy): ");
		Scanner newDateEntry = new Scanner(System.in);
		try {
			String newDate = newDateEntry.nextLine();
			list.remove(7);
			list.add(7, newDate);
			System.out.println("Your new deadline has been successfully registered");
		} catch (InputMismatchException e) {
			System.out.println("Invalid entry!");
		}

	}

	public static <E> void outstandingBalanceChange(List<String> list) {
		System.out.println("Enter the new balance: ");
		Scanner newBalanceEntry = new Scanner(System.in);
		try {
			float newBalanceFloat = newBalanceEntry.nextFloat();
			String newBalance = String.valueOf(newBalanceFloat);
			list.remove(6);
			list.add(6, newBalance);
			System.out.println("Your new deadline has been successfully registered");
		} catch (InputMismatchException e) {
			System.out.println("Invalid entry!");
		}

	}

	public static void outstandingBalanceChange(Project newProjectObject) {
		System.out.println("Enter the new balance: ");
		Scanner newBalanceInput = new Scanner(System.in);
		float newBalance = newBalanceInput.nextFloat();
		newProjectObject.setCurrBalance(newBalance);
	}

	public static void fileReaderMethod() {
		try {
			File savedProjects = new File("CompletedProjects.txt");
			Scanner fileReader = new Scanner(savedProjects);
			while (fileReader.hasNext()) {
				System.out.println(fileReader.nextLine() + "\n");
			}
			fileReader.close();
		} catch (IOException e) {
			System.out.println("An error has occured in attempting to read he file");
		}
	}

	public static void contractorEmailAddressChange(Contractor newContractor) {
		System.out.println("Enter the new email address:");
		Scanner newEmailAddressInput = new Scanner(System.in);
		String newEmailAddress = newEmailAddressInput.nextLine();
		newContractor.setEmailAddress(newEmailAddress);
		System.out.println("New email address registered!\n");
		System.out.println(newContractor.displayPerson());
	}

	public static void contractorTelephoneChange(Contractor newContractor) {
		System.out.println("Enter the new telephone number:");
		Scanner newTelephoneNumberInput = new Scanner(System.in);
		int newTelephoneNumber = newTelephoneNumberInput.nextInt();
		newContractor.setTelephoneNumber(newTelephoneNumber);
		System.out.println("New telephone number registered!\n");
		System.out.println(newContractor.displayPerson());
	}

	public static Contractor contractorObjectCreation() {
		System.out.println("Please enter the following details to register a new contractor: \n");
		String nameOfContractor = contractorName();
		int telephoneNumberOfContractor = contractorTelephoneNumber();
		String emailAddressOfContractor = contractorEmailAddress();
		String physicalAddressOfContractor = contractorPhysicalAddress();
		Contractor newContractor = new Contractor(nameOfContractor, telephoneNumberOfContractor,
				emailAddressOfContractor, physicalAddressOfContractor);
		System.out.println("New contractor registered!\n");
		return newContractor;
	}

	public static void writeToFile(String projectName, int projectNumber, String buildingType,
			String physicalAddressOfProject, int erfNumberForProject, float projectFee, float outstandingBalance,
			String projectDeadline, String fileName) {
		try {
			Formatter outputFile = new Formatter(fileName);
			outputFile.format("%s, " + "%d, " + "%s, " + "%s, " + "%d,  " + "%f, " + "%f, " + "%s", projectName,
					projectNumber, buildingType, physicalAddressOfProject, erfNumberForProject, projectFee,
					outstandingBalance, projectDeadline);
			outputFile.close();
		} catch (IllegalFormatException e) {
			System.out.println("An error has occured writing to the file");
		} catch (FileNotFoundException e) {
			System.out.println("File could not be located!");
		}
	}

	public static void invoiceGeneration(Client newClientObject, float amountToPay) {
		int invoiceNumber = (int) (Math.random() * 1000);
		System.out.println("Invoice " + " " + invoiceNumber + "\n");
		System.out.println(newClientObject.displayPerson() + "\n");
		System.out.println("Outstanding balance: " + " " + amountToPay);
	}

	public static Client clientObjectCreation() {
		String nameOfClient = customerName();
		int telephoneOfClient = customerTelephoneNumber();
		String emailAddressOfClient = customerEmailAddress();
		String physicalAddressOfClient = customerPhysicalAddress();
		Client newClientObject = new Client(nameOfClient, telephoneOfClient, emailAddressOfClient,
				physicalAddressOfClient);

		return newClientObject;
	}

	// Methods to capture the details of the project
	public static String projectNameCapture() {
		System.out.println("The name of the project: ");// Name of the project
		Scanner inputForName = new Scanner(System.in);
		String nameForProject = inputForName.nextLine();

		return nameForProject;

	}

	public static int projectNumberCapture() {
		System.out.println("The project number: ");
		Scanner inputForNum = new Scanner(System.in);
		int numForProject = inputForNum.nextInt();

		return numForProject;
	}

	public static String buildingTypeCapture() {
		System.out.println("Building type (eg. Apartment, House etc.): ");
		Scanner inputForBuild = new Scanner(System.in);
		String buildingType = inputForBuild.nextLine();

		return buildingType;
	}

	public static String physicalAddressCapture() {
		System.out.println("The physical address of the project: ");
		Scanner inputForPhysAdd = new Scanner(System.in);
		String physicalAddress = inputForPhysAdd.nextLine();

		return physicalAddress;
	}

	public static int erfNumberCapture() {
		System.out.println("The ERF number: ");
		Scanner inputForErf = new Scanner(System.in);
		int erf = inputForErf.nextInt();

		return erf;
	}

	public static float projectFeeCapture() {
		System.out.println("The total fee for the project: R ");
		Scanner inputForProjFee = new Scanner(System.in);
		float fees = inputForProjFee.nextFloat();

		return Math.round(fees);
	}

	public static float outstandingBalance() {
		System.out.println("The fees still outstanding: R ");
		Scanner inputForProjBal = new Scanner(System.in);
		float balance = inputForProjBal.nextFloat();

		return Math.round(balance);
	}

	public static String projectDeadline() {
		System.out.println("The deadline for the project: " + "\n");
		Scanner inputForDeadline = new Scanner(System.in);
		String deadline = inputForDeadline.nextLine();

		return deadline;
	}

	// Methods for capturing the details of the contractors
	public static String contractorName() {
		System.out.println("Contractor's name: \n");
		Scanner contrName = new Scanner(System.in);
		String conName = contrName.nextLine();

		return conName;
	}

	public static int contractorTelephoneNumber() {
		System.out.println("Contractor's telephone number: \n");
		Scanner contrTele = new Scanner(System.in);
		int conTele = contrTele.nextInt();

		return conTele;
	}

	public static String contractorEmailAddress() {
		System.out.println("Contractor's email address: \n");
		Scanner contrEmail = new Scanner(System.in);
		String conEmail = contrEmail.nextLine();

		return conEmail;
	}

	public static String contractorPhysicalAddress() {
		System.out.println("Contractor's physical address: ");
		Scanner contrPhysAdd = new Scanner(System.in);
		String conPhysAdd = contrPhysAdd.nextLine();

		return conPhysAdd;
	}

	// Methods for capturing the details of the customers
	public static String customerName() {
		System.out.println("Customer's name: \n");
		Scanner clientName = new Scanner(System.in);
		String cliName = clientName.nextLine();

		return cliName;
	}

	public static int customerTelephoneNumber() {
		System.out.println("Client's telephone number: \n");
		Scanner clientTele = new Scanner(System.in);
		int cliTele = clientTele.nextInt();

		return cliTele;
	}

	public static String customerEmailAddress() {
		System.out.println("Client's email address: \n");
		Scanner clientEmail = new Scanner(System.in);
		String cliEmail = clientEmail.nextLine();

		return cliEmail;
	}

	public static String customerPhysicalAddress() {
		System.out.println("Client's physical address: ");
		Scanner clientPhysAdd = new Scanner(System.in);
		String cliPhysAdd = clientPhysAdd.nextLine();

		return cliPhysAdd;
	}

	// Methods for capturing the details of the architect
	public static String architectName() {
		Scanner archName = new Scanner(System.in);
		String arcName = archName.nextLine();

		return arcName;
	}

	public static int architectTelNum() {
		Scanner archTele = new Scanner(System.in);
		int arcTele = archTele.nextInt();

		return arcTele;
	}

	public static String architectEmailAddress() {
		Scanner archEmail = new Scanner(System.in);
		String arcEmail = archEmail.nextLine();

		return arcEmail;
	}

	public static String architectPhysAdd() {
		Scanner archPhysAdd = new Scanner(System.in);
		String arcPhysAdd = archPhysAdd.nextLine();

		return arcPhysAdd;
	}

	public static void mainMenu() {
		System.out.println("Please select one of the following options: \n");
		System.out.println(" 1. Project registration\n" + " 2. Contractor registration\n" + " 3. View all projects\n");
	}

}
