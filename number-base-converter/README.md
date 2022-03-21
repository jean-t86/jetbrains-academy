# Number Base Converter

## Stage 1 - Convert Decimals

## Description

In daily life, we mostly use the decimal numeral system, but still, there are many other ways to represent numbers. For
example, when working with computers, you'll probably deal with binary, octal, and hexadecimal numbers. It's really nice
to have a tool that can help you easily and correctly convert numbers from one system to another: in this project, we
will build such a tool step-by-step.

In the first stage, you need to implement conversion from decimal to binary, octal, and hexadecimal. The program will
read the user's decimal number and the user's target radix (or base). Then, it will output the given number in the
corresponding base.

Converting from decimal to octal and hexadecimal isn't much different from converting to binary. The algorithm is really
similar to binary conversion, only the base number is different with 8 or 16 in place of 2. See some examples of
converting the number 110 from decimal to octal and hexadecimal below:

![img.png](images/base8remainder.png)
Result: 110(base 10) = 156(base 8)

![img.png](images/base16remainder.png)
Result: 110(base 10) = 6E(base 16)

## Objectives

Your program's output should consist of the following three lines:

1. On the first line, the prompt Enter number in decimal system: is shown, and the user inputs a decimal number to be
   converted.
2. On the second one, the prompt Enter target base: is printed, and the user enters the target base (2, 8, or 16).
3. On the third one, the message Conversion result: is printed, followed by the correct number representation in the
   given base.

## Examples

The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

**Example 1:**

```
Enter number in decimal system: > 8
Enter target base: > 16
Conversion result: 8
```

**Example 2:**

```
Enter number in decimal system: > 101
Enter target base: > 2
Conversion result: 1100101
```

**Example 3:**

```
Enter number in decimal system: > 103
Enter target base: > 8
Conversion result: 147
```

## Stage 2 - Convert to Decimal

### Description

At this point, the user needs to restart the program each time after converting just one number, which is very
inconvenient. Let's fix that and make your program prompt the user for more numbers until the user is ready to quit.

Also, we will add the reverse conversion: from binary, octal, and hexadecimal systems to decimal. Converting from octal,
hexadecimal, or any other system to decimal works almost the same way as with binary numbers, only the base is
different. See for yourself:

```
1726(base 8) = 1 ∗ 512 + 7 ∗ 64 + 2 ∗ 8 + 6 ∗ 1 = 982(base 10)

A3C(base 16) = 10 ∗ 256 + 3 ∗ 16 + 12 ∗ 1 = 2620(base 10)
```

### Objectives

Your program should output the prompt Do you want to convert /from decimal or /to decimal? (To quit type /exit) to
prompt the user for their next move. The possible commands are /from, /to, and /exit.

- If the user types /from, the program should behave as in the previous stage and convert the user's number from the
  decimal system to binary, octal, or hexadecimal.
- If the user types /to, the program should:
    1. Print the prompt Enter source number: and read the user input number to be converted to decimal.
    2. Print the prompt Enter source base: and read the target base (2, 8, or 16).
    3. Output the message Conversion to decimal result: followed by the number's representation in the decimal system.
- If the user types /exit, the program stops. Otherwise, it should process the command and prompt for the next one.

### Examples

The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

**Example 1:**

```
Do you want to convert /from decimal or /to decimal? (To quit type /exit) > /from
Enter a number in decimal system: > 8
Enter the target base: > 16
Conversion result: 8

Do you want to convert /from decimal or /to decimal? (To quit type /exit) > /from
Enter a number in decimal system: > 101
Enter the target base: > 2
Conversion result: 1100101

Do you want to convert /from decimal or /to decimal? (To quit type /exit) > /from
Enter a number in decimal system: > 103
Enter the target base: > 8
Conversion result: 147

Do you want to convert /from decimal or /to decimal? (To quit type /exit) > /exit
```

**Example 2:**

```
Do you want to convert /from decimal or /to decimal? (To quit type /exit) > /to
Enter source number: > ff
Enter source base: > 16
Conversion to decimal result: 255

Do you want to convert /from decimal or /to decimal? (To quit type /exit) > /to
Enter source number: > 71
Enter source base: > 8
Conversion to decimal result: 57

Do you want to convert /from decimal or /to decimal? (To quit type /exit) > /to
Enter source number: > 111001
Enter source base: > 2
Conversion to decimal result: 57

Do you want to convert /from decimal or /to decimal? (To quit type /exit) > /exit
```

**Example 3:**

```
Do you want to convert /from decimal or /to decimal? (To quit type /exit) > /to
Enter source number: > 25a
Enter source base: > 16
Conversion to decimal result: 602

Do you want to convert /from decimal or /to decimal? (To quit type /exit) > /from
Enter number in decimal system: > 602
Enter target base: > 16
Conversion result: 25a

Do you want to convert /from decimal or /to decimal? (To quit type /exit) > /exit
```

## Stage 3 - Convert to any base

### Description

Ideally, we want to convert numbers in different bases, not only from or to the decimal system. In this stage, we will
add support for converting numbers from any given source base to any target base. As there are 26 Latin letters and 10
digits, the maximum base is 26 + 10 = 36. So, the target and source base will be between 2 and 36.

Also, it might be more convenient for our users if the program didn't ask for the base before each conversion and
instead remembered the previously input base. This way, the users will have to do much less typing when they need to
convert a bunch of numbers from base A to base B.

To convert a number from the source base to the target base, you should first convert it to the decimal system and then
convert the decimal number to the target base.

Note that from this stage on, numbers might be larger than you expect, so you should use `BigInteger` instead of `Int`
or
`Long` to avoid errors.

### Objectives

Your program should have a two-level menu:

- On the first level, the user sees the following prompt: Enter two numbers in format: {source base} {target base} (To
  quit type /exit). Then, they input two numbers separated by a single space: source base and target base. The user also
  has the option to use the /exit command to quit the program.
- On the second level, the user sees another prompt: Enter number in base {user source base} to convert to base {user
  target base} (To go back type /back), and inputs the number in the source base. The program outputs the message
  Conversion result: followed by the number in the target base. Then, the program asks for the new number to convert
  from the previously provided source base to the target base. To change the bases, the user can choose the BACK command
  and return to the first level menu to make the necessary changes.

### Examples

The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

```
Enter two numbers in format: {source base} {target base} (To quit type /exit) > 10 2
Enter number in base 10 to convert to base 2 (To go back type /back) > 11
Conversion result: 1011

Enter number in base 10 to convert to base 2 (To go back type /back) > 18
Conversion result: 10010

Enter number in base 10 to convert to base 2 (To go back type /back) > 127
Conversion result: 1111111

Enter number in base 10 to convert to base 2 (To go back type /back) > 189344562689000108753301247
Conversion result: 1001110010011111010001010100111110001111101101011010101101001001111110100010111011111111

Enter number in base 10 to convert to base 2 (To go back type /back) > /back

Enter two numbers in format: {source base} {target base} (To quit type /exit) > 36 10
Enter number in base 36 to convert to base 10 (To go back type /back) > abcde
Conversion result: 17325410

Enter number in base 36 to convert to base 10 (To go back type /back) > 13a0
Conversion result: 50904

Enter number in base 36 to convert to base 10 (To go back type /back) > az
Conversion result: 395

Enter number in base 36 to convert to base 10 (To go back type /back) > /back

Enter two numbers in format: {source base} {target base} (To quit type /exit) > /exit
```

## Stage 4 - Convert Fractions

### Description

Fractional numbers can also be converted from one base to another, so let's add this functionality to our program!

To convert a fractional number from one base to another, you need to split the number into two parts: integer and
fractional. Convert each part from their base to decimal independently and then convert them (once again, separately) to
the target base. Finally, combine both parts and get the final result!

The most challenging part will probably be converting the decimal fractional part to the target base. Don't worry,
though: since you already know how to convert fractions from decimal to binary, it should not be a problem for you!
Converting fractions from decimal to any base is practically the same: just use the proper denominator, which is the
target base, instead of 2.

The example below shows how to convert the number 0.375 from decimal to base 20:

![img.png](images/base20Fractions.png)

Result: 0.375(base 10) = 0.7a(base 20)

Just like in the previous stage, the input numbers can be large. You might want to consider using java.math.BigDecimal
to handle large fractions.

### Objectives

Your program should behave almost the same way as in the previous stage: the two-level menu and the commands stay the
same.

When your program gets a fractional number, it should output its representation in the target base rounded to 5
characters (digits or letters) in the fractional part. If there is no fractional part in the initial number, it should
be omitted in the resulting number, too.

### Examples

The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

```
Enter two numbers in format: {source base} {target base} (To quit type /exit) > 10 7
Enter number in base 10 to convert to base 7 (To go back type /back) > 0.234
Conversion result: 0.14315

Enter number in base 10 to convert to base 7 (To go back type /back) > 10.234
Conversion result: 13.14315

Enter number in base 10 to convert to base 7 (To go back type /back) > /back

Enter two numbers in format: {source base} {target base} (To quit type /exit) > 35 17
Enter number in base 35 to convert to base 17 (To go back type /back) > af.xy
Conversion result: 148.g88a8

Enter number in base 35 to convert to base 17 (To go back type /back) > aaaa.0
Conversion result: 54e36.00000

Enter number in base 35 to convert to base 17 (To go back type /back) > /back

Enter two numbers in format: {source base} {target base} (To quit type /exit) > 21 10
Enter number in base 21 to convert to base 10 (To go back type /back) > 4242
Conversion result: 38012

Enter number in base 21 to convert to base 10 (To go back type /back) > 4242.13a
Conversion result: 38012.05550

Enter number in base 21 to convert to base 10 (To go back type /back) > /back

Enter two numbers in format: {source base} {target base} (To quit type /exit) > /exit
```