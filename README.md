# Smart Pantry Manager

## Project Overview

Smart Pantry Manager is an Android application developed in Java to help users manage pantry ingredients and find recipes they can prepare using the ingredients they already have. The aim is to reduce food waste by suggesting recipes based on available ingredients.

## Features

- Add, edit, and delete pantry ingredients.
- Store ingredient names, quantities, units, and optional expiry dates.
- Save pantry information locally using SQLite.
- Browse pre-loaded recipes stored in the database.
- Suggest recipes only when all required ingredients and quantities are available.
- View recipe ingredients and preparation instructions.
- Save profile and measurement-unit preferences in Settings.

## Technologies Used

- Java
- Android Studio
- XML layouts
- SQLite using `SQLiteOpenHelper`
- Gradle

## Database Structure

The application uses a local SQLite database with tables for:

- `ingredients` — stores pantry items and their details.
- `recipes` — stores recipe names, descriptions, and preparation instructions.
- `recipe_ingredients` — stores the ingredients and quantities required for each recipe.

## Recipe Matching

The application compares the recipe requirements with the ingredients stored in the user's pantry. A recipe is suggested only when all required ingredients and sufficient quantities are available. The application also supports selected ingredient-name and measurement-unit normalisation.

## Running the Application

1. Install Android Studio.
2. Clone or download this repository.
3. Open the project in Android Studio.
4. Allow Gradle to synchronise.
5. Run the application on an Android emulator or compatible Android device.

## Project Status

This project is being developed as part of an academic Android application assignment.

## Author

Tasvir Punwasi