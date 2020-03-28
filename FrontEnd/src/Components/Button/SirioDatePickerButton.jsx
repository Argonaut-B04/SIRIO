import React from "react";
import DatePicker from "react-datepicker";
import SirioDatePickerCustomInput from "./SirioDatePickerCustomInput";
import "react-datepicker/dist/react-datepicker.css";

export default class SirioDatePickerButton extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            selectedDate: null,
            idTarget: null
        };

        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(date, id) {
        this.setState({
            selectedDate: date
        });
    };

    render() {
        return (
            <DatePicker
                selected={this.state.selectedDate}
                onChange={date => {
                    this.handleChange(date, this.props.id);
                    this.props.handleChange(date, this.props.id);
                }}
                customInput={<SirioDatePickerCustomInput {...this.props} />}
            />
        );
    }
}