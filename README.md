# User Story Generator
![version](https://img.shields.io/badge/version-1.0.0-green)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)


![alt text][image]

[image]: ./resource/image/Buchdrucker-1568.png "Image by Jost_Amman ... Eygentliche Beschreibung aller Stände auff Erden, hoher und nidriger, geistlicher und weltlicher, aller Künsten, Handwercken und Händeln ... from https://commons.wikimedia.org/w/index.php?curid=207246"

## Overview

This project contains a utility to generate a set of user stories. I created
this project because I noticed some colleagues were using spreadsheets as an ideation phase before 
creating user stories in an agile tool (e.g. Jira). So I thought
it might be useful to be able to generate user stories, in sentence format, from this
raw data. I also noticed that user story fragments tend to be repetitive and reusable
so I added the ability to generate the user stories from reusable fragments (i.e. in a normalized form).

## Usage

The stories are generated from user story data expressed in both
a simple form (with *denormalized* data) and a more complex form (with *normalized* data).
In both cases, the data is encoded in spreadsheets.

## Prerequisite

Java Runtime, V1.8 or later

## Command Line Syntax 
```                            
java -jar user-story-generator-1.0.0.jar

Flags:
    -d  <filename>  The location of a spreadsheet file containing the user story data
    -o  <filename>  The location of a file to which the stories will be written
    -s  <filename>  The location of a file containing selection criteria
    -h              Help
```

### Flags
* **-d** \<filename\>
  * The location of a spreadsheet file containing user story data (Roles, Goals, Benefits and Criteria). If not specified "data.xslx" will be assumed.
* **-o** \<filename\>
  * The location of a file to which the stories will be written. If the file already exists it will be overwritten without warning. If not specified, standard out is used.
* **-s** \<filename\>
  * The location of a file containing selection criteria. This is only needed when the input data file is in normalized form. 
* **-h**
  * Help. Whenever specified, any other parameters are ignored. When no parameters are specified, **-h** is assumed.
 
###  Simple Form (Denormalized Data)

In this form, all the data needed by the generator is provided in a single spreadsheet file, with one sheet in that file.  Because
it can contain repetitive data (e.g. multiple occurances of the same Role) I refer to it as *denormalized*. 

The spreadsheet file is structured to contain the four columns in the first
sheet. No column headers are assumed. Each row in the first column contains a Role. 
Each row in the second column contains a Goal. Each row in the third column contains a
Benefit.  The fourth column is optional and contains a list of Acceptance Criteria.  

For each row, a user story is generated by simply surrounding the data with text 
to form a complete user story sentence of the form.
``` 
As a <role> I want to <goal> so that I can <benefit>
    Acceptance Criteria:
        <acceptance criterion 1>
        <acceptance criterion 2>
        ...
        <acceptance criterion n>
```
For example, in the data file I could have a row with four columns that looks like this...
```
| Beatle | hold your hand | have a hit single | The song must be less than three minutes long |
``` 
and the resulting user story would be printed like this ...
```
As a Beatle I want to hold your hand so that I can have a hit single.
	Acceptance Criteria:
		The song must be less than three minutes long
```
An example of a denormalized data file is [here](./resource/example/user-story-denormalized-data.xlsx)

An example of a file generated from this data is [here](./resource/example/user-story-generated.txt)

#### Running the Example 
```
java -jar user-story-generator-1.0.0.jar -d "./resource/example/user-story-denormalized-data.xlsx" -o "./user-story-generated-denormalized.txt"
```

### Advanced Form (Normalized Data)

In this form, the data needed by the generator is provided in a spreadsheet file, with one sheet
in that file for each type of element of a user story.  Because duplicate
 data is reduced (for example, a Role that is used in multiple user
 stories only needs to be specified once),
I refer to it as *normalized*. 

The spreadsheet file is structured so that the there is a sheet named 'Role', a sheet named
'Goal', a sheet named 'Benefit' and a sheet named 'Criterion'.
No column headers are assumed.   

For example, in the data file I could have sheets that look like this ...
```
Sheet: Role
| Solution Architect | 
| Solution Developer | 
| Business Analyst   | 
| System Admin       | 
| Beatle             | 

Sheet Goal:
| quickly and easily test my code in my test Cloud environment | 
| deploy the Repository in my test Cloud                       | 
| deploy the Client in my test Cloud                           | 
| hold your hand                                               | 

Sheet: Benefit
| try out writing some apps using it                                        |         
| test how it could work, and what it will cost, in a corporate environment |
| evaluate how well it compares with the competition                        |
| have a hit single                                                         |

Sheet: Criterion
| The solution can be installed in less than minute        | 
| The solution can be uninstalled in less than two minutes | 
| The solution is blue in color                            | 
| The solution is less than 100 MB in size                 | 
| The solution can support at least 100 concurrent users   | 
| The song must be less than three minutes long            | 
``` 

What stories get generated from this data is controlled by another file called a *selections file*.
This file is in csv format, and contains one row for each story you want to generate.  The row
contains an index into each of the sheets in the data file.  For the Criterion data, the index is
optional and can contain contain multiple selections.   Thus each row in the selctions file 
contains 3 to N values separated 
by a comma.  

For example, in the data file I could have sheets that look like this ...
```
5,4,4,6
4,2,2,1,4,5
```
and when used with the example data shown above the resulting user story would be printed like this ...
```
As a Beatle I want to hold your hand so that I can have a hit single.
	Acceptance Criteria:
		The song must be less than three minutes long

As a System Admin I want to deploy the Repository in my test Cloud so that I can test how it could work, and what it will cost, in a corporate environment.
	Acceptance Criteria:
		The solution can be installed in less than minute
		The solution is less than 100 MB in size
		The solution can support at least 100 concurrent users

```
An example of a denormalized data file is [here](./resource/example/user-story-normalized-data.xlsx)

An example of a selection file is [here](./resource/example/user-story-normalized-data.csv)

An example of a file generated from this data is [here](./resource/example/user-story-generated.txt)

#### Running the Example 
```
java -jar user-story-generator-1.0.0.jar -d "./resource/example/user-story-normalized-data.xlsx" -o "./user-story-generated-normalized.txt -s "./resource/example/user-story-normalized-data.csv"
```

See the [example generated file](./resource/example/user-story-generated.txt)

You many notice the same example user stories are generated from the normalized and denormalized
examples. 

### Maven Commands

#### Building the jar from source

```
mvn clean package 

```

#### Test

```
mvn test 

```


