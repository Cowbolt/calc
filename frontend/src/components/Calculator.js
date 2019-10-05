import React from 'react';
import {Table, TextInput, Button} from 'react-materialize';

class Calculator extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      value: ""
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  // Event handler for input change
  handleChange(event) {
    this.setState({value: event.target.value});
  }

  // Event handler for input submit (pressing enter)
  handleSubmit(event) {
    this.calculate();
    event.preventDefault();
  }

  // Event handler for button presses
  // If equals sign is pressed, calculate sum. Otherwise, update input value
  onClick = button => {
    if (button === "=") {
      this.calculate()
    }
    else if (button === "←") {
      this.setState({
        value: this.state.value.slice(0, -1)
      })
    }
    else if (button === "CE") {
      this.setState({
        value: ""
      })
    }
    else {
      this.setState({
        value: this.state.value + button
      })
    }
  }

  // Submit POST query to backend endpoint, fetch resultant value
  calculate() {
    fetch("0.0.0.0", {
      method: "POST",
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({expression: this.state.value})
    })
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            value: result.value
          });
        },
        (error) => {
          this.setState({
            value: error
          });
        }
      )
  }

  calculatorButtons() {
    return(
      <div className="calculatorButtons">
      <Table>
        <tbody>
          {this.buttonRow(1,2,3,'/','CE')}
          {this.buttonRow(4,5,6,'*','←')}
          {this.buttonRow(7,8,9,'-','(')}
          {this.buttonRow(0,'.','=','+',')')}
        </tbody>
      </Table>
      </div>
    );
  }

  buttonRow() {
    const buttons = []
    for (var i = 0; i < arguments.length; i++) {
      buttons.push(<CalculatorButton name={arguments[i]} onClick={this.onClick}/>)
    }
    return(
      <tr>
      {buttons}
      </tr>
    );
  }

  render() {
    return (
      <div className="calculator">
        <form onSubmit={this.handleSubmit}>
          <TextInput
             type="text"
             style={{'font-size': '30px'}}
             value={this.state.value}
             onChange={this.handleChange}
          />
        </form>
        {this.calculatorButtons()}
      </div>
    );
  }

}

class CalculatorButton extends React.Component {
  constructor(props) {
    super(props);
    this.state = {value: ""};
  }
  render() {
    return(
      <td>
        <Button
        node="a"
        waves="light"
        large
        style={{
        width: '20%',
        display: 'table-cell',
        'font-size': '30px'}}
        name = {this.props.name}
        onClick={(e) => this.props.onClick(e.target.name)}>
        {this.props.name}
      </Button>
      </td>
    );
  }
}

export default Calculator;
