import React from "react";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";
import SirioButton from "../Button/SirioButton";

export default class SirioDatePickerButton extends React.Component {
    state = {
        startDate: null
    };

    handleChange = date => {
        this.setState({
            startDate: date
        });
    };

    ExampleCustomInput = ({ value, onClick }) => (
        <SirioButton
            onClick={onClick}
            {...this.props}
        >
            {value ? value : this.props.children}
        </SirioButton>
    );

    render() {
        return (
            <DatePicker
                selected={this.state.startDate}
                onChange={this.handleChange}
                customInput={<this.ExampleCustomInput />}
            />
        );
    }
}