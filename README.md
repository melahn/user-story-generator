# User Story Generator

## Overview

This project contains a utility to generate a set of user stories. I created
this project because I noticed some colleagues were using spreadsheets as an ideation phase before 
creating user stories in an agile tool (e.g. Jira).   So I thought
it might be useful to be able to generate user stories, in sentence format, from this
raw data.  I also noticed that user story fragments tend to be repetitive and reusable
so I added the ability to generate the user stories from reusable fragments (i.e. in a normalized form).

## Usage

The stories are generated from user story data expressed in both
a *normalized (simple)* form and a *denormalized* form.  The data is encoded in spreadsheets.


###  Simple Form (Denormalized Data)

In this form, all the data needed by the generator is provided in a single spreadsheet file, with one sheet.  

The spreadsheet file is structured to contain the four columns in the first
sheet.  No column headers are assumed.  Each row in the first column contains a Role. Each row in the second column contains a Goal  Each row in the third column contains a
Benefit.  The fourth column contains a list of Acceptance Criteria.  

An example of a denormalized data file is [here](./resource/example/user-story-denormalized.xlsx.)

For each row containing data for a Role, a user story is generated and printed by simply surrounding the data with text 
to form a complete user story sentence of the form.
``` 
As a <role> I want to <goal> so that <benefit>
    Acceptance Criteria:
        <acceptance criterion 1>
        <acceptance criterion 2>
        ...
        <acceptance criterion n>
```
#### Running the Simple Example 
```
java -jar original-user-story-generator-1.0-SNAPSHOT.jar 
-d "./resource/example/user-story-denormalized-data.xlsx" -o "./user-story-generated-denormalized.txt"

```

### Complex Form (Normalized Data)

In this form, the data is provided as a set of sheets, where each sheet contains a
table of data.  The spreadsheet file is structured in four sheets with the 
following names.
* Role 
* Goal
* Benefit
* Criterion

An example of a normalized data file is [here](./resource/example/user-story-normalized.xlsx.)

A second file is a selection file in csv format, that contains a list of integers that
relate each of the columns in the spreadsheet file to form a user story statement.
For each row in the selection file row the corresponding data is used in the Role, Goal, Benefit and 
Criterion sheets to 
to form a complete user story sentence of the form

An example of a selection file is [here](./resource/example/user-story-normalized-data.csv)
``` 
As a <role> I want to <goal> so that <benefit>
    Acceptance Criteria:
        <acceptance criterion 1>
        <acceptance criterion 2>
        ...
        <acceptance criterion n>
```

#### Running the Complex Example 
```
java -jar original-user-story-generator-1.0-SNAPSHOT.jar 
-d "./resource/example/user-story-normalized-data.xlsx" -o "./user-story-generated-normalized.txt -s "./resource/example/user-story-normalized-data.csv"

```

See the [example generated file](./resource/example/user-story-generated.txt)

### Command Line Syntax

```
                                    
java --- -d <filename> --- -s <filename> --- -o <filename> ---+----------+
                                                              |          |
                                                              +--- -h ---+       
                   
```

#### Parameters

   * **-d** \<filename\>
      *  The location of a spreadsheet file containing the user story data (Roles, Goals, Benefits and Criteria)
   * **-s** \<filename\>
        *  The location of a csv file containing the selection data
   * **-o** \<filename\>
        *  The location of a file to which the stories will be written.  If the file
         already exists it will be overwritten without warning.
   * **-h**
      * Help.  Whenever specified, any other parameters are ignored.  When no parameters are specified, **-h** is assumed.
  
## Prerequisites

Java Runtime, V1.8 or later
                                          
### Example of a Generated File

[This file](./resource/example/user-story-generated.txt) was generate using the examples shown above.


### Maven Commands

#### Building the jar from source

```
mvn clean package 

```

#### Test

```
mvn test 

```
