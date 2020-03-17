import React from "react";
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";
import TableButton from "../Button/TableButton";

export default class DatePickerButton extends React.Component {
    state = {
        startDate: null
    };

    handleChange = date => {
        this.setState({
            startDate: date
        });
    };

    ExampleCustomInput = ({ value, onClick }) => (
        <TableButton onClick={onClick} disabled={this.props.disabled} recommended={this.props.recommended} hyperlinkMode={this.props.hyperlinkMode} unchangeable={this.props.unchangeable}>
            {value ? value : this.props.tenggatWaktu} 
        </TableButton>
    );

    render() {
        return (
            <DatePicker
                selected={this.state.startDate}
                onChange={this.handleChange}
                customInput={<this.ExampleCustomInput/>}
                placeholderText="for real"
            />
        );
    }
}