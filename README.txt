Team Id: 	41
Project Title: 	Waste Collection Route Tracker
Team Members: 	Ayush Suman(2025BCS0139), Robin Dungdung(2025BCS0107)


Problem Description:-
Bins are assigned to areas. A bin can have waste of various types (organic, plastic, paper, metal).
A route can have many areas and a truck is assigned to any of those routes. 
Score: Each bin is assigned a score on the basis of their priority and distance from a truck. The best score will decide which bin should be picked up by a truck.


Classes:-
Area: 			Defines an area object.
Waste: 			Abstract class for defining waste type of bin. It has sub-classes metalWaste, organicWaste, paperWaste and plasticWaste.
Route: 			Creates a route object. A route can have multiple areas in it.
Truck: 			Creates a truck object. A truck can be assigned to any route.
WasteBin: 		Creates an object which has properties of a dustbin.
AssignmentManager: 	Assigns route to a truck.
BinManager: 		It has methods for adding a bin to any given list, detecting if waste quantity crosses certain thresholds.
FleetManager: 		It has methods for adding a truck to any given list.
RouteManager: 		It has methods for adding a route to any given list.
ScenarioData: 		It is Collection (return type) for storing data of a scenario.
ScenarioLoader: 	Creates multiple objects such as areas, trucks, routes, bins, and links them according to the project using ScenarioData.
Helper:			Managers misc stuff like printing output format, distance calculations, etc.


Encapsulation:-
Classes Area, Route, Truck, and WasteBin implement encapsulation for privating various variables.

Inheritance:-
Sub-classes OrganicWaste, PaperWaste, PlasticWaste and MetalWaste are inheriting abstract Waste class.

Strings:-
Used in Area class for storing name of area in variable areaName and in main class for storing scenario file's name in variable fileName

Exception Handling:-
Handles invalid inputs like scenario out of range or choice entered not being a number.

Packages:-
4 packages wastecollection.app, wastecollection.service, wastecollection.util, wastecollection.model.



For compiling:-

javac -d out/production/WasteCollectionRouteTracker $(find src -name "*.java")

or
 
javac -d out/production/WasteCollectionRouteTracker src/wastecollection/app/Main.java src/wastecollection/model/*.java src/wastecollection/service/*.java src/wastecollection/utils/*.java


For running the program:-

java -cp out/production/WasteCollectionRouteTracker wastecollection.app.Main


Contributions:-
Ayush Suman:    Designed the program
Robin Dungdung: Algorithm and testing

We will further improve the program as we liked making it.
The repository is https://github.com/Mtank2006/WasteCollectionRouteTracker.git
