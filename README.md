# MatriceProject
A graphical interface to solve matrices as well as other linear algebra computations

## Update 
Matrix and Vector Constructors have been added, as well as Elementary row operations for Matrices

## Elementary Row Operations 

| Operation | Function |
| --- | --- |
| Swap | Swaps Row 1 with Row 2 |
| Scalar Multiply | Scales a Row by a constant |
| Add | Adds a scaled row to another Row |

## Ref and RRef 

Ref and RRef are algorithms to convert a matrix into Reduced Echelon Form, and Reduced Echelon Form, in the goal of determining
numerous pieces of info about the matrix. These functions are used extensively by other sections of the project. They use several 
helper methods to streamline the operation.
 
### Ref Algorithm Description 

1. First determine if a row, starting with row one, has 1 one in the column.
2. If no one exists, determine if a row has all values divisible by leading value, then divide all values by leading to get a one in the first column
3. If no one exists and divisible, select 1st row, and divide by leading value. 
4. If row that was selected is not row 1, swap row with row 1
5. Using the first row, subtract it from each row below it, multiplying the first row by the leading value of each row
6. Repeat steps 1 - 5 using the next column, but if the column is an all zero column not including rows with pivots already, move on to the next column and mark as pivot 
7. Preform until all possible pivots are created, creating a Reduced Echelon Form Matrix.

Ref also sets the values in two arrays, representing the rows and columns, for use in other algorithms, as well sets the ref flag to true.

### RRef Algorithm

1. Preform Ref on Matrix if not already done
2. Using the the row, starting from the bottom, subtract off from all rows above, multiplying by the value in that column of each row

sets the RRef flag to true

## Varibale and vector form

Variable displays each variable, indicating if it is a free variable or showing it as the summation of free variables and the value in the augmented column. Assumes the matrix is augmented 

Vector form generates vectors representing each of the free variables, as well as the constant vector, displaying it

## Augment 

Augments the matrix with a new column, represented by a vector 

## Matrix Function 

Matrix Function is an extension of matrix and is set up in the form Ax = b. A matrix is given to represent A and info about it
such as onto and 1 to 1 are determined. The function can also display is a b is in the range, give a potential input for b, and generate the output for x

## System form 

The system form is one of the gui screens available to the user, and is used to solve a system equation. It preforms as follows:       
Reads in a system of a equations, using two parameters, # of Variables, and # of Equations.           
The variables are allowed are X1 .... X(# of variables), in any order. Each variable must be separated by " + " and the equation must be in the form X1 + 5X2 + 6X3 = 5 for example, with each opeator and variable separated by a space.      
Vector or variable form is specified, and if properly inputed, the answer will be pumped out
