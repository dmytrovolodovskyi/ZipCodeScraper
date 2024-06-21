created by      Dmytro Volodovskyi
last update:    21.06.2024


ZipCodeScraper

This program downloads a file with addresses from the website
and puts the data from the file to the database

________________________________________________________________________________________________________________________
Classes
'Main'          -   starts the program.
'Controller'    -   controls the entire program logic.

'FileService'   -   downloads a file from the website,    (to take another file - change only 'FILE_NAME' field)
                    places the file in local directory 'src\main\resources\files\'
                    and reads it to the local List<>.

'DataService'   -   gets data from the List<>,
                    removes all duplicate lines,
                    translates words to Latin       ->    ('TranslatorService' uses library to translate words)
                    and puts the data to the entities.                        

'DBService'     -  connects to the database         ->    ('DBConnection'      takes properties from local directory 'src/main/resources/hibernate.properties')
                   and pushes entities to database.                           
________________________________________________________________________________________________________________________

Also you can see 'graphic.jpg'.
