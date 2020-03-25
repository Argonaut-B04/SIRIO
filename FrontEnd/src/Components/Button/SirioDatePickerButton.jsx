import React from "react";
import DatePicker from "react-datepicker";
import SirioDatePickerCustomInput from "./SirioDatePickerCustomInput";
import "react-datepicker/dist/react-datepicker.css";

export default class SirioDatePickerButton extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            startDate: null
        };
    }

    handleChange = date => {
        this.setState({
            startDate: date
        });
    };

    render() {
        return (
            <DatePicker
                selected={this.state.startDate}
                onChange={this.handleChange}
                customInput={<SirioDatePickerCustomInput {...this.props} />}
            />
        );
    }
}