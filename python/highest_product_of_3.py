# Takes the maximum product of 3 numbers in a list of integers
def highest_product_of_3(numbers):
    numbers.sort()
    # If all numbers are negative, return the product of the least negative ones
    if (numbers[-1] < 0):
        return numbers[-1] * numbers[-2] * numbers[-3]

    # If the second biggest negative number is bigger than the second biggest 
    # positive one, return the sum of biggest positive * the two biggest negative
    if ((numbers[1] < 0) and (abs(numbers[1]) > abs(numbers[-2]))):
        return numbers[0] * numbers[1] * numbers[-1]

    # Otherwise, return product of three biggest numbers
    return numbers[-1] * numbers[-2] * numbers[-3]


print("This value should be 504. It is:", highest_product_of_3([8,1,3,9,0,4,7]))
print("This value should be 810. It is:", highest_product_of_3([-10,9,8,1,3,-9,0,4,7]))
print("This value should be -10. It is:", highest_product_of_3([-5,-10,-1,-54,-2,-49]))
