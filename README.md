# Crunch-Database
In this assignment, I develop a key value store database called CrunchDB in the Java programming language using dynamic data structures. All entries to the database contain a unique key which will map to a set of values. Each entry of the database is identified by a unique key string and contains a dynamically sized list of integer values.

## Description
A program in Java that implements CrunchDB as shown in the examples below. Since this assignment is an introduction to Java, test cases will contain only valid input commands and not cause any integer overflows. Keys are case sensitive and do not contain spaces. Commands are case insensitive.
Entry values are indexed from 1. Snapshots are indexed from 1 and are unique for the lifetime of the program. Keys, entries and snapshots are to be outputted in the order from most recently added to least recently added. Snapshots can be archived in a human-readable text file, storing all entries in the following form <entry_key>|[<entries>,...]}
Archived snapshots can be restored by CrunchDB, resetting the data to the archived snapshot’s state. The program can be contained in the provided scaffold of CrunchDB.java, Snapshot.java and Entry.java. The program reads from standard input and writes to standard output.

## Commands
The program implements the following commands.
* If a <key> does not exist in the current state, output: no such key
* If a <snapshot> does not exist in the database, output: no such snapshot
* If an <index> does not exist in an entry, output: index out of range
  
BYE clear database and exit
HELP display this help message
LIST KEYS displays all keys in current state
LIST ENTRIES displays all entries in current state
LIST SNAPSHOTS displays all snapshots in the database
GET <key> displays entry values
DEL <key> deletes entry from current state
PURGE <key> deletes entry from current state and snapshots
SET <key> <value ...> sets entry values
PUSH <key> <value ...> pushes values to the front
APPEND <key> <value ...> appends values to the back
PICK <key> <index> displays value at index
PLUCK <key> <index> displays and removes value at index
POP <key> displays and removes the front value
DROP <id> deletes snapshot
ROLLBACK <id> restores to snapshot and deletes newer snapshots
CHECKOUT <id> replaces current state with a copy of snapshot
SNAPSHOT saves the current state as a snapshot
ARCHIVE <id> <filename> saves snapshot to file
RESTORE <filename> loads and restores snapshot from file
MIN <key> displays minimum value
MAX <key> displays maximum value
SUM <key> displays sum of values
LEN <key> displays number of values
REV <key> reverses order of values
UNIQ <key> removes repeated adjacent values
SORT <key> sorts values in ascending order
DIFF <key> <key ...> displays set difference of values in keys
INTER <key> <key ...> displays set intersection of values in keys
UNION <key> <key ...> displays set union of values in keys
CARTPROD <key> <key ...> displays cartesian product of sets
