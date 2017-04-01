# GruppeF
Git Repository for Group F of OOA&amp;D

Git project in Intellij:

1. Download Intellij Ultimate
2. Install Intellij
3. Integrate Git and GitHub Plugin
4. Checkout project from repository url
    https://github.com/ChristophRheinberger/GruppeF.git
5. Start working :D

The git commands are in the bottom right corner of Intellij.

Or use a [Git](https://git-scm.com/downloads) client and a preffered IDE.

## Project structure
* Server
  * Contains the logic for the data validation
  * Uses Servlets for the communication with the Client
  * Communicates with the database
  * Uses Java 8, HTML5, CSS3, JavaScript
* Client
  * Loads the data from the Server
  * Does rudimentary data validation
  * Provides a GUI
  * Uses Java8 and JavaFX

## Repository
Try to avoid adding binary files such as \*.pdf, \*.docx, \*.zip, ... .

Make always the comment clear and understandable for other persons.

Try to make small commits to make it easier to find the differences when a merge error occurs.

## IDE
Use Maven for dependencies where possible to avoid unneccessary build errors with different versions of a library.

## Tests
Describe and show how to run the tests with code examples.

## Coding conventions
Use the [Google style guide](https://google.github.io/styleguide/javaguide.html)

## README syntax
[Documentation](https://enterprise.github.com/downloads/en/markdown-cheatsheet.pdf)
