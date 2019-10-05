import operator

# Converts expression from infix to postfix using shunting-yard algorithm
# XXX: Does not implicitly insert multiplicator before parentheses, i.e. 10(30) will be incorrectly parsed.
def infix_to_postfix(expression):
    # Ordered in terms of precedence, from highest to lowest
    operators = ['*','/','-','+','(',')']
    parentheses = ['(',')']
    numbers = ['1','2','3','4','5','6','7','8','9','0','.']
    postfix = ""
    stack = []
    for char in expression:
        if char in numbers:
            postfix+=char

        elif char in operators:
            # Append whitespace to expression to signify end of number
            postfix+=" "
            if len(stack) == 0:
                stack.append(char)
            else:
                while ((len(stack) != 0) and (operators.index(stack[-1]) < operators.index(char))):
                    postfix+=stack.pop()
                stack+=char

        elif char in parentheses:
            if char == '(':
                stack.append(char)
            else:
                while stack[-1] != '(':
                    postfix+=stack.pop()


    while len(stack) != 0:
        postfix+=stack.pop()

    return postfix.replace('(','').replace(')','')



def evaluate_postfix(expression):
    # Stack representing our current progress. Eventually becomes final value
    stack = []
    # We parse char by char, so we need a number string
    number = ""
    operators = {
        '+' : operator.add,
        '-' : operator.sub,
        '*' : operator.mul,
        '/' : operator.truediv,
        }
    numbers = ['1','2','3','4','5','6','7','8','9','0','.']

    #  number
    for char in expression:
        if char in operators:
            # If we were in the process of parsing a number, add it to the stack
            if(len(number) > 0):
                stack.append(float(number))
                number=""
            # We pop beforehand to get the proper operand order
            second = stack.pop()
            first = stack.pop()
            stack.append(operators[char](float(first),float(second)))

        elif char == " " and len(number) > 0:
            stack.append(float(number))
            number=""
        else:
            number+=char

    return stack[0]

postfix = (infix_to_postfix("1+1"))
print("\n1+1 should equal 2.\nThe postfix value is", postfix, "\nIt evaluates to:", evaluate_postfix(postfix))

postfix = (infix_to_postfix("(4+2)*6/3"))
print("\n(4+2)*6/3 should equal 12.\nThe postfix value is", postfix, "\nIt evaluates to:", evaluate_postfix(postfix))

postfix = (infix_to_postfix("(2.213+503/29)*100.2125-20*820"))
print("\n(2.213+503/29)*100.2125-20*820 should equal -14440.1.\nThe postfix value is", postfix, "\nIt evaluates to:", evaluate_postfix(postfix))



